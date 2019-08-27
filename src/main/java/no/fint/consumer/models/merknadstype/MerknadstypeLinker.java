package no.fint.consumer.models.merknadstype;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.MerknadstypeResource;
import no.fint.model.resource.administrasjon.arkiv.MerknadstypeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class MerknadstypeLinker extends FintLinker<MerknadstypeResource> {

    public MerknadstypeLinker() {
        super(MerknadstypeResource.class);
    }

    public void mapLinks(MerknadstypeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public MerknadstypeResources toResources(Collection<MerknadstypeResource> collection) {
        MerknadstypeResources resources = new MerknadstypeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(MerknadstypeResource merknadstype) {
        if (!isNull(merknadstype.getSystemId()) && !isEmpty(merknadstype.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(merknadstype.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

