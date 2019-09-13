package no.fint.consumer.models.tilgang;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.TilgangResource;
import no.fint.model.resource.administrasjon.arkiv.TilgangResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class TilgangLinker extends FintLinker<TilgangResource> {

    public TilgangLinker() {
        super(TilgangResource.class);
    }

    public void mapLinks(TilgangResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public TilgangResources toResources(Collection<TilgangResource> collection) {
        TilgangResources resources = new TilgangResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(TilgangResource tilgang) {
        if (!isNull(tilgang.getSystemId()) && !isEmpty(tilgang.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(tilgang.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

