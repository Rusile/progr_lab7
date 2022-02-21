package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.WrongAmountOfArgumentsException;
import com.people.Person;
import com.util.CollectionManager;
import com.util.ConsoleManager;

/**
 * 'remove_head' command. Prints and deletes first element of collection.
 */
public class RemoveHeadCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        super("remove_head", "вывести первый элемент коллекции и удалить его");
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
            Person headPerson = collectionManager.removeHead();
            ConsoleManager.printSuccessfulMessage("Данный пользователь удален:");
            System.out.println(headPerson);
            return true;

        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ConsoleManager.printErr("ID должен быть представлен числом!");
        } catch (WrongAmountOfArgumentsException e) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        }
        return false;
    }
}
