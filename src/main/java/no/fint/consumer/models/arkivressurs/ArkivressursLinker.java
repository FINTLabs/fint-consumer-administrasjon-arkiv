package no.fint.consumer.models.arkivressurs;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.ArkivressursResource;
import no.fint.model.resource.administrasjon.arkiv.ArkivressursResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class ArkivressursLinker extends FintLinker<ArkivressursResource> {

    public ArkivressursLinker() {
        super(ArkivressursResource.class);
    }

    public void mapLinks(ArkivressursResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ArkivressursResources toResources(Collection<ArkivressursResource> collection) {
        ArkivressursResources resources = new ArkivressursResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(ArkivressursResource arkivressurs) {
        if (!isNull(arkivressurs.getKildesystemId()) && !isEmpty(arkivressurs.getKildesystemId().getIdentifikatorverdi())) {
            return createHrefWithId(arkivressurs.getKildesystemId().getIdentifikatorverdi(), "kildesystemid");
        }
        if (!isNull(arkivressurs.getSystemId()) && !isEmpty(arkivressurs.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(arkivressurs.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(ArkivressursResource arkivressurs) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(arkivressurs.getKildesystemId()) && !isEmpty(arkivressurs.getKildesystemId().getIdentifikatorverdi())) {
            builder.add(arkivressurs.getKildesystemId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(arkivressurs.getSystemId()) && !isEmpty(arkivressurs.getSystemId().getIdentifikatorverdi())) {
            builder.add(arkivressurs.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

