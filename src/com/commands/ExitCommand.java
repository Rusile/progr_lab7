package com.commands;

import com.exception.WrongAmountOfArgumentsException;
import com.util.ConsoleManager;

/**
 * 'exit' command. Closes the program.
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            ConsoleManager.printSuccessfulMessage("Всего ХО-РО-ШЕ-ГО!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        }
        return false;
    }
}
