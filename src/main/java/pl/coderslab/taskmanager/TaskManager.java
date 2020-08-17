package pl.coderslab.taskmanager;

import java.sql.SQLOutput;

public class TaskManager {

    public static void main(String[] args) {
        TaskManager.run();
    }

    public static void run() {

        showWelcomeMessage();
        while(true){
            showMainMenu();
            String userChoice = getUserChoice();
            if(validateUserChoice(userChoice)){
                executeValidChoice();
                if(isExitChoice()){
                    break;
                }
            }else{
                executeInvalidChoice(userChoice);
            }
        }
        showExitMessage();
    }

    private static void executeValidChoice() {

    }

    private static String getUserChoice() {
        return "";
    }

    private static boolean validateUserChoice(String userChoice) {
        return false;
    }

    private static boolean isExitChoice() {
        return false;
    }

    private static void executeInvalidChoice(String userChoice) {
        System.out.println(ConsoleColors.RED_BACKGROUND + "Invalid option "+ userChoice +" plz select valid option");
    }

    private static void showMainMenu() {
        System.out.println(ConsoleColors.BLUE + "Plz select an option: ");
        System.out.println(ConsoleColors.RESET);
        System.out.println("\tadd");
        System.out.println("\tremoce");
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
