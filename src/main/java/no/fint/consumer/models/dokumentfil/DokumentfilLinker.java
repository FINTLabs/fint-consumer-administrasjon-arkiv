package no.fint.consumer.models.dokumentfil;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.DokumentfilResource;
import no.fint.model.resource.administrasjon.arkiv.DokumentfilResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class DokumentfilLinker extends FintLinker<DokumentfilResource> {

    public DokumentfilLinker() {
        super(DokumentfilResource.class);
    }

    public void mapLinks(DokumentfilResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DokumentfilResources toResources(Collection<DokumentfilResource> collection) {
        DokumentfilResources resources = new DokumentfilResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(DokumentfilResource dokumentfil) {
        return getAllSelfHrefs(dokumentfil).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(DokumentfilResource dokumentfil) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(dokumentfil.getSystemId()) && !isEmpty(dokumentfil.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(dokumentfil.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(DokumentfilResource dokumentfil) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(dokumentfil.getSystemId()) && !isEmpty(dokumentfil.getSystemId().getIdentifikatorverdi())) {
            builder.add(dokumentfil.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

