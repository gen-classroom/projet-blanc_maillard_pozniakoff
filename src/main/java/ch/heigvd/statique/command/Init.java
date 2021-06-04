package ch.heigvd.statique.command;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import ch.heigvd.statique.config.SiteConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.io.*;

@Command(name = "init", description = "Initialize a static site directory")
public class Init implements Callable<Integer> {

    final static String DEFAULT_INDEX = "# Sample Markdown\n" +
            "\n" +
            "This is some basic, sample markdown.\n" +
            "\n" +
            "## Second Heading\n" +
            "\n" +
            " * Unordered lists, and:\n" +
            "  1. One\n" +
            "  1. Two\n" +
            "  1. Three\n" +
            " * More\n" +
            "\n" +
            "> Blockquote\n" +
            "\n" +
            "And **bold**, *italics*, and even *italics and later **bold***. Even ~~strikethrough~~. [A link](https://markdowntohtml.com) to somewhere.\n" +
            "\n" +
            "And code highlighting:\n" +
            "\n" +
            "```js\n" +
            "var foo = 'bar';\n" +
            "\n" +
            "function baz(s) {\n" +
            "   return foo + ':' + s;\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "Or inline code like `var foo = 'bar';`.\n" +
            "\n" +
            "Or an image of bears\n" +
            "\n" +
            "![bears](http://placebear.com/200/200)\n" +
            "\n" +
            "The end ...\n" ;

    //path to init directory
    @CommandLine.Parameters(paramLabel = "Directory", description = "Directory to initialize site in")
    private
    String path;

    //Option to indicate if we have to overwrite existing files
    @CommandLine.Option(names = "-O", description = "Overwrite existing config")
    private
    boolean overwrite;

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
               writeDefaultConfigFiles(config, index);
                System.out.println("Config files overwritten");
                return true;
            } else {
                System.out.println("can't create config files, they already exist, checkout -O option");
                return false;
            }
        else{
            createFile(config);
            createFile(index);
            writeDefaultConfigFiles(config, index);
            System.out.println("Config files created");
            return true;
        }
    }

    public static void writeDefaultConfigFiles(File config, File index) throws IOException {
        SiteConfig sc1 = new SiteConfig();
        sc1.writeConfiguration(Paths.get(config.getPath()));
        FileWriter writer = new FileWriter(index);
        writer.write(DEFAULT_INDEX);
        writer.close();
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
