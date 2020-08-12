package no.fint.consumer.models.arkivressurs;

import lombok.extern.slf4j.Slf4j;
import no.fint.audit.FintAuditService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.consumer.status.StatusCache;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.Operation;
import no.fint.model.administrasjon.arkiv.ArkivActions;
import no.fint.relations.FintRelationsMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(name = "Arkivressurs", path = RestEndpoints.ARKIVRESSURS, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ArkivressursCustomController {

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private StatusCache statusCache;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @DeleteMapping("/kildesystemid/{id:.+}")
    public ResponseEntity deleteArkivressursByKildesystemId(
            @PathVariable String id,
            @RequestHeader(name = HeaderConstants.ORG_ID) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT) String client
    ) {
        log.debug("deleteArkivressursByKildesystemId {}, OrgId: {}, Client: {}", id, orgId, client);
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.UPDATE_ARKIVRESSURS, client);
        event.setQuery("kildesystemid/" + id);
        event.setOperation(Operation.DELETE);
        fintAuditService.audit(event);

        consumerEventUtil.send(event);

        statusCache.put(event.getCorrId(), event);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/systemid/{id:.+}")
    public ResponseEntity deleteArkivressursBySystemId(
            @PathVariable String id,
            @RequestHeader(name = HeaderConstants.ORG_ID) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT) String client
    ) {
        log.debug("deleteArkivressursBySystemId {}, OrgId: {}, Client: {}", id, orgId, client);
        Event event = new Event(orgId, Constants.COMPONENT, ArkivActions.UPDATE_ARKIVRESSURS, client);
        event.setQuery("systemid/" + id);
        event.setOperation(Operation.DELETE);
        fintAuditService.audit(event);

        consumerEventUtil.send(event);

        statusCache.put(event.getCorrId(), event);

        return ResponseEntity.noContent().build();
    }

}
