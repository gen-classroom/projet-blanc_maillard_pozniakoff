package ch.heigvd.statique.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import ch.heigvd.statique.config.Article;
import ch.heigvd.statique.config.SiteConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import org.apache.commons.io.FilenameUtils;

import ch.heigvd.statique.config.MDToHTML;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.internal.text.StringEscapeUtils;
import com.github.jknack.handlebars.io.FileTemplateLoader;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

@Command(name = "build", description = "Build a static site")
public class Build implements Callable<Integer> {

  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder to initialize site in")
  String path;

  //répertoire build
  private File build;

  //fichier layout du template
  private Template layout;

  @Override
  public Integer call() throws IOException {

    String currentPath = System.getProperty("user.dir") + "/" + path;
    String buildPath = currentPath + "/build";

    build = new File(buildPath);
    build.mkdirs();

    buildSiteStatique(build, buildPath);

    return 1;
  }

  private void buildSiteStatique(File dir, String path) throws IOException {
    if(dir != null){
      File[] listFiles = dir.listFiles();
      if(listFiles != null){
        createLayoutTemplate();
        for(File file : listFiles){
          String filename = file.getName();
          if(file.isDirectory() && !filename.equals("build")){
            Path newPath = Paths.get(path + "/" + filename);
            new File(String.valueOf(newPath)).mkdirs();
            buildSiteStatique(file, String.valueOf(newPath));
          }
          else if(FilenameUtils.getExtension(filename).equals("md")){
            MDToHTML translator = new MDToHTML();
            String contentFileHTML = translator.MDtoHTML(path + "/" + filename);
            String htmlPath = path + "/" + FilenameUtils.removeExtension(filename) + ".html";

            Path pathConfig = Paths.get(System.getProperty("user.dir") + "/" + this.path + "/config.yaml");
            SiteConfig sc = SiteConfig.loadFromDocument(pathConfig);

            String siteTitre = sc.getSite();
            String pageTitre = sc.getTitle();

            Article article = new Article(siteTitre,pageTitre,contentFileHTML);

            String contentTemplate = unescapeHtml4(layout.apply(article));

            FileWriter writer = new FileWriter(htmlPath);
            writer.write(contentTemplate);
            writer.close();

          }
        }
      }
    }
  }

  private void createLayoutTemplate()
  {
    try
    {
      FileTemplateLoader loader = new FileTemplateLoader(new File(System.getProperty("user.dir") + "/templates/"));
      loader.setSuffix(".html");
      Handlebars handlebars = new Handlebars(loader);
      handlebars.setPrettyPrint(true);

      layout = handlebars.compile("layout");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
