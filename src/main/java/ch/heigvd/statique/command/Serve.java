/**
 * The serve command to visualize the static site in a Web browser
 * @authors Jean-Luc Blanc, Lev Pozniakoff, Mathias Maillard
 */

package ch.heigvd.statique.command;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import static java.nio.file.StandardWatchEventKinds.*;

@Command(name = "serve", description = "Serve a static site")
public class Serve implements Callable<Integer> {

  /**
   * Parameter for the serve command
   * Specifies the folder containing the html files
   */
  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder with content")
  private String path;

  /**
   * Option to watch the build folder
   */
  @CommandLine.Option(names = "--watch", description = "Watch option")
  private boolean watch = false;

  /**
   * Main routine for the serve command
   * @return 1 if successful
   * @throws IOException
   * @throws InterruptedException
   */
  @Override public Integer call() throws IOException, InterruptedException {
    String currentPath = System.getProperty("user.dir") + "/" + path;

    Path watchPath = Paths.get(currentPath + "/build");
    WatchService watchService =  watchPath.getFileSystem().newWatchService();
    watchPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

    /*
    TODO: index.html ne devrait pas être codé en dur
     */

    String htmlPath = currentPath + "/build/index.html";
    try {
      URI htmlUri = new File(htmlPath).toURI();
      Desktop.getDesktop().browse(htmlUri);
    }
    catch (Exception e){
      e.printStackTrace();
    }

    if(watch) {
      while (true) {
        WatchKey watchKey = watchService.take();
        if (watchKey != null) {
          watchKey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
          try {
            URI htmlUri = new File(htmlPath).toURI();
            Desktop.getDesktop().browse(htmlUri);
          }
          catch (Exception e){
            e.printStackTrace();
          }
          watchKey.reset();
        }
      }
    }

    return 1;
  }

}