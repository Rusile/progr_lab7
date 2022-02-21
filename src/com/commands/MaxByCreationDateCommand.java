package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.WrongAmountOfArgumentsException;
import com.people.Person;
import com.util.CollectionManager;
import com.util.ConsoleManager;

/**
 * 'max_by_creation_date' command. Prints information of the person with the latest date of creation.
 */
public class MaxByCreationDateCommand extends Command {
    private final CollectionManager collectionManager;

    public MaxByCreationDateCommand(CollectionManager collectionManager) {
        super("max_by_creation_date", "вывести любой объект из коллекции, значение поля creationDate которого является максимальным");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Person person = collectionManager.findMaxByDate();
            ConsoleManager.printSuccessfulMessage("Человек с наиболее поздней датой внесения в базу успешно найден!");
            System.out.println(person);
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        }
        return false;
    }
}
