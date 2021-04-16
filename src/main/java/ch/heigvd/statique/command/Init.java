package ch.heigvd.statique.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import java.io.*;

@Command(name = "init", description = "Initialize a static site directory")
public class Init implements Callable<Integer> {

  public static void main(String [] args){

  }

  @Override
  public Integer call() throws Exception{
    CommandLine.usage(this, System.out);
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
/*
@Command(name = "init", description = "TBD", version = "init 0.1",
        description = "Creates the necessary files in the folder given in parameters")
class Init implements Callable<Integer>{
  @Parameters(index = "0", decription = "The directory to create the files in")

  private String DIR_PATH;

  //config.yaml, index_tmpl.md
  @Override
  public Integer run() throws Exception {
    File directory = new File(DIR_PATH);
    if(directory.exists())
      directory.mkdirs();
    File config = new File(DIR_PATH + "\\" + "config.yaml");
    File index = new File(DIR_PATH + "\\" + "index.md");
    try{
      FileWriter configFw = new FileWriter(config.getAbsolute);

    }catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public static void main (String... args){
    System.exit(new CommandLine(new Init().execute()))
  }
}
*/
