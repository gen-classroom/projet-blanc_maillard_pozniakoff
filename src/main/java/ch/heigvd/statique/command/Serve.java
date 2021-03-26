package ch.heigvd.statique.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;

@Command(name = "serve", description = "Serve a static site")
public class Serve implements Callable<Integer> {

  @Override public Integer call() {
    System.out.printf("serve");
    return 1;
  }

}