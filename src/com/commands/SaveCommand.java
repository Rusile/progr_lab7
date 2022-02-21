package com.commands;

import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.ConsoleManager;

/**
 * 'save' command. Saves the collection to the file.
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
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
            collectionManager.saveCollection();
            ConsoleManager.printSuccessfulMessage("Коллекция успешно сохранена!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            System.out.println("Использование: '" + getName() + "'");
        }
        return false;
    }
}

