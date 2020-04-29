package no.fint.consumer.models.skjermingshjemmel;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.SkjermingshjemmelResource;
import no.fint.model.resource.administrasjon.arkiv.SkjermingshjemmelResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        return getAllSelfHrefs(skjermingshjemmel).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(SkjermingshjemmelResource skjermingshjemmel) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(skjermingshjemmel.getSystemId()) && !isEmpty(skjermingshjemmel.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(skjermingshjemmel.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(SkjermingshjemmelResource skjermingshjemmel) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(skjermingshjemmel.getSystemId()) && !isEmpty(skjermingshjemmel.getSystemId().getIdentifikatorverdi())) {
            builder.add(skjermingshjemmel.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

