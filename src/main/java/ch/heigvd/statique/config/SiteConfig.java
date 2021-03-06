package ch.heigvd.statique.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class SiteConfig {
    @Getter @Setter @NonNull
    private String site;
    @Getter @Setter @NonNull
    private String title;
    @Getter @Setter @NonNull
    private String domain;
    @Getter @Setter @NonNull
    private String property;
    @Getter @Setter @NonNull
    private Date date;

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private final ObjectMapper MAPPER;
    private final YAMLFactory YF = new YAMLFactory();

    /**
     * Constructor
     * @param site
     * @param title
     * @param domain
     * @param property
     */
    SiteConfig(String site, String title, String domain, String property, Date date) {
        if(site.isEmpty())
            throw new IllegalArgumentException("Site can't be empty");
        if(title.isEmpty())
            throw new IllegalArgumentException("Title can't be empty");
        if(domain.isEmpty())
            throw new IllegalArgumentException("Domain can't be empty");
        if(property.isEmpty())
            throw new IllegalArgumentException("Property can't be empty");
        if(date ==  null)
            throw new IllegalArgumentException("Date can't be empty");

        this.site = site;
        this.title = title;
        this.domain = domain;
        this.property = property;
        this.date = date;

        YF.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        MAPPER = new ObjectMapper(YF);
        MAPPER.enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        MAPPER.setDateFormat(DATE_FORMAT);


    }

    /**
     * Default constructor
     */
    public SiteConfig() {

        this("www.myStaticSite.ch", "My Static Site",
                "My Domain", "The properties of my static site", new Date());
    }


    /**
     * @return une string avec la config
     */
    public String toString(){
        return "site: " + site + "\n"
                + "title: " + title + "\n"
                + "domain: " + domain + "\n"
                + "property: " + property + "\n"
                + "date: " + date + "\n"
                ;
    }

    /**
     * Charge la config dans une instance de siteConfig depuis un fichier yaml
     * @param filePath, le chemin vers le fichier
     * @return la config nouvellement créée
     * @throws IOException
     */
    public static SiteConfig loadFromDocument(Path filePath) throws IOException {

        SiteConfig sc = new SiteConfig();
        return sc.MAPPER.readValue(new File(filePath.toString()), SiteConfig.class);
    }

    /**
     * Ecrit une config depuis un objet java
     * @param filePath
     * @throws IOException
     */
    public void writeConfiguration(Path filePath) throws IOException{
        MAPPER.writeValue(filePath.toFile(), this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteConfig that = (SiteConfig) o;
        return Objects.equals(getSite(), that.getSite()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDomain(), that.getDomain()) &&
                Objects.equals(getProperty(), that.getProperty()) &&
                Objects.equals(getDate(), that.getDate());
    }
}

