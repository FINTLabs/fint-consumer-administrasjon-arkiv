package no.fint.consumer.models.klasse;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.KlasseResource;
import no.fint.model.resource.administrasjon.arkiv.KlasseResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class KlasseLinker extends FintLinker<KlasseResource> {

    public KlasseLinker() {
        super(KlasseResource.class);
    }

    public void mapLinks(KlasseResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KlasseResources toResources(Collection<KlasseResource> collection) {
        KlasseResources resources = new KlasseResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KlasseResource klasse) {
        if (!isNull(klasse.getKlasseId()) && !isEmpty(klasse.getKlasseId().getIdentifikatorverdi())) {
            return createHrefWithId(klasse.getKlasseId().getIdentifikatorverdi(), "klasseid");
        }
        if (!isNull(klasse.getSystemId()) && !isEmpty(klasse.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(klasse.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(KlasseResource klasse) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(klasse.getKlasseId()) && !isEmpty(klasse.getKlasseId().getIdentifikatorverdi())) {
            builder.add(klasse.getKlasseId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(klasse.getSystemId()) && !isEmpty(klasse.getSystemId().getIdentifikatorverdi())) {
            builder.add(klasse.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

