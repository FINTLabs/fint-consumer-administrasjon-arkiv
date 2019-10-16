package no.fint.consumer.models.journalposttype;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.JournalpostTypeResource;
import no.fint.model.resource.administrasjon.arkiv.JournalpostTypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class JournalpostTypeLinker extends FintLinker<JournalpostTypeResource> {

    public JournalpostTypeLinker() {
        super(JournalpostTypeResource.class);
    }

    public void mapLinks(JournalpostTypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public JournalpostTypeResources toResources(Collection<JournalpostTypeResource> collection) {
        JournalpostTypeResources resources = new JournalpostTypeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(JournalpostTypeResource journalposttype) {
        if (!isNull(journalposttype.getSystemId()) && !isEmpty(journalposttype.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(journalposttype.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(JournalpostTypeResource journalposttype) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(journalposttype.getSystemId()) && !isEmpty(journalposttype.getSystemId().getIdentifikatorverdi())) {
            builder.add(journalposttype.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

