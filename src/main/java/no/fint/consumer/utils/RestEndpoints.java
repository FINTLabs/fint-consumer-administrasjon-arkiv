package no.fint.consumer.utils;

import java.util.Set;

public enum RestEndpoints {
    ;

    public static final String ADMIN = "/admin";
    public static final String ADMINISTRATIVENHET = "/administrativenhet";
    public static final String ARKIVDEL = "/arkivdel";
    public static final String ARKIVRESSURS = "/arkivressurs";
    public static final String AUTORISASJON = "/autorisasjon";
    public static final String DOKUMENTFIL = "/dokumentfil";
    public static final String DOKUMENTSTATUS = "/dokumentstatus";
    public static final String DOKUMENTTYPE = "/dokumenttype";
    public static final String JOURNALPOSTTYPE = "/journalposttype";
    public static final String JOURNALSTATUS = "/journalstatus";
    public static final String KLASSE = "/klasse";
    public static final String KLASSIFIKASJONSSYSTEM = "/klassifikasjonssystem";
    public static final String KORRESPONDANSEPART = "/korrespondansepart";
    public static final String KORRESPONDANSEPARTTYPE = "/korrespondanseparttype";
    public static final String MERKNADSTYPE = "/merknadstype";
    public static final String PART = "/part";
    public static final String PARTROLLE = "/partrolle";
    public static final String ROLLE = "/rolle";
    public static final String SAK = "/sak";
    public static final String SAKSSTATUS = "/saksstatus";
    public static final String SKJERMINGSHJEMMEL = "/skjermingshjemmel";
    public static final String TILGANG = "/tilgang";
    public static final String TILGANGSRESTRIKSJON = "/tilgangsrestriksjon";
    public static final String TILKNYTTETREGISTRERINGSOM = "/tilknyttetregistreringsom";
    public static final String VARIANTFORMAT = "/variantformat";

    public static final String[] ALL_ENDPOINTS = new String[]{
            ADMINISTRATIVENHET,
            ARKIVDEL,
            ARKIVRESSURS,
            AUTORISASJON,
            DOKUMENTFIL,
            DOKUMENTSTATUS,
            DOKUMENTTYPE,
            JOURNALPOSTTYPE,
            JOURNALSTATUS,
            KLASSE,
            KLASSIFIKASJONSSYSTEM,
            KORRESPONDANSEPART,
            KORRESPONDANSEPARTTYPE,
            MERKNADSTYPE,
            PART,
            PARTROLLE,
            SAK,
            SAKSSTATUS,
            SKJERMINGSHJEMMEL,
            TILGANG,
            TILGANGSRESTRIKSJON,
            TILKNYTTETREGISTRERINGSOM,
            VARIANTFORMAT,
            ADMIN
    };
}
