package no.fint.consumer.models.merknadstype;

import no.fint.model.resource.administrasjon.arkiv.MerknadstypeResource;
import no.fint.model.resource.administrasjon.arkiv.MerknadstypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class MerknadstypeLinker extends FintLinker<MerknadstypeResource> {

    public MerknadstypeLinker() {
        super(MerknadstypeResource.class);
    }

    public void mapLinks(MerknadstypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public MerknadstypeResources toResources(Collection<MerknadstypeResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public MerknadstypeResources toResources(Stream<MerknadstypeResource> stream, int offset, int size, int totalItems) {
        MerknadstypeResources resources = new MerknadstypeResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(MerknadstypeResource merknadstype) {
        return getAllSelfHrefs(merknadstype).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(MerknadstypeResource merknadstype) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(merknadstype.getSystemId()) && !isEmpty(merknadstype.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(merknadstype.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(MerknadstypeResource merknadstype) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(merknadstype.getSystemId()) && !isEmpty(merknadstype.getSystemId().getIdentifikatorverdi())) {
            builder.add(merknadstype.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

