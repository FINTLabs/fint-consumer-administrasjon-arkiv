package no.fint.consumer.models.saksstatus;

import no.fint.model.resource.administrasjon.arkiv.SaksstatusResource;
import no.fint.model.resource.administrasjon.arkiv.SaksstatusResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class SaksstatusLinker extends FintLinker<SaksstatusResource> {

    public SaksstatusLinker() {
        super(SaksstatusResource.class);
    }

    public void mapLinks(SaksstatusResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SaksstatusResources toResources(Collection<SaksstatusResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public SaksstatusResources toResources(Stream<SaksstatusResource> stream, int offset, int size, int totalItems) {
        SaksstatusResources resources = new SaksstatusResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(SaksstatusResource saksstatus) {
        return getAllSelfHrefs(saksstatus).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(SaksstatusResource saksstatus) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(saksstatus.getSystemId()) && !isEmpty(saksstatus.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(saksstatus.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(SaksstatusResource saksstatus) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(saksstatus.getSystemId()) && !isEmpty(saksstatus.getSystemId().getIdentifikatorverdi())) {
            builder.add(saksstatus.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

