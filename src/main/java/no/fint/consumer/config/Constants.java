
package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "administrasjon-arkiv";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_ADMINISTRATIVENHET = "${fint.consumer.cache.initialDelay.administrativenhet:900000}";
    public static final String CACHE_FIXEDRATE_ADMINISTRATIVENHET = "${fint.consumer.cache.fixedRate.administrativenhet:900000}";
    
    public static final String CACHE_INITIALDELAY_ARKIVDEL = "${fint.consumer.cache.initialDelay.arkivdel:960000}";
    public static final String CACHE_FIXEDRATE_ARKIVDEL = "${fint.consumer.cache.fixedRate.arkivdel:900000}";
    
    public static final String CACHE_INITIALDELAY_ARKIVRESSURS = "${fint.consumer.cache.initialDelay.arkivressurs:1020000}";
    public static final String CACHE_FIXEDRATE_ARKIVRESSURS = "${fint.consumer.cache.fixedRate.arkivressurs:900000}";
    
    public static final String CACHE_INITIALDELAY_AUTORISASJON = "${fint.consumer.cache.initialDelay.autorisasjon:1080000}";
    public static final String CACHE_FIXEDRATE_AUTORISASJON = "${fint.consumer.cache.fixedRate.autorisasjon:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTFIL = "${fint.consumer.cache.initialDelay.dokumentfil:1140000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTFIL = "${fint.consumer.cache.fixedRate.dokumentfil:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTSTATUS = "${fint.consumer.cache.initialDelay.dokumentstatus:1200000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTSTATUS = "${fint.consumer.cache.fixedRate.dokumentstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTTYPE = "${fint.consumer.cache.initialDelay.dokumenttype:1260000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTTYPE = "${fint.consumer.cache.fixedRate.dokumenttype:900000}";
    
    public static final String CACHE_INITIALDELAY_JOURNALPOSTTYPE = "${fint.consumer.cache.initialDelay.journalposttype:1320000}";
    public static final String CACHE_FIXEDRATE_JOURNALPOSTTYPE = "${fint.consumer.cache.fixedRate.journalposttype:900000}";
    
    public static final String CACHE_INITIALDELAY_JOURNALSTATUS = "${fint.consumer.cache.initialDelay.journalstatus:1380000}";
    public static final String CACHE_FIXEDRATE_JOURNALSTATUS = "${fint.consumer.cache.fixedRate.journalstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_KLASSE = "${fint.consumer.cache.initialDelay.klasse:1440000}";
    public static final String CACHE_FIXEDRATE_KLASSE = "${fint.consumer.cache.fixedRate.klasse:900000}";
    
    public static final String CACHE_INITIALDELAY_KLASSIFIKASJONSSYSTEM = "${fint.consumer.cache.initialDelay.klassifikasjonssystem:1500000}";
    public static final String CACHE_FIXEDRATE_KLASSIFIKASJONSSYSTEM = "${fint.consumer.cache.fixedRate.klassifikasjonssystem:900000}";
    
    public static final String CACHE_INITIALDELAY_KORRESPONDANSEPART = "${fint.consumer.cache.initialDelay.korrespondansepart:1560000}";
    public static final String CACHE_FIXEDRATE_KORRESPONDANSEPART = "${fint.consumer.cache.fixedRate.korrespondansepart:900000}";
    
    public static final String CACHE_INITIALDELAY_KORRESPONDANSEPARTTYPE = "${fint.consumer.cache.initialDelay.korrespondanseparttype:1620000}";
    public static final String CACHE_FIXEDRATE_KORRESPONDANSEPARTTYPE = "${fint.consumer.cache.fixedRate.korrespondanseparttype:900000}";
    
    public static final String CACHE_INITIALDELAY_MERKNADSTYPE = "${fint.consumer.cache.initialDelay.merknadstype:1680000}";
    public static final String CACHE_FIXEDRATE_MERKNADSTYPE = "${fint.consumer.cache.fixedRate.merknadstype:900000}";
    
    public static final String CACHE_INITIALDELAY_PART = "${fint.consumer.cache.initialDelay.part:1740000}";
    public static final String CACHE_FIXEDRATE_PART = "${fint.consumer.cache.fixedRate.part:900000}";
    
    public static final String CACHE_INITIALDELAY_PARTROLLE = "${fint.consumer.cache.initialDelay.partrolle:1800000}";
    public static final String CACHE_FIXEDRATE_PARTROLLE = "${fint.consumer.cache.fixedRate.partrolle:900000}";
    
    public static final String CACHE_INITIALDELAY_ROLLE = "${fint.consumer.cache.initialDelay.rolle:1860000}";
    public static final String CACHE_FIXEDRATE_ROLLE = "${fint.consumer.cache.fixedRate.rolle:900000}";
    
    public static final String CACHE_INITIALDELAY_SAK = "${fint.consumer.cache.initialDelay.sak:1920000}";
    public static final String CACHE_FIXEDRATE_SAK = "${fint.consumer.cache.fixedRate.sak:900000}";
    
    public static final String CACHE_INITIALDELAY_SAKSSTATUS = "${fint.consumer.cache.initialDelay.saksstatus:1980000}";
    public static final String CACHE_FIXEDRATE_SAKSSTATUS = "${fint.consumer.cache.fixedRate.saksstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_SKJERMINGSHJEMMEL = "${fint.consumer.cache.initialDelay.skjermingshjemmel:2040000}";
    public static final String CACHE_FIXEDRATE_SKJERMINGSHJEMMEL = "${fint.consumer.cache.fixedRate.skjermingshjemmel:900000}";
    
    public static final String CACHE_INITIALDELAY_TILGANG = "${fint.consumer.cache.initialDelay.tilgang:2100000}";
    public static final String CACHE_FIXEDRATE_TILGANG = "${fint.consumer.cache.fixedRate.tilgang:900000}";
    
    public static final String CACHE_INITIALDELAY_TILGANGSRESTRIKSJON = "${fint.consumer.cache.initialDelay.tilgangsrestriksjon:2160000}";
    public static final String CACHE_FIXEDRATE_TILGANGSRESTRIKSJON = "${fint.consumer.cache.fixedRate.tilgangsrestriksjon:900000}";
    
    public static final String CACHE_INITIALDELAY_TILKNYTTETREGISTRERINGSOM = "${fint.consumer.cache.initialDelay.tilknyttetregistreringsom:2220000}";
    public static final String CACHE_FIXEDRATE_TILKNYTTETREGISTRERINGSOM = "${fint.consumer.cache.fixedRate.tilknyttetregistreringsom:900000}";
    
    public static final String CACHE_INITIALDELAY_VARIANTFORMAT = "${fint.consumer.cache.initialDelay.variantformat:2280000}";
    public static final String CACHE_FIXEDRATE_VARIANTFORMAT = "${fint.consumer.cache.fixedRate.variantformat:900000}";
    

}
