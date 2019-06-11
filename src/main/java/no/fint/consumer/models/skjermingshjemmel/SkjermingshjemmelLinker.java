package no.fint.consumer.models.skjermingshjemmel;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.SkjermingshjemmelResource;
import no.fint.model.resource.administrasjon.arkiv.SkjermingshjemmelResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class SkjermingshjemmelLinker extends FintLinker<SkjermingshjemmelResource> {

    public SkjermingshjemmelLinker() {
        super(SkjermingshjemmelResource.class);
    }

    public void mapLinks(SkjermingshjemmelResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SkjermingshjemmelResources toResources(Collection<SkjermingshjemmelResource> collection) {
        SkjermingshjemmelResources resources = new SkjermingshjemmelResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(SkjermingshjemmelResource skjermingshjemmel) {
        if (!isNull(skjermingshjemmel.getSystemId()) && !isEmpty(skjermingshjemmel.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(skjermingshjemmel.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

