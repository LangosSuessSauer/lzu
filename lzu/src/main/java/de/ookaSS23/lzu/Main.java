package de.ookaSS23.lzu;

import de.ookaSS23.lzu.command.DisplayStatusOperation;
import de.ookaSS23.lzu.command.StartComponentOperation;
import de.ookaSS23.lzu.command.StopComponentOperation;
import de.ookaSS23.lzu.controller.ComponentAssembler;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        ComponentAssembler componentAssembler = ComponentAssembler.getInstance();

        while (true) {
            Thread.sleep(500);
            System.out.println("Enter a command (load/start/stop/status/stopHard/exit):");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "status":
                    for (int i = 0; i < componentAssembler.getComponentWrappers().size(); ++i) {
                        DisplayStatusOperation displayStatusOperation = new DisplayStatusOperation(componentAssembler.getComponentWrappers().get(i));
                        componentAssembler.addComponentOperation(displayStatusOperation);
                    }
                    componentAssembler.executeComponentOperations();
                    break;
                case "load":
                    System.out.println("Choose between component A and B by typing A/B");
                    command = scanner.nextLine();
                    switch (command.toLowerCase()) {
                        case "b":
                            componentAssembler.loadComponentFromJar(ComponentAssembler.Component.COMPONENT_B);
                            break;
                        case "a":
                            componentAssembler.loadComponentFromJar(ComponentAssembler.Component.COMPONENT_A);
                            break;
                        default:
                            System.out.println("Invalid command.");
                            break;
                    }
                    break;
                case "start":
                    System.out.println("Choose the number as integer (1,2...) of following components: ");
                    for (int i = 0; i < componentAssembler.getComponentWrappers().size(); ++i) {
                        DisplayStatusOperation displayStatusOperation = new DisplayStatusOperation(componentAssembler.getComponentWrappers().get(i));
                        componentAssembler.addComponentOperation(displayStatusOperation);
                    }
                    componentAssembler.executeComponentOperations();
                    command = scanner.nextLine();
                    int startnumber = Integer.parseInt(command) - 1;
                    if (startnumber > componentAssembler.getComponentWrappers().size() - 1 || startnumber < 0) {
                        System.out.println("Invalid command.");
                        break;
                    } else {
                        StartComponentOperation displayStatusOperation = new StartComponentOperation(componentAssembler.getComponentWrappers().get(startnumber));
                        componentAssembler.addComponentOperation(displayStatusOperation);
                        componentAssembler.executeComponentOperations();
                    }
                    break;
                case "stop":
                    System.out.println("Choose the number as integer (1,2...) of following components: ");
                    for (int i = 0; i < componentAssembler.getComponentWrappers().size(); ++i) {
                        DisplayStatusOperation displayStatusOperation = new DisplayStatusOperation(componentAssembler.getComponentWrappers().get(i));
                        componentAssembler.addComponentOperation(displayStatusOperation);
                    }
                    componentAssembler.executeComponentOperations();
                    command = scanner.nextLine();
                    int stopnumber = Integer.parseInt(command) - 1;
                    if (stopnumber > componentAssembler.getComponentWrappers().size() - 1 || stopnumber < 0) {
                        System.out.println("Invalid command.");
                        break;
                    } else {
                        StopComponentOperation displayStatusOperation = new StopComponentOperation(componentAssembler.getComponentWrappers().get(stopnumber));
                        componentAssembler.addComponentOperation(displayStatusOperation);
                        componentAssembler.executeComponentOperations();
                    }
                    break;
                case "stopHard":
                    componentAssembler.stopAllThreads();
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    componentAssembler.stopAllThreads();
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

}