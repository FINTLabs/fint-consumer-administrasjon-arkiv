package no.fint.consumer.models.tilknyttetregistreringsom;

import no.fint.model.resource.administrasjon.arkiv.TilknyttetRegistreringSomResource;
import no.fint.model.resource.administrasjon.arkiv.TilknyttetRegistreringSomResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class TilknyttetRegistreringSomLinker extends FintLinker<TilknyttetRegistreringSomResource> {

    public TilknyttetRegistreringSomLinker() {
        super(TilknyttetRegistreringSomResource.class);
    }

    public void mapLinks(TilknyttetRegistreringSomResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public TilknyttetRegistreringSomResources toResources(Collection<TilknyttetRegistreringSomResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public TilknyttetRegistreringSomResources toResources(Stream<TilknyttetRegistreringSomResource> stream, int offset, int size, int totalItems) {
        TilknyttetRegistreringSomResources resources = new TilknyttetRegistreringSomResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(TilknyttetRegistreringSomResource tilknyttetregistreringsom) {
        return getAllSelfHrefs(tilknyttetregistreringsom).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(TilknyttetRegistreringSomResource tilknyttetregistreringsom) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(tilknyttetregistreringsom.getSystemId()) && !isEmpty(tilknyttetregistreringsom.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(tilknyttetregistreringsom.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(TilknyttetRegistreringSomResource tilknyttetregistreringsom) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(tilknyttetregistreringsom.getSystemId()) && !isEmpty(tilknyttetregistreringsom.getSystemId().getIdentifikatorverdi())) {
            builder.add(tilknyttetregistreringsom.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

