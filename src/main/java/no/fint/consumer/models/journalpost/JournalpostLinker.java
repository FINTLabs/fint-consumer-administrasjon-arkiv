package no.fint.consumer.models.journalpost;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.JournalpostResource;
import no.fint.model.resource.administrasjon.arkiv.JournalpostResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class JournalpostLinker extends FintLinker<JournalpostResource> {

    public JournalpostLinker() {
        super(JournalpostResource.class);
    }

    public void mapLinks(JournalpostResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public JournalpostResources toResources(Collection<JournalpostResource> collection) {
        JournalpostResources resources = new JournalpostResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(JournalpostResource journalpost) {
        if (!isNull(journalpost.getSystemId()) && !isEmpty(journalpost.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(journalpost.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

