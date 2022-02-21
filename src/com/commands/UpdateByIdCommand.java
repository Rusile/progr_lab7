package com.commands;

import com.exception.CollectionIsEmptyException;
import com.exception.IncorrectInputInScriptException;
import com.exception.PersonNotFoundException;
import com.exception.WrongAmountOfArgumentsException;
import com.people.*;
import com.util.CollectionManager;
import com.util.ConsoleManager;
import com.util.ScannerManager;

import java.time.LocalDateTime;

/**
 * 'update' command. Updates the information about selected Person.
 */
public class UpdateByIdCommand extends Command {
    private final CollectionManager collectionManager;
    private final ScannerManager scannerManager;

    public UpdateByIdCommand(CollectionManager collectionManager, ScannerManager scannerManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
        this.scannerManager = scannerManager;
    }

    /**
     * Executes the command.
     *
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

            String name = person.getName();
            Coordinates coordinates = person.getCoordinates();
            LocalDateTime creationDate = person.getCreationDate();
            int height = person.getHeight();
            Color eyeColor = person.getEyeColor();
            Color hairColor = person.getHairColor();
            Country nationality = person.getNationality();
            Location location = person.getLocation();


            collectionManager.removeFromCollection(person);

            if (scannerManager.askQuestion("Хотите изменить имя человека?"))
                name = scannerManager.askPersonName();
            if (scannerManager.askQuestion("Хотите изменить координаты человека?"))
                coordinates = scannerManager.askCoordinates();
            if (scannerManager.askQuestion("Хотите изменить рост студента?"))
                height = scannerManager.askPersonHeight();
            if (scannerManager.askQuestion("Хотите изменить цвет глаз человека?"))
                eyeColor = scannerManager.askPersonEyeColor();
            if (scannerManager.askQuestion("Хотите изменить цвет волос человека?"))
                hairColor = scannerManager.askPersonHairColor();
            if (scannerManager.askQuestion("Хотите изменить национальность человека?"))
                nationality =  scannerManager.askPersonNationality();
            if (scannerManager.askQuestion("Хотите изменить локацию человека?"))
                location = scannerManager.askPersonLocation();

            collectionManager.addToCollection(new Person(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    height,
                    eyeColor,
                    hairColor,
                    nationality,
                    location
            ));
            ConsoleManager.printSuccessfulMessage("Данные о человеке успешно обновлены!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ConsoleManager.printErr("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ConsoleManager.printErr("ID должен быть представлен числом!");
        } catch (PersonNotFoundException exception) {
            ConsoleManager.printErr("Человека с таким ID в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {
            ConsoleManager.printErr("Не удалось выполнить скрипт.");
        }
        return false;
    }
}
