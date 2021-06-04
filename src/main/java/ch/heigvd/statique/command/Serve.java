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
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "serve", description = "Serve a static site")
public class Serve implements Callable<Integer> {

  /**
   * Parameter for the serve command
   * Specifies the folder containing the html files
   */
  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder with content")
  String path;

  /**
   * Main routine for the serve command
   * @return 1 if successful
   */
  @Override public Integer call() {
    String currentPath = System.getProperty("user.dir") + "/" + path;

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
    return 1;
  }

}