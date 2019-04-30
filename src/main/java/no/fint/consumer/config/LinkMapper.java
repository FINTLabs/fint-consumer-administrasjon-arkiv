package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

import no.fint.model.administrasjon.arkiv.*;
import no.fint.model.felles.*;

public class LinkMapper {

	public static Map<String, String> linkMapper(String contextPath) {
		return ImmutableMap.<String,String>builder()
			.put(Dokumentfil.class.getName(), contextPath + RestEndpoints.DOKUMENTFIL)
			.put(DokumentStatus.class.getName(), contextPath + RestEndpoints.DOKUMENTSTATUS)
			.put(DokumentType.class.getName(), contextPath + RestEndpoints.DOKUMENTTYPE)
			.put(JournalpostType.class.getName(), contextPath + RestEndpoints.JOURNALPOSTTYPE)
			.put(JournalStatus.class.getName(), contextPath + RestEndpoints.JOURNALSTATUS)
			.put(Korrespondansepart.class.getName(), contextPath + RestEndpoints.KORRESPONDANSEPART)
			.put(KorrespondansepartType.class.getName(), contextPath + RestEndpoints.KORRESPONDANSEPARTTYPE)
			.put(Sak.class.getName(), contextPath + RestEndpoints.SAK)
			.put(Sakspart.class.getName(), contextPath + RestEndpoints.SAKSPART)
			.put(SakspartRolle.class.getName(), contextPath + RestEndpoints.SAKSPARTROLLE)
			.put(Saksstatus.class.getName(), contextPath + RestEndpoints.SAKSSTATUS)
			.put(TilknyttetRegistreringSom.class.getName(), contextPath + RestEndpoints.TILKNYTTETREGISTRERINGSOM)
			.put(Variantformat.class.getName(), contextPath + RestEndpoints.VARIANTFORMAT)
			/* .put(TODO,TODO) */
			.build();
	}

}
