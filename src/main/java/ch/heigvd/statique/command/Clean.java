package ch.heigvd.statique.command;

import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "clean", description = "Clean a static site")
public class Clean implements Callable<Integer> {

  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder to clean")
  String path;

  @Override public Integer call() {

    String currentPath = System.getProperty("user.dir") + path;
    String buildPath = currentPath + "/build";

    final File myStaticSite = new File(currentPath);
    deleteDirectory(myStaticSite);

    return 1;
  }




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