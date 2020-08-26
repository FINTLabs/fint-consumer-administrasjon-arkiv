package no.fint.consumer.status;

import lombok.extern.slf4j.Slf4j;
import no.fint.event.model.Event;
import no.fint.event.model.EventResponse;
import no.fint.event.model.HeaderConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StatusController {

    private final StatusCache statusCache;

    public StatusController(StatusCache statusCache) {
        this.statusCache = statusCache;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getStatus(
            @PathVariable String id,
            @RequestHeader(HeaderConstants.ORG_ID) String orgId,
            @RequestHeader(HeaderConstants.CLIENT) String client) {
        log.debug("/status/{} for {} from {}", id, orgId, client);
        if (!statusCache.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        Event event = statusCache.get(id);
        log.debug("Event: {}", event);
        log.trace("Data: {}", event.getData());
        if (!event.getOrgId().equals(orgId)) {
            return ResponseEntity.badRequest().body(new EventResponse() {
                {
                    setMessage("Invalid OrgId");
                }
            });
        }
        if (event.getResponseStatus() == null) {
            return ResponseEntity.noContent().build();
        }
        switch (event.getResponseStatus()) {
            case ACCEPTED:
                return ResponseEntity.ok(event.getResponse());
            case ERROR:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(event.getResponse());
            case CONFLICT:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(event.getResponse());
            case REJECTED:
                return ResponseEntity.badRequest().body(event.getResponse());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(event.getResponse());
    }

}
