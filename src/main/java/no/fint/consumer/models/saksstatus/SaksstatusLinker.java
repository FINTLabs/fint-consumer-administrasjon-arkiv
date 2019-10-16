package no.fint.consumer.models.saksstatus;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.SaksstatusResource;
import no.fint.model.resource.administrasjon.arkiv.SaksstatusResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class SaksstatusLinker extends FintLinker<SaksstatusResource> {

    public SaksstatusLinker() {
        super(SaksstatusResource.class);
    }

    public void mapLinks(SaksstatusResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SaksstatusResources toResources(Collection<SaksstatusResource> collection) {
        SaksstatusResources resources = new SaksstatusResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(SaksstatusResource saksstatus) {
        if (!isNull(saksstatus.getSystemId()) && !isEmpty(saksstatus.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(saksstatus.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(SaksstatusResource saksstatus) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(saksstatus.getSystemId()) && !isEmpty(saksstatus.getSystemId().getIdentifikatorverdi())) {
            builder.add(saksstatus.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

