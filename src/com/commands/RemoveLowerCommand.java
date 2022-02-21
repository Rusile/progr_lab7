package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.IncorrectInputInScriptException;
import com.exception.PersonNotFoundException;
import com.exception.WrongAmountOfArgumentsException;
import com.people.Person;
import com.util.CollectionManager;
import com.util.ConsoleManager;
import com.util.ScannerManager;

import java.time.LocalDateTime;

/**
 * 'lower_lower' command. Removes elements lower than user entered.
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final ScannerManager scannerManager;

    public RemoveLowerCommand(CollectionManager collectionManager, ScannerManager scannerManager) {
        super("remove_lower {element}", "удалить из коллекции все элементы меньше заданного");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Person personToFind = new Person(
                    0L,
                    scannerManager.askPersonName(),
                    scannerManager.askCoordinates(),
                    LocalDateTime.now(),
                    scannerManager.askPersonHeight(),
                    scannerManager.askPersonEyeColor(),
                    scannerManager.askPersonHairColor(),
                    scannerManager.askPersonNationality(),
                    scannerManager.askPersonLocation()
            );
            Person person = collectionManager.getByValue(personToFind);
            if (person == null) throw new PersonNotFoundException();
            int collectionSize = collectionManager.collectionSize();
            collectionManager.removeLower(person);
            ConsoleManager.printSuccessfulMessage("Удалено пользователей: " + (collectionManager.collectionSize() - collectionSize));
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        } catch (PersonNotFoundException exception) {
            ConsoleManager.printErr("Группы с такими параметрами в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {
            ConsoleManager.printErr("Не удалось выполнить скрипт.");
        }
        return false;
    }
}