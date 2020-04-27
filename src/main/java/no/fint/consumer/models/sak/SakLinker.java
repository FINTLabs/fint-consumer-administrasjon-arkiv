package no.fint.consumer.models.sak;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.SakResource;
import no.fint.model.resource.administrasjon.arkiv.SakResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class SakLinker extends FintLinker<SakResource> {

    public SakLinker() {
        super(SakResource.class);
    }

    public void mapLinks(SakResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SakResources toResources(Collection<SakResource> collection) {
        SakResources resources = new SakResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(SakResource sak) {
        return getAllSelfHrefs(sak).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(SakResource sak) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(sak.getMappeId()) && !isEmpty(sak.getMappeId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(sak.getMappeId().getIdentifikatorverdi(), "mappeid"));
        }
        if (!isNull(sak.getSystemId()) && !isEmpty(sak.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(sak.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(SakResource sak) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(sak.getMappeId()) && !isEmpty(sak.getMappeId().getIdentifikatorverdi())) {
            builder.add(sak.getMappeId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(sak.getSystemId()) && !isEmpty(sak.getSystemId().getIdentifikatorverdi())) {
            builder.add(sak.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

