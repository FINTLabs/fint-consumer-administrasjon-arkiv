package no.fint.consumer.config;

import com.google.common.collect.ImmutableMap;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.model.administrasjon.arkiv.*;

import java.util.Map;

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
			.put(Merknadstype.class.getName(), contextPath + RestEndpoints.MERKNADSTYPE)
			.put(Part.class.getName(), contextPath + RestEndpoints.PART)
			.put(PartRolle.class.getName(), contextPath + RestEndpoints.PARTROLLE)
			.put(Sak.class.getName(), contextPath + RestEndpoints.SAK)
			.put(Saksstatus.class.getName(), contextPath + RestEndpoints.SAKSSTATUS)
			.put(TilknyttetRegistreringSom.class.getName(), contextPath + RestEndpoints.TILKNYTTETREGISTRERINGSOM)
			.put(Skjermingshjemmel.class.getName(), contextPath + RestEndpoints.SKJERMINGSHJEMMEL)
			.put(Tilgangsrestriksjon.class.getName(), contextPath + RestEndpoints.TILGANGSRESTRIKSJON)
			.put(Variantformat.class.getName(), contextPath + RestEndpoints.VARIANTFORMAT)
			/* .put(TODO,TODO) */
			.build();
	}

}
