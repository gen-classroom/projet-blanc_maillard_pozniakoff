package ch.heigvd.statique.command;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

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

    String currentPath = System.getProperty("user.dir") + path;
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
            convertMDtoHTML(file, path);
          }
        }
      }
    }
  }

}
