package no.fint.consumer.models.variantformat;

import no.fint.model.resource.administrasjon.arkiv.VariantformatResource;
import no.fint.model.resource.administrasjon.arkiv.VariantformatResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class VariantformatLinker extends FintLinker<VariantformatResource> {

    public VariantformatLinker() {
        super(VariantformatResource.class);
    }

    public void mapLinks(VariantformatResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public VariantformatResources toResources(Collection<VariantformatResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public VariantformatResources toResources(Stream<VariantformatResource> stream, int offset, int size, int totalItems) {
        VariantformatResources resources = new VariantformatResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(VariantformatResource variantformat) {
        return getAllSelfHrefs(variantformat).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(VariantformatResource variantformat) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(variantformat.getSystemId()) && !isEmpty(variantformat.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(variantformat.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(VariantformatResource variantformat) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(variantformat.getSystemId()) && !isEmpty(variantformat.getSystemId().getIdentifikatorverdi())) {
            builder.add(variantformat.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

