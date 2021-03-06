package no.fint.consumer.models.variantformat;

import no.fint.model.resource.Link;
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
        VariantformatResources resources = new VariantformatResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
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

