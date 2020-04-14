package no.fint.consumer.models.skjermingshjemmel;

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

import no.fint.model.administrasjon.arkiv.Skjermingshjemmel;
import no.fint.model.resource.administrasjon.arkiv.SkjermingshjemmelResource;
import no.fint.model.administrasjon.arkiv.ArkivActions;
import no.fint.model.felles.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.skjermingshjemmel", havingValue = "false", matchIfMissing = true)
public class SkjermingshjemmelCacheService extends CacheService<SkjermingshjemmelResource> {

    public static final String MODEL = Skjermingshjemmel.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private SkjermingshjemmelLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public SkjermingshjemmelCacheService() {
        super(MODEL, ArkivActions.GET_ALL_SKJERMINGSHJEMMEL, ArkivActions.UPDATE_SKJERMINGSHJEMMEL);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, SkjermingshjemmelResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_SKJERMINGSHJEMMEL, fixedRateString = Constants.CACHE_FIXEDRATE_SKJERMINGSHJEMMEL)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Skjermingshjemmel cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_ALL_SKJERMINGSHJEMMEL, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<SkjermingshjemmelResource> getSkjermingshjemmelBySystemId(String orgId, String systemId) {
        return getOne(orgId, systemId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(SkjermingshjemmelResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(systemId::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<SkjermingshjemmelResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<SkjermingshjemmelResource> to SkjermingshjemmelResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), SkjermingshjemmelResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (ArkivActions.valueOf(event.getAction()) == ArkivActions.UPDATE_SKJERMINGSHJEMMEL) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<SkjermingshjemmelResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<SkjermingshjemmelResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            final Long volume = getCache(event.getOrgId()).map(Cache::volume).orElse(0L) >> 20;
            log.info("Updated cache for {} with {} cache objects ({} MiB)", event.getOrgId(), cacheObjects.size(), volume);
        }
    }
}
