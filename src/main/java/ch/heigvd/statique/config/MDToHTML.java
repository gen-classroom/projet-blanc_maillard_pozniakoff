package ch.heigvd.statique.config;

import java.io.*;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MDToHTML {

    /**
     * Converti un fichier markdown en html
     * /!\ aucune vérification sur la validité du fichier passé n'est effectué
     * @param path, le chemin vers le fichier md
     * @return la conversion en html
     * @throws IOException
     */
    public static String MDtoHTML(String path) throws IOException {
        //récupère le contenu du fichier
        BufferedReader br = new BufferedReader(new FileReader(path));
        String fileContent = "";
        String str;
        while ((str = br.readLine()) != null) {
            fileContent += str;
            fileContent += "\n";
        }
        //conversion en html
        Parser parser = Parser.builder().build();
        Node document = parser.parse(fileContent);
        HtmlRenderer renderer = HtmlRenderer.builder().
                attributeProviderFactory(attributeProviderContext -> new PAttributeProvider()).
                build();
        return renderer.render(document);
    }

    static void templating(String path) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("mytemplate");

        System.out.println(template.apply("Handlebars.java"));
    }

}