package com.commands;

import com.exception.WrongAmountOfArgumentsException;
import com.util.ConsoleManager;

/**
 * 'execute_script' command. Executes scripts from a file.
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand() {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
    }

    /**
     * Executes the script.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            ConsoleManager.printSuccessfulMessage("Выполняю скрипт '" + argument + "'...");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ConsoleManager.printErr("Использование: '" + getName() + "'");
        }
        return false;
    }
}