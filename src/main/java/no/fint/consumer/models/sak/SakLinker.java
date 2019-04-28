package no.fint.consumer.models.sak;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.SakResource;
import no.fint.model.resource.administrasjon.arkiv.SakResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class SakLinker extends FintLinker<SakResource> {

    public SakLinker() {
        super(SakResource.class);
    }

    public void mapLinks(SakResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SakResources toResources(Collection<SakResource> collection) {
        SakResources resources = new SakResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(SakResource sak) {
        if (!isNull(sak.getMappeId()) && !isEmpty(sak.getMappeId().getIdentifikatorverdi())) {
            return createHrefWithId(sak.getMappeId().getIdentifikatorverdi(), "mappeid");
        }
        if (!isNull(sak.getSystemId()) && !isEmpty(sak.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(sak.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

