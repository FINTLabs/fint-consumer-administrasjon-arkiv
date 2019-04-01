package no.fint.consumer.models.tilknyttetregistreringsom;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.TilknyttetRegistreringSomResource;
import no.fint.model.resource.administrasjon.arkiv.TilknyttetRegistreringSomResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class TilknyttetRegistreringSomLinker extends FintLinker<TilknyttetRegistreringSomResource> {

    public TilknyttetRegistreringSomLinker() {
        super(TilknyttetRegistreringSomResource.class);
    }

    public void mapLinks(TilknyttetRegistreringSomResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public TilknyttetRegistreringSomResources toResources(Collection<TilknyttetRegistreringSomResource> collection) {
        TilknyttetRegistreringSomResources resources = new TilknyttetRegistreringSomResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(TilknyttetRegistreringSomResource tilknyttetregistreringsom) {
        if (!isNull(tilknyttetregistreringsom.getSystemId()) && !isEmpty(tilknyttetregistreringsom.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(tilknyttetregistreringsom.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

