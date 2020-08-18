package pl.coderslab.taskmanager;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {

        TaskManager.run();
    }

    private static String[][] list = new String[0][];

    public static void run() {

        showWelcomeMessage();
        try {
            loadTaskListFromFile();
        } catch (IOException e) {
            System.out.println("Błąd wczytywania pliku");
            e.printStackTrace();
        }
        while (true) {
            showMainMenu();
            String userChoice = getUserChoice();
            if (validateUserChoice(userChoice)) {
                executeValidChoice(userChoice);
                if (isExitChoice(userChoice)) {
                    break;
                }
            } else {
                executeInvalidChoice(userChoice);
            }
        }
        showExitMessage();
        saveTaskListToFile();
    }

    private static void saveTaskListToFile() {

    }

    private static void loadTaskListFromFile() throws IOException {
        Path path = Paths.get("tasks.csv");
        if(!Files.exists(path)){
            System.out.println("File 'task.csv' not found.");
        }
        while (!Files.exists(path)) {
            System.out.println("Create new 'task.csv' file? Y/N");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine().toLowerCase().trim()) {
                case "y":
                    Files.createFile(path);
                    System.out.println("Created new 'tasks.csv' file");
                    break;
                case "n":
                    System.out.println("Create 'task.csv' file and confirm by pressing enter");
                    scanner.nextLine();
                    while (!Files.exists(path)) {
                        System.out.println("Error, file not found.\nCreate 'task.csv' file and confirm by pressing enter");
                        scanner.nextLine();

                    }
                default:
                    System.out.println("Invalid answer, try again.");
            }

        }

        try {
            Scanner scanner = new Scanner(path);
            int i = 0;
            while (scanner.hasNextLine()) {
                if (i >= list.length) {
                    list = Arrays.copyOf(list, list.length + 1);
                }
                StringBuilder sb = new StringBuilder();
                sb.append(scanner.nextLine());
                list[i] = sb.toString().split(",");
                i++;
            }
        } catch (IOException e) {
            //
        }
    }


    private static void executeValidChoice(String userChoice) {
        switch (userChoice) {
            case "add":
                //kod dla add
                //
                break;
            case "remove":
                //kod dla remove
                //
                break;
            case "list":
                //kod dla list
                //
                break;
            case "exit":
                //kod dla exi
                //
                break;
        }
    }

    private static String getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static boolean validateUserChoice(String userChoice) {
        String[] validChoices = {"add", "list", "remove", "exit"};
        for (String validChoice : validChoices) {
            if (userChoice.toLowerCase().trim().equals(validChoice)) return true;
        }
        return false;
    }

    private static boolean isExitChoice(String userChoice) {
        return "exit".equalsIgnoreCase(userChoice);
    }

    private static void executeInvalidChoice(String userChoice) {
        System.out.println(ConsoleColors.RED_BACKGROUND + "Invalid option '" + userChoice + "' plz select valid option");
    }

    private static void showMainMenu() {
        System.out.println(ConsoleColors.BLUE + "Plz select an option: ");
        System.out.println(ConsoleColors.RESET);
        System.out.println("\tadd");
        System.out.println("\tremove");
        System.out.println("\tlist");
        System.out.println("\texit");

    }

    private static void showExitMessage() {
        System.out.println(ConsoleColors.RED + "Goodbye, come back soon");
        System.out.println(ConsoleColors.RESET);
    }

    private static void showWelcomeMessage() {
        System.out.println(ConsoleColors.RED + "Welcome in Task Manager");
        System.out.println(ConsoleColors.RESET);
    }

}
