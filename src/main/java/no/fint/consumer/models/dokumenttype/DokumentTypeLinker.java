package no.fint.consumer.models.dokumenttype;

import no.fint.model.resource.administrasjon.arkiv.DokumentTypeResource;
import no.fint.model.resource.administrasjon.arkiv.DokumentTypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class DokumentTypeLinker extends FintLinker<DokumentTypeResource> {

    public DokumentTypeLinker() {
        super(DokumentTypeResource.class);
    }

    public void mapLinks(DokumentTypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DokumentTypeResources toResources(Collection<DokumentTypeResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public DokumentTypeResources toResources(Stream<DokumentTypeResource> stream, int offset, int size, int totalItems) {
        DokumentTypeResources resources = new DokumentTypeResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(DokumentTypeResource dokumenttype) {
        return getAllSelfHrefs(dokumenttype).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(DokumentTypeResource dokumenttype) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(dokumenttype.getSystemId()) && !isEmpty(dokumenttype.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(dokumenttype.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(DokumentTypeResource dokumenttype) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(dokumenttype.getSystemId()) && !isEmpty(dokumenttype.getSystemId().getIdentifikatorverdi())) {
            builder.add(dokumenttype.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

