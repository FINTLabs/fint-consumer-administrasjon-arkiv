package no.fint.consumer.models.korrespondanseparttype;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.KorrespondansepartTypeResource;
import no.fint.model.resource.administrasjon.arkiv.KorrespondansepartTypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class KorrespondansepartTypeLinker extends FintLinker<KorrespondansepartTypeResource> {

    public KorrespondansepartTypeLinker() {
        super(KorrespondansepartTypeResource.class);
    }

    public void mapLinks(KorrespondansepartTypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KorrespondansepartTypeResources toResources(Collection<KorrespondansepartTypeResource> collection) {
        KorrespondansepartTypeResources resources = new KorrespondansepartTypeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KorrespondansepartTypeResource korrespondanseparttype) {
        return getAllSelfHrefs(korrespondanseparttype).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(KorrespondansepartTypeResource korrespondanseparttype) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(korrespondanseparttype.getSystemId()) && !isEmpty(korrespondanseparttype.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(korrespondanseparttype.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(KorrespondansepartTypeResource korrespondanseparttype) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(korrespondanseparttype.getSystemId()) && !isEmpty(korrespondanseparttype.getSystemId().getIdentifikatorverdi())) {
            builder.add(korrespondanseparttype.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

