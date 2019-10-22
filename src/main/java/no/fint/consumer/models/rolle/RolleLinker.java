package no.fint.consumer.models.rolle;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.RolleResource;
import no.fint.model.resource.administrasjon.arkiv.RolleResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class RolleLinker extends FintLinker<RolleResource> {

    public RolleLinker() {
        super(RolleResource.class);
    }

    public void mapLinks(RolleResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public RolleResources toResources(Collection<RolleResource> collection) {
        RolleResources resources = new RolleResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(RolleResource rolle) {
        if (!isNull(rolle.getSystemId()) && !isEmpty(rolle.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(rolle.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(RolleResource rolle) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(rolle.getSystemId()) && !isEmpty(rolle.getSystemId().getIdentifikatorverdi())) {
            builder.add(rolle.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

