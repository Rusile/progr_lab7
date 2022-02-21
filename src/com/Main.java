package com;

import com.commands.*;
import com.util.*;

import java.time.LocalDateTime;
import java.util.Scanner;


public class Main {
    public static final String INPUT_COMMAND = "$ ";
    public static final String INPUT_INFO = "> ";

    public static void setFileName(String fileName) {
        Main.fileName = fileName;
    }

    private static String fileName = null;

    public static void main(String[] args) {
        try (Scanner userScanner = new Scanner(System.in)) {
            ScannerManager scannerManager = new ScannerManager(userScanner);
            FileManager fileManager = new FileManager(fileName);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(
                    new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new AddCommand(collectionManager, scannerManager),
                    new UpdateByIdCommand(collectionManager, scannerManager),
                    new RemoveByIdCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new SaveCommand(collectionManager),
                    new ExitCommand(),
                    new ExecuteScriptCommand(),
                    new AddIfMinCommand(collectionManager, scannerManager),
                    new RemoveHeadCommand(collectionManager),
                    new RemoveLowerCommand(collectionManager, scannerManager),
                    new MaxByCreationDateCommand(collectionManager),
                    new FilterLessThanHairColorCommand(collectionManager, scannerManager),
                    new PrintDescendingCommand(collectionManager)
            );
            ConsoleManager console = new ConsoleManager(commandManager, userScanner, scannerManager);
            console.interactiveMode();
        }
    }
}