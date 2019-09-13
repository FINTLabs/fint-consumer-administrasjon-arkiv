package no.fint.consumer.models.administrativenhet;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.AdministrativEnhetResource;
import no.fint.model.resource.administrasjon.arkiv.AdministrativEnhetResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class AdministrativEnhetLinker extends FintLinker<AdministrativEnhetResource> {

    public AdministrativEnhetLinker() {
        super(AdministrativEnhetResource.class);
    }

    public void mapLinks(AdministrativEnhetResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public AdministrativEnhetResources toResources(Collection<AdministrativEnhetResource> collection) {
        AdministrativEnhetResources resources = new AdministrativEnhetResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(AdministrativEnhetResource administrativenhet) {
        if (!isNull(administrativenhet.getSystemId()) && !isEmpty(administrativenhet.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(administrativenhet.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

