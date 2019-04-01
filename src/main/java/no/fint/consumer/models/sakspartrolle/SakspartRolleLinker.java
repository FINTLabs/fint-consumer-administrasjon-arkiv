package no.fint.consumer.models.sakspartrolle;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.SakspartRolleResource;
import no.fint.model.resource.administrasjon.arkiv.SakspartRolleResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class SakspartRolleLinker extends FintLinker<SakspartRolleResource> {

    public SakspartRolleLinker() {
        super(SakspartRolleResource.class);
    }

    public void mapLinks(SakspartRolleResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SakspartRolleResources toResources(Collection<SakspartRolleResource> collection) {
        SakspartRolleResources resources = new SakspartRolleResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(SakspartRolleResource sakspartrolle) {
        if (!isNull(sakspartrolle.getSystemId()) && !isEmpty(sakspartrolle.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(sakspartrolle.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

