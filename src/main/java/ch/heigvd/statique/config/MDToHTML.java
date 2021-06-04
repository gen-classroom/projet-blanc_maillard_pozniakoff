/**
 * This class allows us the convert an md file into an html file
 * @authors Jean-Luc Blanc, Lev Pozniakoff, Mathias Maillard
 */

package ch.heigvd.statique.config;

import java.io.*;


import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MDToHTML {

    /**
     * Converts md file to html file
     * /!\ No verification on the validity of the file
     * @param path path to the md file
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
        br.close();
        return renderer.render(document);
    }



}