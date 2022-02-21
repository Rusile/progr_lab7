package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.ConsoleManager;

import java.time.LocalDateTime;

/**
 * 'info' command. Prints information about the collection.
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
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
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                    lastSaveTime.toString();

            ConsoleManager.printInfo("Сведения о коллекции:");
            ConsoleManager.printInfo(" Тип: " + collectionManager.collectionType());
            ConsoleManager.printInfo(" Количество элементов: " + collectionManager.collectionSize());
            ConsoleManager.printInfo(" Дата последнего сохранения: " + lastSaveTimeString);
            ConsoleManager.printInfo(" Дата последней инициализации: " + lastInitTimeString);
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        }
            return false;

    }
}