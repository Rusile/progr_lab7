package com.util;

import com.Main;
import com.exception.NoAccessToFileException;
import com.exception.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Operates command input.
 */
public class ConsoleManager {
    private final CommandManager commandManager;
    private final Scanner userScanner;
    private final ScannerManager scannerManager;
    private final List<String> scriptStack = new ArrayList<>();

    public ConsoleManager(CommandManager commandManager, Scanner userScanner, ScannerManager scannerManager) {
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.scannerManager = scannerManager;
    }

    /**
     * Mode for catching commands from user input.
     */
    public void interactiveMode() {
        String[] userCommand = {"", ""};
        int commandStatus;
        try {
            do {
                System.out.println(Main.INPUT_COMMAND);
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != 2);
        } catch (NoSuchElementException exception) {
            ConsoleManager.printErr("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            ConsoleManager.printErr("Непредвиденная ошибка!");
        }
    }

    /**
     * Launches the command.
     *
     * @param userCommand Command to launch.
     * @return Exit code.
     */
    private int launchCommand(String[] userCommand) {
        String command = userCommand[0];
        String argument = userCommand[1];
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(argument)) return 1;
                break;
            case "info":
                if (!commandManager.info(argument)) return 1;
                break;
            case "show":
                if (!commandManager.show(argument)) return 1;
                break;
            case "add":
                if (!commandManager.add(argument)) return 1;
                break;
            case "update":
                if (!commandManager.update(argument)) return 1;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(argument)) return 1;
                break;
            case "clear":
                if (!commandManager.clear(argument)) return 1;
                break;
            case "save":
                if (!commandManager.save(argument)) return 1;
                break;
            case "add_if_min":
                if (!commandManager.addIfMin(argument)) return 1;
                break;
            case "max_by_creation_date":
                if (!commandManager.maxByCreationDate(argument)) return 1;
                break;
            case "filter_less_than_hair_color":
                if (!commandManager.filterLessThanHairColor(argument)) return 1;
                break;
            case "execute_script":
                if (!commandManager.executeScript(argument)) return 1;
                else return scriptMode(argument);
            case "print_descending":
                if (!commandManager.printDescending(argument)) return 1;
                break;
            case "remove_head":
                if (!commandManager.removeHead(argument)) return 1;
                break;
            case "exit":
                if (!commandManager.exit(argument)) return 1;
                else return 2;
            default:
                if (!commandManager.noSuchCommand(command)) return 1;
        }
        return 0;
    }

    /**
     * Mode for catching commands from a script.
     *
     * @param argument Its argument.
     * @return Exit code.
     */
    public int scriptMode(String argument) {
        String[] userCommand = {"", ""};
        int commandStatus;
        scriptStack.add(argument);
        try {
            File file = new File(argument);
            if (file.exists() && !file.canRead()) throw new NoAccessToFileException();
            Scanner scriptScanner = new Scanner(file);
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = scannerManager.getUserScanner();
            scannerManager.setUserScanner(scriptScanner);
            scannerManager.setFileMode();
            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                System.out.println(Main.INPUT_COMMAND + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == 0 && scriptScanner.hasNextLine());
            scannerManager.setUserScanner(tmpScanner);
            scannerManager.setUserMode();
            if (commandStatus == 1 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty()))
                System.out.println("Проверьте скрипт на корректность введенных данных!");
            return commandStatus;
        } catch (FileNotFoundException exception) {
            ConsoleManager.printErr("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            ConsoleManager.printErr("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            ConsoleManager.printErr("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            ConsoleManager.printErr("Непредвиденная ошибка!");
            System.exit(0);
        } catch (NoAccessToFileException e) {
            ConsoleManager.printErr("Нет доступа к файлу");
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return 1;
    }

    /**
     * Prints errors
     * @param message
     */
    public static void printErr(Object message) {
        System.out.println("\u001B[31m" + "Error: " + message + "\u001B[0m");
    }

    /**
     * Prints message if operation is successfully executed
     * @param message
     */
    public static void printSuccessfulMessage(Object message) {
        System.out.println("\u001B[32m" + message + "\u001B[0m");
    }

    /**
     * Prints different information
     * @param message
     */
    public static void printInfo(Object message) {
        System.out.println("\u001B[36m" + message + "\u001B[0m");
    }

}
