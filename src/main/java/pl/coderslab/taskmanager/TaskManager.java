package pl.coderslab.taskmanager;


import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
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
        makeSureFileExist("tasks.csv");
        Path path = Paths.get("tasks.csv");
        StringBuilder sb = new StringBuilder();
        for (String[] strings : list) {
            if (strings == null) break;
            else {
                for (String string : strings) {
                    sb.append(string).append(",");
                }
                sb.delete(sb.length() - 1, sb.length());
                sb.append("\n");
            }
        }
        try {
            Files.writeString(path, sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//zrobione

    private static void makeSureFileExist(String fileName) {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("File " + fileName + " not found.");
        }
        while (!Files.exists(path)) {
            System.out.println("Create new '" + fileName + "' file? Y/N");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine().toLowerCase().trim()) {
                case "y":
                    try {
                        Files.createFile(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Created new 'tasks.csv' file");
                    break;
                case "n":
                    System.out.println("Create '" + fileName + "' file and confirm by pressing enter");
                    scanner.nextLine();
                    while (!Files.exists(path)) {
                        System.out.println(ConsoleColors.RED_BACKGROUND + "Error, file not found.");
                        System.out.println(ConsoleColors.RESET);
                        System.out.println("Create '" + fileName + "' file and confirm by pressing enter");
                        scanner.nextLine();

                    }
                    break;
                default:
                    System.out.println(ConsoleColors.RED_BACKGROUND + "Invalid answer, try again.");
                    System.out.println(ConsoleColors.RESET);
            }

        }
    }//zrobione

    private static void loadTaskListFromFile() throws IOException {
        makeSureFileExist("tasks.csv");

        Path path = Paths.get("tasks.csv");
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
    }//zrobione


    private static void executeValidChoice(String userChoice) {
        switch (userChoice) {
            case "add":
                addTask();
                break;
            case "remove":
                removeTask();
                break;
            case "list":
                listOfTaskt();
                break;
            case "exit":
                break;
        }
    }//zrobione

    private static void removeTask() {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (true) {
            listOfTaskt();
            System.out.println("Enter number of task you want to delete: ");
            String input = scanner.nextLine();
            try {
                i = Integer.parseInt(input);
                if (i > 0 && i <= list.length) {
                    list = ArrayUtils.remove(list, i - 1);
                    System.out.print("Deleted task number " + i);
                    break;
                } else {
                    invalidAnswer();
                }

            } catch (NumberFormatException e) {
                invalidAnswer();
            }
        }
    }//zrobione


    private static void listOfTaskt() {
        int i = 1;
        for (String[] strings : list) {
            System.out.print("Task " + i);
            for (String string : strings) {
                if(string.trim().equals("true")){
                    System.out.print(" Done ");
                }else if(string.trim().equals("false")){
                    System.out.print(" Not done yet ");
                }else {
                    System.out.print(" " + string + " ");
                }
            }
            System.out.println();
            i++;
        }
    }//zrobione

    private static void addTask() {
        System.out.print("Creating task nr " + (list.length + 1) + "\n Task name: ");
        list = Arrays.copyOf(list, list.length + 1);
        Scanner scanner = new Scanner(System.in);
        StringBuilder task = new StringBuilder();
        task.append(scanner.nextLine().replaceAll(",", ""));
        boolean loopBreaker = true;
        while (loopBreaker) {
            System.out.println("Is the task important? Y/N");
            switch (scanner.nextLine().toLowerCase().trim()) {
                case "y":
                    task.append(" - Important");
                    loopBreaker = false;
                    break;
                case "n":
                    task.append(" - Not so important");
                    loopBreaker = false;
                    break;
                default:
                    invalidAnswer();
            }
        }
        task.append(",");
        System.out.println("Enter task date");
        loopBreaker = true;
        int yearToInt = 0;
        while (loopBreaker) {
            System.out.print("Year: ");
            try {
                String year = scanner.nextLine().trim();
                yearToInt = Integer.parseInt(year);
                task.append(yearToInt).append("-");
                loopBreaker = false;
            } catch (NumberFormatException e) {
                invalidAnswer();
            }
        }

        loopBreaker = true;
        int monthToInt = 0;
        while (loopBreaker) {
            System.out.print("Month: ");
            try {
                String month = scanner.nextLine().trim();
                monthToInt = Integer.parseInt(month);
                if (monthToInt > 0 && monthToInt <= 9) {
                    task.append("0").append(monthToInt).append("-");
                    loopBreaker = false;
                } else if (monthToInt > 9 && monthToInt <= 12) {
                    task.append(monthToInt).append("-");
                    loopBreaker = false;
                } else {
                    invalidAnswer();
                }
            } catch (NumberFormatException e) {
                invalidAnswer();

            }
        }

        loopBreaker = true;
        while (loopBreaker) {
            System.out.print("Day: ");
            try {
                String day = scanner.nextLine().trim();
                int dayToInt = Integer.parseInt(day);
                if (dayToInt > 0 && dayToInt < 10) {
                    task.append("0").append(dayToInt);
                    loopBreaker = false;
                } else if (dayToInt >= 10 && dayToInt <= 28) {
                    task.append(dayToInt);
                    loopBreaker = false;
                } else {
                    switch (dayToInt) {
                        case 31:
                            if (monthToInt == 1 || monthToInt == 3 || monthToInt == 5 || monthToInt == 7 || monthToInt == 8 || monthToInt == 10 || monthToInt == 12) {
                                task.append(day);
                                loopBreaker = false;
                            } else invalidAnswer();
                            break;
                        case 30:
                            if (monthToInt != 2) {
                                task.append(day);
                                loopBreaker = false;
                            } else invalidAnswer();
                            break;
                        case 29:
                            if (yearToInt % 4 == 0 && yearToInt % 100 != 0 || yearToInt % 400 == 0) {
                                task.append(day);
                                loopBreaker = false;
                            } else if (monthToInt != 2) {
                                task.append(day);
                                loopBreaker = false;
                            } else {
                                invalidAnswer();
                            }
                            break;
                        default:
                            invalidAnswer();
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                invalidAnswer();
            }
        }
        task.append(",");
        loopBreaker = true;
        while (loopBreaker) {
            System.out.println("Is task done? Y/N");
            switch (scanner.nextLine().toLowerCase().trim()) {
                case "y":
                    task.append("true");
                    loopBreaker = false;
                    break;
                case "n":
                    task.append("false");
                    loopBreaker = false;
                    break;
                default:
                    invalidAnswer();
                    break;
            }
        }
        list[list.length - 1] = task.toString().split(",");

    }//zrobione

    private static String getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }//zrobione

    private static boolean validateUserChoice(String userChoice) {
        String[] validChoices = {"add", "list", "remove", "exit"};
        for (String validChoice : validChoices) {
            if (userChoice.toLowerCase().trim().equals(validChoice)) return true;
        }
        return false;
    }//zrobione

    private static boolean isExitChoice(String userChoice) {
        return "exit".equalsIgnoreCase(userChoice);
    }//zrobione

    private static void executeInvalidChoice(String userChoice) {
        System.out.println(ConsoleColors.RED_BACKGROUND + "Invalid option '" + userChoice + "' plz select valid option");
        System.out.println(ConsoleColors.RESET);
    }//zrobione

    private static void showMainMenu() {
        System.out.println(ConsoleColors.BLUE + "Plz select an option: ");
        System.out.println(ConsoleColors.RESET);
        System.out.println("\tadd");
        System.out.println("\tremove");
        System.out.println("\tlist");
        System.out.println("\texit");

    }//zrobione

    private static void showExitMessage() {
        System.out.println(ConsoleColors.RED + "Goodbye, come back soon");
        System.out.println(ConsoleColors.RESET);
    }//zrobione

    private static void showWelcomeMessage() {
        System.out.println(ConsoleColors.RED + "Welcome in Task Manager");
        System.out.println(ConsoleColors.RESET);
    }//zrobione

    private static void invalidAnswer() {
        System.out.print(ConsoleColors.RED_BACKGROUND + "Invalid answer, try again.");
        System.out.println(ConsoleColors.RESET);
    }
}
