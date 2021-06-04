package ch.heigvd.statique.config;

import org.junit.jupiter.api.Test;
import java.io.IOException;
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

    @Test
    void checkIfEqualsWorks(){
        Date date = new Date();
        SiteConfig sc1 = new SiteConfig("a", "a", "a", "a", date);
        SiteConfig sc2 = new SiteConfig("a", "a", "a", "a", date);
        SiteConfig sc3 = new SiteConfig("b", "a", "a", "a", date);
        assertEquals(sc1, sc2);
        assertNotEquals(sc1, sc3);
    }

    @Test
    void checkEqualsOnObjectEqualsHimself(){
        Date date = new Date();
        SiteConfig sc1 = new SiteConfig("a", "a", "a", "a", date);
        assertEquals(sc1, sc1);
    }

    @Test
    void checkEqualsWithNullObject(){
        Date date = new Date();
        SiteConfig sc1 = new SiteConfig("a", "a", "a", "a", date);
        assertNotEquals(sc1, null);
    }

    @Test
    void checkEqualsWithDifferentObject(){
        Date date = new Date();
        SiteConfig sc1 = new SiteConfig("a", "a", "a", "a", date);
        assertNotEquals(sc1, new Integer(1));
    }


}