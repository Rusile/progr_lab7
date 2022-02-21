package com.commands;

import com.exception.IncorrectInputInScriptException;
import com.exception.WrongAmountOfArgumentsException;
import com.people.Person;
import com.util.CollectionManager;
import com.util.ConsoleManager;
import com.util.ScannerManager;

import java.time.LocalDateTime;

/**
 * 'add' command. Add person in collection.
 */

public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    private final ScannerManager scannerManager;

    public AddCommand(CollectionManager collectionManager, ScannerManager scannerManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.scannerManager = scannerManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            collectionManager.addToCollection(
                    new Person(
                            collectionManager.generateNextId(),
                            scannerManager.askPersonName(),
                            scannerManager.askCoordinates(),
                            LocalDateTime.now(),
                            scannerManager.askPersonHeight(),
                            scannerManager.askPersonEyeColor(),
                            scannerManager.askPersonHairColor(),
                            scannerManager.askPersonNationality(),
                            scannerManager.askPersonLocation()
                    )
            );
            ConsoleManager.printSuccessfulMessage("Данные о персоне успешно добавлены!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {
            ConsoleManager.printErr("Не удалось выполнить скрипт.");
        }
        return false;
    }

}
