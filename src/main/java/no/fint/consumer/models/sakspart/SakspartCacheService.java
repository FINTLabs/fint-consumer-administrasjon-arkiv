package no.fint.consumer.models.sakspart;

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
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.relations.FintResourceCompatibility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import no.fint.model.administrasjon.arkiv.Sakspart;
import no.fint.model.resource.administrasjon.arkiv.SakspartResource;
import no.fint.model.administrasjon.arkiv.ArkivActions;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.sakspart", havingValue = "false", matchIfMissing = true)
public class SakspartCacheService extends CacheService<SakspartResource> {

    public static final String MODEL = Sakspart.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private SakspartLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public SakspartCacheService() {
        super(MODEL, ArkivActions.GET_ALL_SAKSPART, ArkivActions.UPDATE_SAKSPART);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, SakspartResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_SAKSPART, fixedRateString = Constants.CACHE_FIXEDRATE_SAKSPART)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    private void populateCache(String orgId) {
		log.info("Populating Sakspart cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_ALL_SAKSPART, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<SakspartResource> getSakspartBySakspartId(String orgId, String sakspartId) {
        return getOne(orgId, (resource) -> Optional
                .ofNullable(resource)
                .map(SakspartResource::getSakspartId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(_id -> _id.equals(sakspartId))
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<SakspartResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<SakspartResource> to SakspartResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), SakspartResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (ArkivActions.valueOf(event.getAction()) == ArkivActions.UPDATE_SAKSPART) {
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
