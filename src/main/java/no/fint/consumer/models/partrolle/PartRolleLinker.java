package no.fint.consumer.models.partrolle;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.PartRolleResource;
import no.fint.model.resource.administrasjon.arkiv.PartRolleResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class PartRolleLinker extends FintLinker<PartRolleResource> {

    public PartRolleLinker() {
        super(PartRolleResource.class);
    }

    public void mapLinks(PartRolleResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public PartRolleResources toResources(Collection<PartRolleResource> collection) {
        PartRolleResources resources = new PartRolleResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(PartRolleResource partrolle) {
        return getAllSelfHrefs(partrolle).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(PartRolleResource partrolle) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(partrolle.getSystemId()) && !isEmpty(partrolle.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(partrolle.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(PartRolleResource partrolle) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(partrolle.getSystemId()) && !isEmpty(partrolle.getSystemId().getIdentifikatorverdi())) {
            builder.add(partrolle.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

