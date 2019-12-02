package no.fint.consumer.models.personalmappe;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.PersonalmappeResource;
import no.fint.model.resource.administrasjon.arkiv.PersonalmappeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class PersonalmappeLinker extends FintLinker<PersonalmappeResource> {

    public PersonalmappeLinker() {
        super(PersonalmappeResource.class);
    }

    public void mapLinks(PersonalmappeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public PersonalmappeResources toResources(Collection<PersonalmappeResource> collection) {
        PersonalmappeResources resources = new PersonalmappeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(PersonalmappeResource personalmappe) {
        if (!isNull(personalmappe.getFodselsnummer()) && !isEmpty(personalmappe.getFodselsnummer().getIdentifikatorverdi())) {
            return createHrefWithId(personalmappe.getFodselsnummer().getIdentifikatorverdi(), "fodselsnummer");
        }
        if (!isNull(personalmappe.getMappeId()) && !isEmpty(personalmappe.getMappeId().getIdentifikatorverdi())) {
            return createHrefWithId(personalmappe.getMappeId().getIdentifikatorverdi(), "mappeid");
        }
        if (!isNull(personalmappe.getSystemId()) && !isEmpty(personalmappe.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(personalmappe.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(PersonalmappeResource personalmappe) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(personalmappe.getFodselsnummer()) && !isEmpty(personalmappe.getFodselsnummer().getIdentifikatorverdi())) {
            builder.add(personalmappe.getFodselsnummer().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(personalmappe.getMappeId()) && !isEmpty(personalmappe.getMappeId().getIdentifikatorverdi())) {
            builder.add(personalmappe.getMappeId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(personalmappe.getSystemId()) && !isEmpty(personalmappe.getSystemId().getIdentifikatorverdi())) {
            builder.add(personalmappe.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

