package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.ConsoleManager;

/**
 * 'show' command. Shows information about all elements of the collection.
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
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
            System.out.println(collectionManager);
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        }
        return false;
    }
}

