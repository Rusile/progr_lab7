package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.ConsoleManager;

/**
 * 'print_descending' command. Prints the collection in descending order.
 */
public class PrintDescendingCommand extends Command {
    private final CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
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
            collectionManager.printDescending();
            return true;
        } catch (WrongAmountOfArgumentsException e) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException e) {
            ConsoleManager.printErr("Коллекция пуста!");
        }
        return false;
    }
}

