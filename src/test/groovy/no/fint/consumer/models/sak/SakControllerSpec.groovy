package no.fint.consumer.models.sak

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.audit.FintAuditService
import no.fint.consumer.config.ConsumerProps
import no.fint.consumer.event.ConsumerEventUtil
import no.fint.consumer.event.SynchronousEvents
import no.fint.consumer.status.StatusCache
import no.fint.event.model.Event
import no.fint.event.model.Operation
import no.fint.event.model.ResponseStatus
import no.fint.model.resource.administrasjon.arkiv.SakResource
import no.fint.test.utils.MockMvcSpecification
import org.hamcrest.CoreMatchers
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

/*
 * This specification asserts that the manually-added mappings for /mappeid/{year}/{sequence}
 * are present.  If the controller is replaced with the generated version, these mappings will
 * be deleted.
 * If you get here due to an unexpected build failure, go back to you Git history and locate the
 * @GetMapping and @PutMapping for /mappeid/{ar}/{sekvensnummer}
 */

class SakControllerSpec extends MockMvcSpecification {

    private MockMvc mockMvc
    private SakController controller
    private SakLinker linker
    private SakCacheService cacheService
    private ConsumerProps props
    private FintAuditService auditService
    private ConsumerEventUtil consumerEventUtil
    private StatusCache statusCache

    void setup() {
        cacheService = Mock()
        props = Mock()
        auditService = Mock()
        linker = Mock()
        consumerEventUtil = Mock()
        statusCache = Mock()
        controller = new SakController(
                cacheService: cacheService,
                linker: linker,
                props: props,
                fintAuditService: auditService,
                consumerEventUtil: consumerEventUtil,
                statusCache: statusCache,
                objectMapper: new ObjectMapper()
        )
        mockMvc = standaloneSetup(controller)
    }

    def "Verify that GET by mappeId works"() {
        when:
        def response = mockMvc.perform(
                get('/sak/mappeid/2020/42')
                        .header('x-org-id', 'test.org')
                        .header('x-client', 'Spock'))

        then:
        response.andExpect(status().is2xxSuccessful())
        1 * cacheService.getSakByMappeId('test.org', '2020/42') >> Optional.of(new SakResource())
        1 * linker.toResource(_ as SakResource) >> new SakResource()
    }

    def "Verify that PUT by mappeId works"() {
        when:
        def response = mockMvc.perform(
                put('/sak/mappeid/2020/22')
                        .header('x-org-id', 'test.org')
                        .header('x-client', 'Spock')
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )

        then:
        response
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string('Location', CoreMatchers.containsString('/status/')))
        1 * linker.self() >> '/sak/'
    }

    def "Verify that GET forwards query string with no cache service"() {
        given:
        def synchronousEvents = Mock(SynchronousEvents)
        def queue = Mock(BlockingQueue)
        controller.cacheService = null
        controller.synchronousEvents = synchronousEvents

        when:
        def response = mockMvc.perform(
                get('/sak/?foo=bar&name=baz')
                        .header('x-org-id', 'test.org')
                        .header('x-client', 'Spock')
        )

        then:
        response
                .andExpect(status().is2xxSuccessful())
        1 * synchronousEvents.register({ it.request.query == '?foo=bar&name=baz' }) >> queue
        1 * queue.poll(5, TimeUnit.MINUTES) >> {
            Event event = new Event()
            event.responseStatus = ResponseStatus.ACCEPTED
            event
        }
    }

}
