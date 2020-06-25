package no.fint.consumer.models.klasse;

import no.fint.model.resource.administrasjon.arkiv.KlasseResource;
import no.fint.model.resource.administrasjon.arkiv.KlasseResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class KlasseLinker extends FintLinker<KlasseResource> {

    public KlasseLinker() {
        super(KlasseResource.class);
    }

    public void mapLinks(KlasseResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KlasseResources toResources(Collection<KlasseResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public KlasseResources toResources(Stream<KlasseResource> stream, int offset, int size, int totalItems) {
        KlasseResources resources = new KlasseResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(KlasseResource klasse) {
        return getAllSelfHrefs(klasse).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(KlasseResource klasse) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(klasse.getKlasseId()) && !isEmpty(klasse.getKlasseId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(klasse.getKlasseId().getIdentifikatorverdi(), "klasseid"));
        }
        if (!isNull(klasse.getSystemId()) && !isEmpty(klasse.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(klasse.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(KlasseResource klasse) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(klasse.getKlasseId()) && !isEmpty(klasse.getKlasseId().getIdentifikatorverdi())) {
            builder.add(klasse.getKlasseId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(klasse.getSystemId()) && !isEmpty(klasse.getSystemId().getIdentifikatorverdi())) {
            builder.add(klasse.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

