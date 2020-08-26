package no.fint.consumer.models.journalstatus;

import no.fint.model.resource.administrasjon.arkiv.JournalStatusResource;
import no.fint.model.resource.administrasjon.arkiv.JournalStatusResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class JournalStatusLinker extends FintLinker<JournalStatusResource> {

    public JournalStatusLinker() {
        super(JournalStatusResource.class);
    }

    public void mapLinks(JournalStatusResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public JournalStatusResources toResources(Collection<JournalStatusResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public JournalStatusResources toResources(Stream<JournalStatusResource> stream, int offset, int size, int totalItems) {
        JournalStatusResources resources = new JournalStatusResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(JournalStatusResource journalstatus) {
        return getAllSelfHrefs(journalstatus).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(JournalStatusResource journalstatus) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(journalstatus.getSystemId()) && !isEmpty(journalstatus.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(journalstatus.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(JournalStatusResource journalstatus) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(journalstatus.getSystemId()) && !isEmpty(journalstatus.getSystemId().getIdentifikatorverdi())) {
            builder.add(journalstatus.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

