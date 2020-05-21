package no.fint.consumer.util

import no.fint.audit.FintAuditService
import no.fint.consumer.config.ConsumerProps
import no.fint.consumer.models.administrativenhet.AdministrativEnhetCacheService
import no.fint.consumer.models.administrativenhet.AdministrativEnhetController
import no.fint.consumer.models.administrativenhet.AdministrativEnhetLinker
import no.fint.consumer.utils.RequestHeaderInterceptor
import no.fint.consumer.utils.RestEndpoints
import no.fint.event.model.HeaderConstants
import no.fint.model.resource.administrasjon.arkiv.AdministrativEnhetResources
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RequestHeaderInterceptorSpec extends Specification {
    AdministrativEnhetController controller
    AdministrativEnhetCacheService cacheService
    AdministrativEnhetLinker linker
    ConsumerProps props
    MockMvc mockMvc

    void setup() {
        cacheService = Mock()
        linker = Mock()
        props = Mock()
        controller = new AdministrativEnhetController(fintAuditService: Mock(FintAuditService), cacheService: cacheService, linker: linker, props: props)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addInterceptors(new RequestHeaderInterceptor('x-fint-access-collection'))
                .build()
    }

    def "Controller returns result when endpoint is included in custom header"() {
        when:
        def response = mockMvc.perform(get(RestEndpoints.ADMINISTRATIVENHET)
                .header(HeaderConstants.ORG_ID, 'org-id')
                .header(HeaderConstants.CLIENT, 'client')
                .header('x-fint-access-collection', RestEndpoints.ADMINISTRATIVENHET))

        then:
        1 * props.isOverrideOrgId() >> false
        1 * cacheService.getAll('org-id') >> []
        1 * linker.toResources([]) >> new AdministrativEnhetResources()
        response.andExpect(status().isOk())
    }

    def "Controller returns error when endpoint is not included in custom header"() {
        when:
        def response = mockMvc.perform(get(RestEndpoints.ADMINISTRATIVENHET)
                .header(HeaderConstants.ORG_ID, 'org-id')
                .header(HeaderConstants.CLIENT, 'client')
                .header('x-fint-access-collection', RestEndpoints.ARKIVRESSURS, RestEndpoints.SAK))

        then:
        response.andExpect(status().isForbidden())
    }
}
