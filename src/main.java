/*
 * Auteurs      : Blanc Jean-Luc, Maillard Mathias, Pozniakoff Lev
 * Date         : 06.03.2021
 * Description  : Fichier main permettant de lancer le projet
 */
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "statique",
        subcommands = {newCommand.class, cleanCommand.class, buildCommand.class, serveCommand.class}
        description = "TBD")

@Command(name = "build")
public class buildCommand implements Runnable {
    @Parameters(arity = "1", defaultValue = "./", description = "need a filepath to build")
    File filepath;

    @Override
    public void run() {
        System.out.println("Adding some files to the staging area");
    }
}