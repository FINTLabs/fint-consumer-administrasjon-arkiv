
package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "administrasjon-arkiv";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_DOKUMENTBESKRIVELSE = "${fint.consumer.cache.initialDelay.dokumentbeskrivelse:60000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTBESKRIVELSE = "${fint.consumer.cache.fixedRate.dokumentbeskrivelse:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTOBJEKT = "${fint.consumer.cache.initialDelay.dokumentobjekt:70000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTOBJEKT = "${fint.consumer.cache.fixedRate.dokumentobjekt:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTSTATUS = "${fint.consumer.cache.initialDelay.dokumentstatus:80000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTSTATUS = "${fint.consumer.cache.fixedRate.dokumentstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_DOKUMENTTYPE = "${fint.consumer.cache.initialDelay.dokumenttype:90000}";
    public static final String CACHE_FIXEDRATE_DOKUMENTTYPE = "${fint.consumer.cache.fixedRate.dokumenttype:900000}";
    
    public static final String CACHE_INITIALDELAY_JOURNALPOST = "${fint.consumer.cache.initialDelay.journalpost:100000}";
    public static final String CACHE_FIXEDRATE_JOURNALPOST = "${fint.consumer.cache.fixedRate.journalpost:900000}";
    
    public static final String CACHE_INITIALDELAY_JOURNALPOSTTYPE = "${fint.consumer.cache.initialDelay.journalposttype:110000}";
    public static final String CACHE_FIXEDRATE_JOURNALPOSTTYPE = "${fint.consumer.cache.fixedRate.journalposttype:900000}";
    
    public static final String CACHE_INITIALDELAY_JOURNALSTATUS = "${fint.consumer.cache.initialDelay.journalstatus:120000}";
    public static final String CACHE_FIXEDRATE_JOURNALSTATUS = "${fint.consumer.cache.fixedRate.journalstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_KORRESPONDANSEPART = "${fint.consumer.cache.initialDelay.korrespondansepart:130000}";
    public static final String CACHE_FIXEDRATE_KORRESPONDANSEPART = "${fint.consumer.cache.fixedRate.korrespondansepart:900000}";
    
    public static final String CACHE_INITIALDELAY_KORRESPONDANSEPARTTYPE = "${fint.consumer.cache.initialDelay.korrespondanseparttype:140000}";
    public static final String CACHE_FIXEDRATE_KORRESPONDANSEPARTTYPE = "${fint.consumer.cache.fixedRate.korrespondanseparttype:900000}";
    
    public static final String CACHE_INITIALDELAY_SAKSPART = "${fint.consumer.cache.initialDelay.sakspart:150000}";
    public static final String CACHE_FIXEDRATE_SAKSPART = "${fint.consumer.cache.fixedRate.sakspart:900000}";
    
    public static final String CACHE_INITIALDELAY_SAKSPARTROLLE = "${fint.consumer.cache.initialDelay.sakspartrolle:160000}";
    public static final String CACHE_FIXEDRATE_SAKSPARTROLLE = "${fint.consumer.cache.fixedRate.sakspartrolle:900000}";
    
    public static final String CACHE_INITIALDELAY_SAKSSTATUS = "${fint.consumer.cache.initialDelay.saksstatus:170000}";
    public static final String CACHE_FIXEDRATE_SAKSSTATUS = "${fint.consumer.cache.fixedRate.saksstatus:900000}";
    
    public static final String CACHE_INITIALDELAY_TILKNYTTETREGISTRERINGSOM = "${fint.consumer.cache.initialDelay.tilknyttetregistreringsom:180000}";
    public static final String CACHE_FIXEDRATE_TILKNYTTETREGISTRERINGSOM = "${fint.consumer.cache.fixedRate.tilknyttetregistreringsom:900000}";
    
    public static final String CACHE_INITIALDELAY_VARIANTFORMAT = "${fint.consumer.cache.initialDelay.variantformat:190000}";
    public static final String CACHE_FIXEDRATE_VARIANTFORMAT = "${fint.consumer.cache.fixedRate.variantformat:900000}";
    

}
