package no.fint.consumer.models.dokumentbeskrivelse;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.DokumentbeskrivelseResource;
import no.fint.model.resource.administrasjon.arkiv.DokumentbeskrivelseResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class DokumentbeskrivelseLinker extends FintLinker<DokumentbeskrivelseResource> {

    public DokumentbeskrivelseLinker() {
        super(DokumentbeskrivelseResource.class);
    }

    public void mapLinks(DokumentbeskrivelseResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DokumentbeskrivelseResources toResources(Collection<DokumentbeskrivelseResource> collection) {
        DokumentbeskrivelseResources resources = new DokumentbeskrivelseResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(DokumentbeskrivelseResource dokumentbeskrivelse) {
        if (!isNull(dokumentbeskrivelse.getSystemId()) && !isEmpty(dokumentbeskrivelse.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(dokumentbeskrivelse.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

