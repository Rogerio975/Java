package com.adsearch;

import java.io.*;
import java.util.Properties;

/**
 * Carrega configurações de conexão do AD a partir do arquivo
 * ad-config.properties ou de variáveis de ambiente.
 */
public class ADConfig {

    public String  host;
    public int     port;
    public String  searchBase;
    public String  bindDn;
    public String  bindPassword;
    public boolean useSsl;
    public int     maxResults;

    /** Carrega do arquivo de propriedades */
    public static ADConfig fromFile(String path) throws IOException {
        Properties p = new Properties();
        try (InputStream is = new FileInputStream(path)) {
            p.load(is);
        }
        return fromProperties(p);
    }

    /** Carrega do classpath (src/main/resources/ad-config.properties) */
    public static ADConfig fromClasspath() throws IOException {
        Properties p = new Properties();
        try (InputStream is = ADConfig.class
                .getResourceAsStream("/ad-config.properties")) {
            if (is == null) throw new FileNotFoundException("ad-config.properties não encontrado no classpath");
            p.load(is);
        }
        return fromProperties(p);
    }

    private static ADConfig fromProperties(Properties p) {
        ADConfig c = new ADConfig();
        c.host        = env("AD_HOST",     p.getProperty("ad.host", "dc.empresa.local"));
        c.port        = Integer.parseInt(
                        env("AD_PORT",     p.getProperty("ad.port", "389")));
        c.searchBase  = env("AD_BASE",     p.getProperty("ad.searchBase", "DC=empresa,DC=local"));
        c.bindDn      = env("AD_BIND_DN",  p.getProperty("ad.bindDn", ""));
        c.bindPassword= env("AD_PASSWORD", p.getProperty("ad.bindPassword", ""));
        c.useSsl      = Boolean.parseBoolean(
                        env("AD_SSL",      p.getProperty("ad.ssl", "false")));
        c.maxResults  = Integer.parseInt(
                        env("AD_MAX",      p.getProperty("ad.maxResults", "20")));
        return c;
    }

    /** Prefere variável de ambiente, senão usa o valor do properties */
    private static String env(String envVar, String fallback) {
        String v = System.getenv(envVar);
        return (v != null && !v.isEmpty()) ? v : fallback;
    }

    @Override
    public String toString() {
        return String.format(
            "Host=%s:%d | Base=%s | SSL=%s | MaxResults=%d",
            host, port, searchBase, useSsl, maxResults);
    }
}
