package com.commands;

import com.exception.*;
import com.people.Person;
import com.util.CollectionManager;
import com.util.ConsoleManager;
import com.util.ScannerManager;

import java.time.LocalDateTime;
/**
 * 'add_if_min {element}' command. Add person if he will be min in the collection.
 */
public class AddIfMinCommand extends Command {
    private final CollectionManager collectionManager;
    private final ScannerManager scannerManager;


    public AddIfMinCommand(CollectionManager collectionManager, ScannerManager scannerManager) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.scannerManager = scannerManager;
    }
    /**
     * Executes the command.
     * @return Command execute status.
     */
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Person personToAdd = new Person(
                    collectionManager.generateNextId(),
                    scannerManager.askPersonName(),
                    scannerManager.askCoordinates(),
                    LocalDateTime.now(),
                    scannerManager.askPersonHeight(),
                    scannerManager.askPersonEyeColor(),
                    scannerManager.askPersonHairColor(),
                    scannerManager.askPersonNationality(),
                    scannerManager.askPersonLocation()
            );
            if (personToAdd.compareTo(collectionManager.getFirst()) < 0)
                collectionManager.addToCollection(personToAdd);
            else
                throw new NotMinException();
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста! Используйте команду 'add'.");
        } catch (NotMinException exception) {
            ConsoleManager.printErr("Данный элемент не минимален!");
        } catch (IncorrectInputInScriptException exception) {
            ConsoleManager.printErr("Не удалось выполнить скрипт.");
        }
        return false;

    }
}
