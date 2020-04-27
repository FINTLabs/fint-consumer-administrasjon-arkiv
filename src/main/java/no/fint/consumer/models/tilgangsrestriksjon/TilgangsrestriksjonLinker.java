package no.fint.consumer.models.tilgangsrestriksjon;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.TilgangsrestriksjonResource;
import no.fint.model.resource.administrasjon.arkiv.TilgangsrestriksjonResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class TilgangsrestriksjonLinker extends FintLinker<TilgangsrestriksjonResource> {

    public TilgangsrestriksjonLinker() {
        super(TilgangsrestriksjonResource.class);
    }

    public void mapLinks(TilgangsrestriksjonResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public TilgangsrestriksjonResources toResources(Collection<TilgangsrestriksjonResource> collection) {
        TilgangsrestriksjonResources resources = new TilgangsrestriksjonResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(TilgangsrestriksjonResource tilgangsrestriksjon) {
        return getAllSelfHrefs(tilgangsrestriksjon).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(TilgangsrestriksjonResource tilgangsrestriksjon) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(tilgangsrestriksjon.getSystemId()) && !isEmpty(tilgangsrestriksjon.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(tilgangsrestriksjon.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(TilgangsrestriksjonResource tilgangsrestriksjon) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(tilgangsrestriksjon.getSystemId()) && !isEmpty(tilgangsrestriksjon.getSystemId().getIdentifikatorverdi())) {
            builder.add(tilgangsrestriksjon.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

