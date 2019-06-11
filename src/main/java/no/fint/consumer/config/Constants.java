
package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "administrasjon-arkiv";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_DOKUMENTFIL = "${fint.consumer.cache.initialDelay.dokumentfil:60000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTFIL = "${fint.consumer.cache.fixedRate.dokumentfil:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTSTATUS = "${fint.consumer.cache.initialDelay.dokumentstatus:70000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTSTATUS = "${fint.consumer.cache.fixedRate.dokumentstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTTYPE = "${fint.consumer.cache.initialDelay.dokumenttype:80000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTTYPE = "${fint.consumer.cache.fixedRate.dokumenttype:900000}";
    
    public static final String CACHE_INITIALDELAY_JOURNALPOSTTYPE = "${fint.consumer.cache.initialDelay.journalposttype:90000}";
    public static final String CACHE_FIXEDRATE_JOURNALPOSTTYPE = "${fint.consumer.cache.fixedRate.journalposttype:900000}";
    
    public static final String CACHE_INITIALDELAY_JOURNALSTATUS = "${fint.consumer.cache.initialDelay.journalstatus:100000}";
    public static final String CACHE_FIXEDRATE_JOURNALSTATUS = "${fint.consumer.cache.fixedRate.journalstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_KORRESPONDANSEPART = "${fint.consumer.cache.initialDelay.korrespondansepart:110000}";
    public static final String CACHE_FIXEDRATE_KORRESPONDANSEPART = "${fint.consumer.cache.fixedRate.korrespondansepart:900000}";
    
    public static final String CACHE_INITIALDELAY_KORRESPONDANSEPARTTYPE = "${fint.consumer.cache.initialDelay.korrespondanseparttype:120000}";
    public static final String CACHE_FIXEDRATE_KORRESPONDANSEPARTTYPE = "${fint.consumer.cache.fixedRate.korrespondanseparttype:900000}";
    
    public static final String CACHE_INITIALDELAY_PART = "${fint.consumer.cache.initialDelay.part:130000}";
    public static final String CACHE_FIXEDRATE_PART = "${fint.consumer.cache.fixedRate.part:900000}";
    
    public static final String CACHE_INITIALDELAY_PARTROLLE = "${fint.consumer.cache.initialDelay.partrolle:140000}";
    public static final String CACHE_FIXEDRATE_PARTROLLE = "${fint.consumer.cache.fixedRate.partrolle:900000}";
    
    public static final String CACHE_INITIALDELAY_SAK = "${fint.consumer.cache.initialDelay.sak:150000}";
    public static final String CACHE_FIXEDRATE_SAK = "${fint.consumer.cache.fixedRate.sak:900000}";
    
    public static final String CACHE_INITIALDELAY_SAKSSTATUS = "${fint.consumer.cache.initialDelay.saksstatus:160000}";
    public static final String CACHE_FIXEDRATE_SAKSSTATUS = "${fint.consumer.cache.fixedRate.saksstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_SKJERMINGSHJEMMEL = "${fint.consumer.cache.initialDelay.skjermingshjemmel:170000}";
    public static final String CACHE_FIXEDRATE_SKJERMINGSHJEMMEL = "${fint.consumer.cache.fixedRate.skjermingshjemmel:900000}";
    
    public static final String CACHE_INITIALDELAY_TILGANGSRESTRIKSJON = "${fint.consumer.cache.initialDelay.tilgangsrestriksjon:180000}";
    public static final String CACHE_FIXEDRATE_TILGANGSRESTRIKSJON = "${fint.consumer.cache.fixedRate.tilgangsrestriksjon:900000}";
    
    public static final String CACHE_INITIALDELAY_TILKNYTTETREGISTRERINGSOM = "${fint.consumer.cache.initialDelay.tilknyttetregistreringsom:190000}";
    public static final String CACHE_FIXEDRATE_TILKNYTTETREGISTRERINGSOM = "${fint.consumer.cache.fixedRate.tilknyttetregistreringsom:900000}";
    
    public static final String CACHE_INITIALDELAY_VARIANTFORMAT = "${fint.consumer.cache.initialDelay.variantformat:200000}";
    public static final String CACHE_FIXEDRATE_VARIANTFORMAT = "${fint.consumer.cache.fixedRate.variantformat:900000}";
    

}
