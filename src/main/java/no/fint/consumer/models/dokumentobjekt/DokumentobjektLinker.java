package no.fint.consumer.models.dokumentobjekt;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.DokumentobjektResource;
import no.fint.model.resource.administrasjon.arkiv.DokumentobjektResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class DokumentobjektLinker extends FintLinker<DokumentobjektResource> {

    public DokumentobjektLinker() {
        super(DokumentobjektResource.class);
    }

    public void mapLinks(DokumentobjektResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DokumentobjektResources toResources(Collection<DokumentobjektResource> collection) {
        DokumentobjektResources resources = new DokumentobjektResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(DokumentobjektResource dokumentobjekt) {
        if (!isNull(dokumentobjekt.getSystemId()) && !isEmpty(dokumentobjekt.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(dokumentobjekt.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

