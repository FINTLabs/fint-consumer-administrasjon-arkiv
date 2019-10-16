package no.fint.consumer.models.korrespondansepart;

import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.arkiv.KorrespondansepartResource;
import no.fint.model.resource.administrasjon.arkiv.KorrespondansepartResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class KorrespondansepartLinker extends FintLinker<KorrespondansepartResource> {

    public KorrespondansepartLinker() {
        super(KorrespondansepartResource.class);
    }

    public void mapLinks(KorrespondansepartResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KorrespondansepartResources toResources(Collection<KorrespondansepartResource> collection) {
        KorrespondansepartResources resources = new KorrespondansepartResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KorrespondansepartResource korrespondansepart) {
        if (!isNull(korrespondansepart.getFodselsnummer()) && !isEmpty(korrespondansepart.getFodselsnummer().getIdentifikatorverdi())) {
            return createHrefWithId(korrespondansepart.getFodselsnummer().getIdentifikatorverdi(), "fodselsnummer");
        }
        if (!isNull(korrespondansepart.getOrganisasjonsnummer()) && !isEmpty(korrespondansepart.getOrganisasjonsnummer().getIdentifikatorverdi())) {
            return createHrefWithId(korrespondansepart.getOrganisasjonsnummer().getIdentifikatorverdi(), "organisasjonsnummer");
        }
        if (!isNull(korrespondansepart.getSystemId()) && !isEmpty(korrespondansepart.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(korrespondansepart.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }

    int[] hashCodes(KorrespondansepartResource korrespondansepart) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(korrespondansepart.getFodselsnummer()) && !isEmpty(korrespondansepart.getFodselsnummer().getIdentifikatorverdi())) {
            builder.add(korrespondansepart.getFodselsnummer().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(korrespondansepart.getOrganisasjonsnummer()) && !isEmpty(korrespondansepart.getOrganisasjonsnummer().getIdentifikatorverdi())) {
            builder.add(korrespondansepart.getOrganisasjonsnummer().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(korrespondansepart.getSystemId()) && !isEmpty(korrespondansepart.getSystemId().getIdentifikatorverdi())) {
            builder.add(korrespondansepart.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

