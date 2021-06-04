/**
 * The init command for the static site generator
 * Generates the config files
 * @authors Jean-Luc Blanc, Lev Pozniakoff, Mathias Maillard
 */

package ch.heigvd.statique.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import ch.heigvd.statique.config.SiteConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.io.*;


@Command(name = "init", description = "Initialize a static site directory")
public class Init implements Callable<Integer> {

    /**
     * Directory: Parameter for the init command
     * Indicates the name of the directory to initialize the files in
     */
    @CommandLine.Parameters(paramLabel = "Directory", description = "Directory to initialize site in")
    private
    String path;

    /**
     * -O : option for the init command
     * If specified, the config files will be overwritten
     */
    @CommandLine.Option(names = "-O", description = "Overwrite existing config")
    private
    boolean overwrite;

    /**
     * Main routine for the init command
     * @return 1 if successful
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception{
        //check if last char is a / to add it to indicate it is a directory
        if (path.charAt(path.length()-1) != '/')
            path+='/';
        Path cleanPath = Paths.get(System.getProperty("user.dir")+ '/' + path);
        createDirectory(new File(cleanPath.toString()));
        createConfigFiles(cleanPath, overwrite);
        System.out.println("Configuration successful.");
        return 0;
    }

    public static boolean createConfigFiles(Path path, boolean overwrite) throws IOException {
        File config = new File(path.toString() + "/" + "config.yaml");
        File index = new File(path.toString() +  "/" + "index.md");
        if(config.exists() || index.exists())
            if(overwrite){
                SiteConfig sc1 = new SiteConfig();
                sc1.writeConfiguration(Paths.get(config.getPath()));
                System.out.println("Config files overwritten");
                return true;
            } else {
                System.out.println("can't create config files, they already exist, checkout -O option");
                return false;
            }
        else{
            createFile(config);
            SiteConfig sc1 = new SiteConfig();
            sc1.writeConfiguration(Paths.get(config.getPath()));
            createFile(index);
            System.out.println("Config files created");
            return true;
        }
    }

    public static boolean createDirectory(File directory){
        if(!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + directory.getPath());
                return true;
            }else {
                System.out.println("Failed to create the directory, using the existing one");
                return false;
            }
        }
        System.out.println("Directory already exists");
        return false;
    }

    /**
     * Create a file if it does not exists
     * @param toCreate file to create;
     * @return true if the file was successfuly created
     * @throws IOException
     */
    public static boolean createFile(File toCreate) throws IOException {

        if (!toCreate.createNewFile()) {
            System.out.println("Failed to create " + toCreate.getName() + ", already exists");
            return false;
        }
        System.out.println("File created: " + toCreate.getName());
        return true;
    }

}
