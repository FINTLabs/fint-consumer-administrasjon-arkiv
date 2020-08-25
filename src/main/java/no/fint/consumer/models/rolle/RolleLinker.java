package no.fint.consumer.models.rolle;

import no.fint.model.resource.administrasjon.arkiv.RolleResource;
import no.fint.model.resource.administrasjon.arkiv.RolleResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public RolleResources toResources(Stream<RolleResource> stream, int offset, int size, int totalItems) {
        RolleResources resources = new RolleResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(RolleResource rolle) {
        return getAllSelfHrefs(rolle).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(RolleResource rolle) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(rolle.getSystemId()) && !isEmpty(rolle.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(rolle.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(RolleResource rolle) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(rolle.getSystemId()) && !isEmpty(rolle.getSystemId().getIdentifikatorverdi())) {
            builder.add(rolle.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

