package ch.heigvd.statique.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SiteConfig {
    //nécessaire de faire les getter setter de date of creation manuellement car bug sinon
    @Getter @Setter
    private String site;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String domain;
    @Getter @Setter
    private String property;

    private Date date_of_creation;

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private final ObjectMapper MAPPER;
    private final YAMLFactory YF = new YAMLFactory();

    /**
     * Constructor
     * @param site
     * @param title
     * @param description
     * @param name
     * @param domain
     * @param property
     */
    SiteConfig(String site, String title, String description, String name, String domain, String property) {
        this();
        this.site = site;
        this.title = title;
        this.description = description;
        this.name = name;
        this.domain = domain;
        this.property = property;
    }

    /**
     * Constructor
     */
    public SiteConfig() {
        YF.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        MAPPER = new ObjectMapper(YF);
        MAPPER.enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        MAPPER.setDateFormat(DATE_FORMAT);
    }

    /**
     * Getter et setter pour date of creation
     * nécessaire d'être manuel car plante avec lombok :(
     */
    public Date getDate_of_creation() {
        return date_of_creation;
    }
    public void setDate_of_creation(Date date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    /**
     * @return une string avec la config
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return "site: " + site + "\n"
                + "title: " + title + "\n"
                + "description: " + description + "\n"
                + "name: " + name + "\n"
                + "domain: " + domain + "\n"
                + "property: " + property + "\n"
                + "date_of_creation: " + date_of_creation + "\n"
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
        MAPPER.writeValue(new File(filePath.toString()), this);
    }
}

