package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.IncorrectInputInScriptException;
import com.exception.WrongAmountOfArgumentsException;
import com.people.Color;
import com.people.Person;
import com.util.CollectionManager;
import com.util.ConsoleManager;
import com.util.ScannerManager;

import java.util.ArrayDeque;

/**
 * 'filter_less_than_hair_color' command. Add person if his hair's color will be min in the collection.
 */
public class FilterLessThanHairColorCommand extends Command {
    private final CollectionManager collectionManager;
    private final ScannerManager scannerManager;

    public FilterLessThanHairColorCommand(CollectionManager collectionManager, ScannerManager scannerManager) {
        super("filter_less_than_hair_color", "вывести элементы, значение поля hairColor которых меньше заданного");
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
            Color filterColor = scannerManager.askPersonHairColor();
            ArrayDeque<Person> filteredCollection = collectionManager.getFilteredLessByHairColorCollection(filterColor);
            if (!filteredCollection.isEmpty()) {
                ConsoleManager.printSuccessfulMessage("Коллекция успешно отфильтрована:");
                for (Person person : filteredCollection)
                    System.out.println(person);
            } else {
                ConsoleManager.printErr("В коллекции нет элементов с меньшим цветом!");
            }
            return true;

        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        } catch (IncorrectInputInScriptException e) {
            ConsoleManager.printErr("Не удалось выполнить скрипт.");
        }


        return false;
    }
}
