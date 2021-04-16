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

public class SiteConfig{
    String site;
    String title;
    String description;
    String name;
    String domain;
    String property;
    Date date_of_creation;

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private final ObjectMapper MAPPER;
    private final YAMLFactory YF = new YAMLFactory();

    SiteConfig(String site, String title, String description, String name, String domain, String property) {
        this();
        this.site = site;
        this.title = title;
        this.description = description;
        this.name = name;
        this.domain = domain;
        this.property = property;
    }

    SiteConfig() {
        YF.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        MAPPER = new ObjectMapper(YF);
        MAPPER.enable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        MAPPER.setDateFormat(DATE_FORMAT);
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Date date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

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

    public static SiteConfig loadFromDocument(Path filePath) throws IOException {
        SiteConfig sc = new SiteConfig();
        return sc.MAPPER.readValue(new File(filePath.toString()), SiteConfig.class);
    }

    public void loadToDocument(Path filePath) throws IOException{
        MAPPER.writeValue(new File(filePath.toString()), this);
    }
}

