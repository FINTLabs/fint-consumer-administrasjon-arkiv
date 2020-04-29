package no.fint.consumer.models.arkivdel;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.ArkivdelResource;
import no.fint.model.resource.administrasjon.arkiv.ArkivdelResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ArkivdelLinker extends FintLinker<ArkivdelResource> {

    public ArkivdelLinker() {
        super(ArkivdelResource.class);
    }

    public void mapLinks(ArkivdelResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ArkivdelResources toResources(Collection<ArkivdelResource> collection) {
        ArkivdelResources resources = new ArkivdelResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ArkivdelResource arkivdel) {
        return getAllSelfHrefs(arkivdel).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(ArkivdelResource arkivdel) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(arkivdel.getSystemId()) && !isEmpty(arkivdel.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(arkivdel.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(ArkivdelResource arkivdel) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(arkivdel.getSystemId()) && !isEmpty(arkivdel.getSystemId().getIdentifikatorverdi())) {
            builder.add(arkivdel.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

