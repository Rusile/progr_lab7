package com.commands;

import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.ConsoleManager;

/**
 * 'clear' command. Clears the collection.
 */
public class ClearCommand extends Command{
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
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
            collectionManager.clearCollection();
            ConsoleManager.printSuccessfulMessage("Коллекция очищена!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        }
        return false;
    }
}
