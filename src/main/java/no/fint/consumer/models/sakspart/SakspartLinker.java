package no.fint.consumer.models.sakspart;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.SakspartResource;
import no.fint.model.resource.administrasjon.arkiv.SakspartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class SakspartLinker extends FintLinker<SakspartResource> {

    public SakspartLinker() {
        super(SakspartResource.class);
    }

    public void mapLinks(SakspartResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SakspartResources toResources(Collection<SakspartResource> collection) {
        SakspartResources resources = new SakspartResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(SakspartResource sakspart) {
        if (!isNull(sakspart.getSakspartId()) && !isEmpty(sakspart.getSakspartId().getIdentifikatorverdi())) {
            return createHrefWithId(sakspart.getSakspartId().getIdentifikatorverdi(), "sakspartid");
        }
        
        return null;
    }
    
}

