package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

import no.fint.model.administrasjon.arkiv.*;
import no.fint.model.felles.*;

public class LinkMapper {

	public static Map<String, String> linkMapper(String contextPath) {
		return ImmutableMap.<String,String>builder()
			.put(Dokumentbeskrivelse.class.getName(), contextPath + RestEndpoints.DOKUMENTBESKRIVELSE)
			.put(Dokumentobjekt.class.getName(), contextPath + RestEndpoints.DOKUMENTOBJEKT)
			.put(Dokumentstatus.class.getName(), contextPath + RestEndpoints.DOKUMENTSTATUS)
			.put(Dokumenttype.class.getName(), contextPath + RestEndpoints.DOKUMENTTYPE)
			.put(Journalpost.class.getName(), contextPath + RestEndpoints.JOURNALPOST)
			.put(Journalposttype.class.getName(), contextPath + RestEndpoints.JOURNALPOSTTYPE)
			.put(Journalstatus.class.getName(), contextPath + RestEndpoints.JOURNALSTATUS)
			.put(Korrespondansepart.class.getName(), contextPath + RestEndpoints.KORRESPONDANSEPART)
			.put(Korrespondanseparttype.class.getName(), contextPath + RestEndpoints.KORRESPONDANSEPARTTYPE)
			.put(Sakspart.class.getName(), contextPath + RestEndpoints.SAKSPART)
			.put(Sakspartrolle.class.getName(), contextPath + RestEndpoints.SAKSPARTROLLE)
			.put(Saksstatus.class.getName(), contextPath + RestEndpoints.SAKSSTATUS)
			.put(Tilknyttetregistreringsom.class.getName(), contextPath + RestEndpoints.TILKNYTTETREGISTRERINGSOM)
			.put(Variantformat.class.getName(), contextPath + RestEndpoints.VARIANTFORMAT)
			/* .put(TODO,TODO) */
			.build();
	}

}
