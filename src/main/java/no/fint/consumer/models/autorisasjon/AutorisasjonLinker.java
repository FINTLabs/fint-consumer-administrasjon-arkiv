package no.fint.consumer.models.autorisasjon;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.AutorisasjonResource;
import no.fint.model.resource.administrasjon.arkiv.AutorisasjonResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class AutorisasjonLinker extends FintLinker<AutorisasjonResource> {

    public AutorisasjonLinker() {
        super(AutorisasjonResource.class);
    }

    public void mapLinks(AutorisasjonResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public AutorisasjonResources toResources(Collection<AutorisasjonResource> collection) {
        AutorisasjonResources resources = new AutorisasjonResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(AutorisasjonResource autorisasjon) {
        if (!isNull(autorisasjon.getSystemId()) && !isEmpty(autorisasjon.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(autorisasjon.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(AutorisasjonResource autorisasjon) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(autorisasjon.getSystemId()) && !isEmpty(autorisasjon.getSystemId().getIdentifikatorverdi())) {
            builder.add(autorisasjon.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}
