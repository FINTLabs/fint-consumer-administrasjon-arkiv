package no.fint.consumer.models.korrespondansepart;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.KorrespondansepartResource;
import no.fint.model.resource.administrasjon.arkiv.KorrespondansepartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class KorrespondansepartLinker extends FintLinker<KorrespondansepartResource> {

    public KorrespondansepartLinker() {
        super(KorrespondansepartResource.class);
    }

    public void mapLinks(KorrespondansepartResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KorrespondansepartResources toResources(Collection<KorrespondansepartResource> collection) {
        KorrespondansepartResources resources = new KorrespondansepartResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KorrespondansepartResource korrespondansepart) {
        if (!isNull(korrespondansepart.getSystemId()) && !isEmpty(korrespondansepart.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(korrespondansepart.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

