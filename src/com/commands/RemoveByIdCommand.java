package com.commands;


import com.exception.CollectionIsEmptyException;
import com.exception.IncorrectInputInScriptException;
import com.exception.PersonNotFoundException;
import com.exception.WrongAmountOfArgumentsException;
import com.people.Person;
import com.util.CollectionManager;
import com.util.ConsoleManager;

/**
 * 'remove_by_id <ID>' command. Deletes the person with the similar id.
 */
public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long id = Long.parseLong(argument);
            Person person = collectionManager.getById(id);
            if (person == null) throw new PersonNotFoundException();
            collectionManager.removeById(person);
            ConsoleManager.printSuccessfulMessage("Группа успешно удалена!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ConsoleManager.printErr("ID должен быть представлен числом!");
        } catch (PersonNotFoundException exception) {
            ConsoleManager.printErr("Группы с таким ID в коллекции нет!");
        }
        return false;
    }
}