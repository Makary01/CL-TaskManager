package pl.coderslab.taskmanager;


import java.lang.invoke.SwitchPoint;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {

        TaskManager.run();
    }
    private static String[][] list = new String[0][];

    public static void run() {

        showWelcomeMessage();
        loadTaskListFromFile();
        while(true){
            showMainMenu();
            String userChoice = getUserChoice();
            if(validateUserChoice(userChoice)){
                executeValidChoice(userChoice);
                if(isExitChoice(userChoice)){
                    break;
                }
            }else{
                executeInvalidChoice(userChoice);
            }
        }
        showExitMessage();
        saveTaskListToFile();
    }

    private static void saveTaskListToFile() {

    }

    private static void loadTaskListFromFile() {

    }

    private static void executeValidChoice(String userChoice) {
        switch (userChoice){
            case"add":
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
            if(userChoice.toLowerCase().trim().equals(validChoice)) return true;
        }return false;
    }

    private static boolean isExitChoice(String userChoice) {
        return "exit".equalsIgnoreCase(userChoice);
    }

    private static void executeInvalidChoice(String userChoice) {
        System.out.println(ConsoleColors.RED_BACKGROUND + "Invalid option "+ userChoice +" plz select valid option");
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
