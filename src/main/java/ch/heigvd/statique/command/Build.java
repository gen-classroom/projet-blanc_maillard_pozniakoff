/**
 * The build command to build the static site
 * @authors: Jean-Luc Blanc, Lev Pozniakoff, Mathias Maillard
 */

package ch.heigvd.statique.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.*;
import java.util.concurrent.Callable;

import ch.heigvd.statique.config.Article;
import ch.heigvd.statique.config.SiteConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import org.apache.commons.io.FilenameUtils;

import ch.heigvd.statique.config.MDToHTML;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;

import static java.nio.file.StandardWatchEventKinds.*;
import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

@Command(name = "build", description = "Build a static site")
public class Build implements Callable<Integer> {

  /**
   * The name of the directory to initialize the site, from the directory the command is typed in.
   */
  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder to initialize site in")
  String path;

  /**
   * Option to watch the build folder
   */
  @CommandLine.Option(names = "--watch", description = "Watch option")
  Boolean watch = false;

  /**
   * The build directory
   */
  private File build;

  /**
   * The Template object for the layout
   */
  private Template layout;

  /**
   * Call method for the build command
   * @return 1 if successful
   * @throws IOException
   */
  @Override
  public Integer call() throws IOException, InterruptedException {


    String currentPath = System.getProperty("user.dir") + "/" + path;
    String buildPath = currentPath + "/build";


    build = new File(buildPath);
    build.mkdirs();
    buildStaticSite(build.getParentFile(), currentPath);
    
    Path watchPath = Paths.get(currentPath);
    WatchService watchService =  watchPath.getFileSystem().newWatchService();
    watchPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    //If watch is true, we keep looping
    if(watch) {
      while (true) {
        WatchKey watchKey = watchService.take();
        if (watchKey != null) {
          watchKey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
          build = new File(buildPath);
          build.mkdirs();
          buildStaticSite(build.getParentFile(), currentPath);
        }
        watchKey.reset();
      }
    }
    return 1;
  }

  /**
   * Routine to create html files
   * @param dir directory with the .md files
   * @param path path for the created files
   * @throws IOException if the files could not be created
   */
  private void buildStaticSite(File dir, String path) throws IOException {
    if(dir != null){
      File[] listFiles = dir.listFiles();
      if(listFiles != null){
        createLayoutTemplate();
        for(File file : listFiles){
          String filename = file.getName();
          if(file.isDirectory() && !filename.equals("build")){
            Path newPath = Paths.get(path + "/" + filename);
            new File(String.valueOf(newPath)).mkdirs();
            buildStaticSite(file, String.valueOf(newPath));
          }
          else if(FilenameUtils.getExtension(filename).equals("md")){
            MDToHTML translator = new MDToHTML();
            String contentFileHTML = translator.MDtoHTML(path + "/" + filename);
            String htmlPath = path + "/build/" + FilenameUtils.removeExtension(filename) + ".html";

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

  /**
   * Method to create the layout template
   * @throws IOException
   */
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
