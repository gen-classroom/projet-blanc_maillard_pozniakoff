package ch.heigvd.statique.command;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

import ch.heigvd.statique.config.SiteConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.io.*;

@Command(name = "init", description = "Initialize a static site directory")
public class Init implements Callable<Integer> {

    //path to init directory
    @CommandLine.Parameters(paramLabel = "Directory", description = "Directory to initialize site in")
    String path;

    //Option to indicate if we have to overwrite existing files
    @CommandLine.Option(names = "-O", description = "Overwrite existing config")
    private
    boolean overwrite;

    @Override
    public Integer call() throws Exception{
        String currentPath = System.getProperty("user.dir") + "/" + path;

        //check if last char is a / to add it to indicate it is a directory
        if (path.charAt(path.length()-1) != '/')
            path+='/';
        path = currentPath+path;

        //Create the directory hierarchy from arguments given to command if it does not exists.
        File directory = new File(path);
        if(!directory.exists()) {
            if (directory.mkdirs())
                System.out.println("Created directory: " + path);
            else
                System.out.println("Failed to create the directory, using the existing one");
        }

        File config = new File(path + "config.yaml");
        File index = new File(path + "index.md");
        // create the config file if it does not exists.
        if((config.exists() && overwrite) || createFile(config)){
            SiteConfig sc1 = new SiteConfig();
            sc1.writeConfiguration(Paths.get(config.getPath()));
        } else {
            System.out.println("Configuration failed.");
            return 1;
        }

        // create the md file if it does not exists.
        if(!createFile(index) && !overwrite){
            System.out.println("Configuration failed.");
            return 1;
        }
        System.out.println("Configuration successful.");
        return 0;
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
