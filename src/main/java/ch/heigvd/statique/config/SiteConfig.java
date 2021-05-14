package ch.heigvd.statique.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * POJO object
 * Used to contain the YAML config of the website
 * This uses Jackson Maven dependancy
 */
@Getter
@Setter
public class SiteConfig {
    private String site;
    private String title;
    private String description;
    private String name;
    private String domain;
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
    public SiteConfig(String site, String title, String description, String name, String domain, String property) {
        this();
        this.site = site;
        this.title = title;
        this.description = description;
        this.name = name;
        this.domain = domain;
        this.property = property;
    }

    /**
     * Default constructor
     */
    public SiteConfig() {
        YF.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        MAPPER = new ObjectMapper(YF);
        MAPPER.enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        MAPPER.setDateFormat(DATE_FORMAT);
    }


    /**
     * @return the configuration YAML in a string
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
     * Load the content of an YAML configuration into a siteConfig
     * @param filePath the path to the config to load
     * @return the newly created siteConfig
     * @throws IOException
     */
    public static SiteConfig loadFromDocument(Path filePath) throws IOException {
        SiteConfig sc = new SiteConfig();
        return sc.MAPPER.readValue(new File(filePath.toString()), SiteConfig.class);
    }

    /**
     * Write the POJO object in a config file
     * @param filePath the file path to the yaml (including the name of the file)
     * @throws IOException
     */
    public void writeConfiguration(Path filePath) throws IOException{
        MAPPER.writeValue(new File(filePath.toString()), this);
    }
}

