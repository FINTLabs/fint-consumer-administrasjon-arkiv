package no.fint.consumer.models.dokumentstatus;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.DokumentStatusResource;
import no.fint.model.resource.administrasjon.arkiv.DokumentStatusResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class DokumentStatusLinker extends FintLinker<DokumentStatusResource> {

    public DokumentStatusLinker() {
        super(DokumentStatusResource.class);
    }

    public void mapLinks(DokumentStatusResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DokumentStatusResources toResources(Collection<DokumentStatusResource> collection) {
        DokumentStatusResources resources = new DokumentStatusResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(DokumentStatusResource dokumentstatus) {
        if (!isNull(dokumentstatus.getSystemId()) && !isEmpty(dokumentstatus.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(dokumentstatus.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(DokumentStatusResource dokumentstatus) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(dokumentstatus.getSystemId()) && !isEmpty(dokumentstatus.getSystemId().getIdentifikatorverdi())) {
            builder.add(dokumentstatus.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

