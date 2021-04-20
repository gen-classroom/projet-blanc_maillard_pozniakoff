package ch.heigvd.statique.command;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

import ch.heigvd.statique.config.SiteConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.io.*;

@Command(name = "init", description = "Initialize a static site directory")
public class Init implements Callable<Integer> {

  @CommandLine.Parameters(paramLabel = "Folder", description = "Folder to initialize site in")
  String path;

  @Override
  public Integer call() throws Exception{
    if (path.charAt(path.length()-1) != '/')
      path+='/';
    String configName = "config.yaml";
    String indexName = "index.md";
    File file = new File(path);

    //Create the directory hierarchy from arguments given to command if it does not exists.
    if(!file.exists()) {
      if (file.mkdirs())
        System.out.println("Created directory: " + path);
      else
        System.out.println("Failed to create the directory");
    }

    if(createFile(path, configName)){
      SiteConfig sc1 = new SiteConfig();
      sc1.loadToDocument(Paths.get(path+configName));
    } else {
      return 1;
    }

    createFile(path, indexName);
    return 0;
  }



  public static boolean createFile(String path, String fileName) throws IOException {
    File configFile = new File(path + fileName);
    if (!configFile.createNewFile()) {
      System.out.println("Failed to create " + fileName + ", already exists");
      return false;
    }
    System.out.println("File created: " + configFile.getName());
    return true;
  }

}
