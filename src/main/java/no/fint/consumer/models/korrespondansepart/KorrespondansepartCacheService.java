package no.fint.consumer.models.korrespondansepart;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import no.fint.cache.Cache;
import no.fint.cache.CacheService;
import no.fint.cache.model.CacheObject;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.relations.FintResourceCompatibility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import no.fint.model.administrasjon.arkiv.Korrespondansepart;
import no.fint.model.resource.administrasjon.arkiv.KorrespondansepartResource;
import no.fint.model.administrasjon.arkiv.ArkivActions;
import no.fint.model.felles.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.korrespondansepart", havingValue = "false", matchIfMissing = true)
public class KorrespondansepartCacheService extends CacheService<KorrespondansepartResource> {

    public static final String MODEL = Korrespondansepart.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private KorrespondansepartLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public KorrespondansepartCacheService() {
        super(MODEL, ArkivActions.GET_ALL_KORRESPONDANSEPART, ArkivActions.UPDATE_KORRESPONDANSEPART);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, KorrespondansepartResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_KORRESPONDANSEPART, fixedRateString = Constants.CACHE_FIXEDRATE_KORRESPONDANSEPART)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Korrespondansepart cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_ALL_KORRESPONDANSEPART, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<KorrespondansepartResource> getKorrespondansepartByFodselsnummer(String orgId, String fodselsnummer) {
        return getOne(orgId, fodselsnummer.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(KorrespondansepartResource::getFodselsnummer)
                .map(Identifikator::getIdentifikatorverdi)
                .map(fodselsnummer::equals)
                .orElse(false));
    }

    public Optional<KorrespondansepartResource> getKorrespondansepartByOrganisasjonsnummer(String orgId, String organisasjonsnummer) {
        return getOne(orgId, organisasjonsnummer.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(KorrespondansepartResource::getOrganisasjonsnummer)
                .map(Identifikator::getIdentifikatorverdi)
                .map(organisasjonsnummer::equals)
                .orElse(false));
    }

    public Optional<KorrespondansepartResource> getKorrespondansepartBySystemId(String orgId, String systemId) {
        return getOne(orgId, systemId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(KorrespondansepartResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(systemId::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<KorrespondansepartResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<KorrespondansepartResource> to KorrespondansepartResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), KorrespondansepartResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (ArkivActions.valueOf(event.getAction()) == ArkivActions.UPDATE_KORRESPONDANSEPART) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<KorrespondansepartResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<KorrespondansepartResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            final Long volume = getCache(event.getOrgId()).map(Cache::volume).orElse(0L) >> 20;
            log.info("Updated cache for {} with {} cache objects ({} MiB)", event.getOrgId(), cacheObjects.size(), volume);
        }
    }
}
