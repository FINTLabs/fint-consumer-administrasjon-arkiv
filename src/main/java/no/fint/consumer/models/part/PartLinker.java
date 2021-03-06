package no.fint.consumer.models.part;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.PartResource;
import no.fint.model.resource.administrasjon.arkiv.PartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class PartLinker extends FintLinker<PartResource> {

    public PartLinker() {
        super(PartResource.class);
    }

    public void mapLinks(PartResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public PartResources toResources(Collection<PartResource> collection) {
        PartResources resources = new PartResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(PartResource part) {
        return getAllSelfHrefs(part).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(PartResource part) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(part.getPartId()) && !isEmpty(part.getPartId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(part.getPartId().getIdentifikatorverdi(), "partid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(PartResource part) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(part.getPartId()) && !isEmpty(part.getPartId().getIdentifikatorverdi())) {
            builder.add(part.getPartId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

