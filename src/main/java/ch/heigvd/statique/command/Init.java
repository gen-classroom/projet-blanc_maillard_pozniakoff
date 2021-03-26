package ch.heigvd.statique.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(name = "init", description = "Initialize a static site directory")
public class Init implements Callable<Integer> {

  @Override public Integer call() {
    System.out.printf("init");
    return 1;
  }

}