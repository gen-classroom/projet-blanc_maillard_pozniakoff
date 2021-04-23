package ch.heigvd.statique.command;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "build", description = "Build a static site")
public class Build implements Callable<Integer> {

    @CommandLine.Parameters(paramLabel = "Folder", description = "Folder to initialize site in")
    String path;

  @Override
  public Integer call() throws IOException {

    String currentPath = System.getProperty("user.dir") + path;
    String buildPath = currentPath + "/build";

    final File myStaticSite = new File(currentPath);

    File build = new File(buildPath);

    System.out.println(currentPath);
    build.mkdirs();

    File htmlFile = new File(buildPath + "/pageRandom.html");
    htmlFile.createNewFile();

    return 1;
  }


}
