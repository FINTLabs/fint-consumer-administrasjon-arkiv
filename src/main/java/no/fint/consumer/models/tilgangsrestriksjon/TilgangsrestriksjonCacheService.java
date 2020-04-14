package no.fint.consumer.models.tilgangsrestriksjon;

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

import no.fint.model.administrasjon.arkiv.Tilgangsrestriksjon;
import no.fint.model.resource.administrasjon.arkiv.TilgangsrestriksjonResource;
import no.fint.model.administrasjon.arkiv.ArkivActions;
import no.fint.model.felles.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.tilgangsrestriksjon", havingValue = "false", matchIfMissing = true)
public class TilgangsrestriksjonCacheService extends CacheService<TilgangsrestriksjonResource> {

    public static final String MODEL = Tilgangsrestriksjon.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private TilgangsrestriksjonLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public TilgangsrestriksjonCacheService() {
        super(MODEL, ArkivActions.GET_ALL_TILGANGSRESTRIKSJON, ArkivActions.UPDATE_TILGANGSRESTRIKSJON);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, TilgangsrestriksjonResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_TILGANGSRESTRIKSJON, fixedRateString = Constants.CACHE_FIXEDRATE_TILGANGSRESTRIKSJON)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Tilgangsrestriksjon cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_ALL_TILGANGSRESTRIKSJON, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<TilgangsrestriksjonResource> getTilgangsrestriksjonBySystemId(String orgId, String systemId) {
        return getOne(orgId, systemId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(TilgangsrestriksjonResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(systemId::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<TilgangsrestriksjonResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<TilgangsrestriksjonResource> to TilgangsrestriksjonResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), TilgangsrestriksjonResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (ArkivActions.valueOf(event.getAction()) == ArkivActions.UPDATE_TILGANGSRESTRIKSJON) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<TilgangsrestriksjonResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<TilgangsrestriksjonResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            final Long volume = getCache(event.getOrgId()).map(Cache::volume).orElse(0L) >> 20;
            log.info("Updated cache for {} with {} cache objects ({} MiB)", event.getOrgId(), cacheObjects.size(), volume);
        }
    }
}
