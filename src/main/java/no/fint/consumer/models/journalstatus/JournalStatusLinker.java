package no.fint.consumer.models.journalstatus;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.JournalStatusResource;
import no.fint.model.resource.administrasjon.arkiv.JournalStatusResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

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
        JournalStatusResources resources = new JournalStatusResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(JournalStatusResource journalstatus) {
        if (!isNull(journalstatus.getSystemId()) && !isEmpty(journalstatus.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(journalstatus.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(JournalStatusResource journalstatus) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(journalstatus.getSystemId()) && !isEmpty(journalstatus.getSystemId().getIdentifikatorverdi())) {
            builder.add(journalstatus.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

