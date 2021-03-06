package no.fint.consumer.models.klassifikasjonssystem;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.KlassifikasjonssystemResource;
import no.fint.model.resource.administrasjon.arkiv.KlassifikasjonssystemResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class KlassifikasjonssystemLinker extends FintLinker<KlassifikasjonssystemResource> {

    public KlassifikasjonssystemLinker() {
        super(KlassifikasjonssystemResource.class);
    }

    public void mapLinks(KlassifikasjonssystemResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KlassifikasjonssystemResources toResources(Collection<KlassifikasjonssystemResource> collection) {
        KlassifikasjonssystemResources resources = new KlassifikasjonssystemResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KlassifikasjonssystemResource klassifikasjonssystem) {
        return getAllSelfHrefs(klassifikasjonssystem).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(KlassifikasjonssystemResource klassifikasjonssystem) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(klassifikasjonssystem.getSystemId()) && !isEmpty(klassifikasjonssystem.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(klassifikasjonssystem.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(KlassifikasjonssystemResource klassifikasjonssystem) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(klassifikasjonssystem.getSystemId()) && !isEmpty(klassifikasjonssystem.getSystemId().getIdentifikatorverdi())) {
            builder.add(klassifikasjonssystem.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

