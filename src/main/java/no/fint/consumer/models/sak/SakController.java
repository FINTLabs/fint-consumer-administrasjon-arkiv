package no.fint.consumer.models.sak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import no.fint.audit.FintAuditService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.consumer.event.SynchronousEvents;
import no.fint.consumer.exceptions.*;
import no.fint.consumer.status.StatusCache;
import no.fint.consumer.utils.EventResponses;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.Operation;
import no.fint.event.model.Status;
import no.fint.model.administrasjon.arkiv.ArkivActions;
import no.fint.model.resource.administrasjon.arkiv.SakResource;
import no.fint.model.resource.administrasjon.arkiv.SakResources;
import no.fint.relations.FintRelationsMediaType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = {"Sak"})
@CrossOrigin
@RestController
@RequestMapping(name = "Sak", value = RestEndpoints.SAK, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SakController {

    @Autowired(required = false)
    private SakCacheService cacheService;

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private SakLinker linker;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private StatusCache statusCache;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SynchronousEvents synchronousEvents;

    @GetMapping("/last-updated")
    public Map<String, String> getLastUpdated(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
        if (cacheService == null) {
            throw new CacheDisabledException("Sak cache is disabled.");
        }
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        String lastUpdated = Long.toString(cacheService.getLastUpdated(orgId));
        return ImmutableMap.of("lastUpdated", lastUpdated);
    }

    @GetMapping("/cache/size")
    public ImmutableMap<String, Integer> getCacheSize(@RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId) {
        if (cacheService == null) {
            throw new CacheDisabledException("Sak cache is disabled.");
        }
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        return ImmutableMap.of("size", cacheService.getAll(orgId).size());
    }

    @GetMapping
    public SakResources getSak(
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client,
            @RequestParam(required = false) Long sinceTimeStamp) {
        if (cacheService == null) {
            throw new CacheDisabledException("Sak cache is disabled.");
        }
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        if (client == null) {
            client = props.getDefaultClient();
        }
        log.debug("OrgId: {}, Client: {}", orgId, client);

        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_ALL_SAK, client);
        event.setOperation(Operation.READ);
        fintAuditService.audit(event);
        fintAuditService.audit(event, Status.CACHE);

        List<SakResource> sak;
        if (sinceTimeStamp == null) {
            sak = cacheService.getAll(orgId);
        } else {
            sak = cacheService.getAll(orgId, sinceTimeStamp);
        }

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return linker.toResources(sak);
    }

    @GetMapping("/search")
    public SakResources searchSak(
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client,
            HttpServletRequest request
    ) throws InterruptedException {
        if (StringUtils.isBlank(request.getQueryString())) {
            throw new BadRequestException("Invalid query");
        }
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_SAK, client);
        event.setQuery("?" + request.getQueryString());
        event.setOperation(Operation.READ);
        BlockingQueue<Event> queue = synchronousEvents.register(event);
        consumerEventUtil.send(event);

        Event response = EventResponses.handle(queue.poll(5, TimeUnit.MINUTES));
        if (response.getData() == null ||
                response.getData().isEmpty()) {
            throw new EntityNotFoundException(event.getQuery());
        }

        List<SakResource> resources = objectMapper.convertValue(response.getData(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, SakResource.class));

        return linker.toResources(resources);
    }

    @GetMapping("/mappeid/{ar}/{sekvensnummer}")
    public SakResource getTilskuddFartoyByMappeArSekvensnummer(
            @PathVariable String ar,
            @PathVariable String sekvensnummer,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) throws InterruptedException {
        return getSakByMappeId(ar + "/" + sekvensnummer, orgId, client);
    }

    @GetMapping("/mappeid/{id:.+}")
    public SakResource getSakByMappeId(
            @PathVariable String id,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) throws InterruptedException {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        if (client == null) {
            client = props.getDefaultClient();
        }
        log.debug("mappeId: {}, OrgId: {}, Client: {}", id, orgId, client);

        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_SAK, client);
        event.setOperation(Operation.READ);
        event.setQuery("mappeId/" + id);

        if (cacheService != null) {
            fintAuditService.audit(event);
            fintAuditService.audit(event, Status.CACHE);

            Optional<SakResource> sak = cacheService.getSakByMappeId(orgId, id);

            fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

            return sak.map(linker::toResource).orElseThrow(() -> new EntityNotFoundException(id));

        } else {
            BlockingQueue<Event> queue = synchronousEvents.register(event);
            consumerEventUtil.send(event);

            Event response = EventResponses.handle(queue.poll(5, TimeUnit.MINUTES));

            if (response.getData() == null ||
                    response.getData().isEmpty()) {
                throw new EntityNotFoundException(id);
            }

            SakResource sak = objectMapper.convertValue(response.getData().get(0), SakResource.class);

            fintAuditService.audit(response, Status.SENT_TO_CLIENT);

            return linker.toResource(sak);
        }
    }

    @GetMapping("/systemid/{id:.+}")
    public SakResource getSakBySystemId(
            @PathVariable String id,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) throws InterruptedException {
        if (props.isOverrideOrgId() || orgId == null) {
            orgId = props.getDefaultOrgId();
        }
        if (client == null) {
            client = props.getDefaultClient();
        }
        log.debug("systemId: {}, OrgId: {}, Client: {}", id, orgId, client);

        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.GET_SAK, client);
        event.setOperation(Operation.READ);
        event.setQuery("systemId/" + id);

        if (cacheService != null) {
            fintAuditService.audit(event);
            fintAuditService.audit(event, Status.CACHE);

            Optional<SakResource> sak = cacheService.getSakBySystemId(orgId, id);

            fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

            return sak.map(linker::toResource).orElseThrow(() -> new EntityNotFoundException(id));

        } else {
            BlockingQueue<Event> queue = synchronousEvents.register(event);
            consumerEventUtil.send(event);

            Event response = EventResponses.handle(queue.poll(5, TimeUnit.MINUTES));

            if (response.getData() == null ||
                    response.getData().isEmpty()) throw new EntityNotFoundException(id);

            SakResource sak = objectMapper.convertValue(response.getData().get(0), SakResource.class);

            fintAuditService.audit(response, Status.SENT_TO_CLIENT);

            return linker.toResource(sak);
        }
    }


    //
    // Exception handlers
    //
    @ExceptionHandler(EventResponseException.class)
    public ResponseEntity handleEventResponseException(EventResponseException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getResponse());
    }

    @ExceptionHandler(UpdateEntityMismatchException.class)
    public ResponseEntity handleUpdateEntityMismatch(Exception e) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(CreateEntityMismatchException.class)
    public ResponseEntity handleCreateEntityMismatch(Exception e) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(EntityFoundException.class)
    public ResponseEntity handleEntityFound(Exception e) {
        return ResponseEntity.status(HttpStatus.FOUND).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(CacheDisabledException.class)
    public ResponseEntity handleBadRequest(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity handleUnkownHost(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity handlieInterrupted(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(e));
    }

}

