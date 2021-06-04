/**
 * The clean command to clean the files of a generated static site
 * @authors Jean-Luc Blanc, Lev Pozniakoff, Mathias Maillard
 */

package ch.heigvd.statique.command;

import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "clean", description = "Clean a static site")
public class Clean implements Callable<Integer> {

  /**
   * Parameter for the clean command
   * Specifies the folder to clean
   */
  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder to clean")
  String path;

  /**
   * Main routine for the clean command
   * @return 1 if successful
   */
  @Override public Integer call() {

    String currentPath = System.getProperty("user.dir") + path;
    String buildPath = currentPath + "/build";

    final File myStaticSite = new File(currentPath);
    deleteDirectory(myStaticSite);

    return 1;
  }


  /**
   * Deletes the directory (recursively)
   * @param directoryToBeDeleted directory to be deleted
   * @return 1 if successful
   */
  boolean deleteDirectory(File directoryToBeDeleted) {
    File[] allContents = directoryToBeDeleted.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }
    return directoryToBeDeleted.delete();
  }

}