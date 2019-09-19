package no.fint.consumer.models.arkivressurs;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import no.fint.cache.CacheService;
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

import no.fint.model.administrasjon.arkiv.Arkivressurs;
import no.fint.model.resource.administrasjon.arkiv.ArkivressursResource;
import no.fint.model.administrasjon.arkiv.ArkivActions;
import no.fint.model.felles.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.arkivressurs", havingValue = "false", matchIfMissing = true)
public class ArkivressursCacheService extends CacheService<ArkivressursResource> {

    public static final String MODEL = Arkivressurs.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private ArkivressursLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public ArkivressursCacheService() {
        super(MODEL, ArkivActions.GET_ALL_ARKIVRESSURS, ArkivActions.UPDATE_ARKIVRESSURS);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, ArkivressursResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_ARKIVRESSURS, fixedRateString = Constants.CACHE_FIXEDRATE_ARKIVRESSURS)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Arkivressurs cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_ALL_ARKIVRESSURS, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<ArkivressursResource> getArkivressursByKildesystemId(String orgId, String kildesystemId) {
        return getOne(orgId, (resource) -> Optional
                .ofNullable(resource)
                .map(ArkivressursResource::getKildesystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(_id -> _id.equals(kildesystemId))
                .orElse(false));
    }

    public Optional<ArkivressursResource> getArkivressursBySystemId(String orgId, String systemId) {
        return getOne(orgId, (resource) -> Optional
                .ofNullable(resource)
                .map(ArkivressursResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(_id -> _id.equals(systemId))
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<ArkivressursResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<ArkivressursResource> to ArkivressursResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), ArkivressursResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (ArkivActions.valueOf(event.getAction()) == ArkivActions.UPDATE_ARKIVRESSURS) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                add(event.getOrgId(), data);
                log.info("Added {} elements to cache for {}", data.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            update(event.getOrgId(), data);
            log.info("Updated cache for {} with {} elements", event.getOrgId(), data.size());
        }
    }
}
