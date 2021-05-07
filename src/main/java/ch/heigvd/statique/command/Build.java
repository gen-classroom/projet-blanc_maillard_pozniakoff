package ch.heigvd.statique.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import org.apache.commons.io.FilenameUtils;

import ch.heigvd.statique.config.MDToHTML;

@Command(name = "build", description = "Build a static site")
public class Build implements Callable<Integer> {

  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder to initialize site in")
  String path;


  @Override
  public Integer call() throws IOException {

    String currentPath = System.getProperty("user.dir") + "/" + path;
    String buildPath = currentPath + "/build";

    File build = new File(buildPath);
    build.mkdirs();

    buildSiteStatique(build, buildPath);

    return 1;
  }

  private void buildSiteStatique(File dir, String path) throws IOException {
    if(dir != null){
      File[] listFiles = dir.listFiles();
      if(listFiles != null){
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

            String contentWithTemplate = templating(contentFileHTML);

            FileWriter writer = new FileWriter(htmlPath);
            writer.write(contentWithTemplate);
            writer.close();

          }
        }
      }
    }
  }

  static String templating(String content) throws IOException {
    TemplateLoader loader = new ClassPathTemplateLoader();
    loader.setPrefix("templates");
    loader.setSuffix(".html");
    Handlebars handlebars = new Handlebars(loader);

    Template template = handlebars.compile("layout");

    return template.apply(content);
  }

}
