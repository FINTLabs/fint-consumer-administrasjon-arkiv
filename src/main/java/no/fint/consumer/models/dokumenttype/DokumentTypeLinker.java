package no.fint.consumer.models.dokumenttype;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.DokumentTypeResource;
import no.fint.model.resource.administrasjon.arkiv.DokumentTypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class DokumentTypeLinker extends FintLinker<DokumentTypeResource> {

    public DokumentTypeLinker() {
        super(DokumentTypeResource.class);
    }

    public void mapLinks(DokumentTypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DokumentTypeResources toResources(Collection<DokumentTypeResource> collection) {
        DokumentTypeResources resources = new DokumentTypeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(DokumentTypeResource dokumenttype) {
        if (!isNull(dokumenttype.getSystemId()) && !isEmpty(dokumenttype.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(dokumenttype.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}
