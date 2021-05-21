package ch.heigvd.statique.config;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SiteConfigTest {

    @Test
    public void oneNullArgumentShouldThrow(){
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new SiteConfig("", "a", "a", "a", new Date());
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new SiteConfig("a", "", "a", "a", new Date());
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new SiteConfig("a", "a", "", "a", new Date());
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new SiteConfig("a", "a", "a", "", new Date());
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new SiteConfig("a", "a", "a", "a", null);
                });
    }

    @Test
    public void wrongDocumentPathMakesLoadCrash(){
        assertThrows(Exception.class, ()  -> {
            SiteConfig.loadFromDocument(Paths.get("random"));
        });
    }

    @Test
    public void setterWithNullArgumentShouldThrow() throws IOException {
        SiteConfig sc = new SiteConfig("a", "a", "a", "a", new Date());
        assertThrows(NullPointerException.class, () -> {
            sc.setTitle(null);
        });
        assertThrows(NullPointerException.class, () -> {
            sc.setDomain(null);
        });
        assertThrows(NullPointerException.class, () -> {
            sc.setProperty(null);
        });
        assertThrows(NullPointerException.class, () -> {
            sc.setSite(null);
        });
        assertThrows(NullPointerException.class, () -> {
            sc.setDate(null);
        });
    }


}