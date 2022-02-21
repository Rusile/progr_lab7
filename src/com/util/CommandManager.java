package com.util;


import com.commands.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * This class realizes all commands' operations
 */
public class CommandManager {
    private final List<Command> commands = new ArrayList<>();
    private final Command helpCommand;
    private final Command infoCommand;
    private final Command showCommand;
    private final Command addCommand;
    private final Command updateByIdCommand;
    private final Command removeByIdCommand;
    private final Command clearCommand;
    private final Command saveCommand;
    private final Command exitCommand;
    private final Command executeScriptCommand;
    private final Command addIfMinCommand;
    private final Command removeLowerCommand;
    private final Command removeHeadCommand;
    private final Command maxByCreationDateCommand;
    private final Command filterLessThanHairColorCommand;
    private final Command printDescendingCommand;

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateByIdCommand, Command removeByIdCommand, Command clearCommand, Command saveCommand, Command exitCommand, Command executeScriptCommand, Command addIfMinCommand, Command removeHeadCommand, Command removeLowerCommand, Command showMaxByCreationDateCommand, Command filterLessThanHairColorCommand, Command printDescendingCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateByIdCommand = updateByIdCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.removeHeadCommand = removeHeadCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.maxByCreationDateCommand = showMaxByCreationDateCommand;
        this.filterLessThanHairColorCommand = filterLessThanHairColorCommand;
        this.printDescendingCommand = printDescendingCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateByIdCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(addIfMinCommand);
        commands.add(removeHeadCommand);
        commands.add(removeLowerCommand);
        commands.add(showMaxByCreationDateCommand);
        commands.add(filterLessThanHairColorCommand);
        commands.add(printDescendingCommand);
    }

    /**
     * @return List of manager's com.commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Prints that command is not found.
     *
     * @param command Command, which is not found.
     * @return Command exit status.
     */
    public boolean noSuchCommand(String command) {
        System.out.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
        return false;
    }

    /**
     * Prints info about the all com.commands.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean help(String argument) {
        if (helpCommand.execute(argument)) {
            for (Command command : commands) {
                System.out.println("Название команды: " + command.getName() + ". Функционал: " + command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean info(String argument) {
        return infoCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean show(String argument) {
        return showCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean add(String argument) {
        return addCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean update(String argument) {
        return updateByIdCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeById(String argument) {
        return removeByIdCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean clear(String argument) {
        return clearCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean save(String argument) {
        return saveCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean exit(String argument) {
        return exitCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String argument) {
        return executeScriptCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean addIfMin(String argument) {
        return addIfMinCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeHead(String argument) {
        return removeHeadCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeLower(String argument) {
        return removeLowerCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean maxByCreationDate(String argument) {
        return maxByCreationDateCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean filterLessThanHairColor(String argument) {
        return filterLessThanHairColorCommand.execute(argument);
    }

    /**
     * Executes needed command.
     *
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printDescending(String argument) {
        return printDescendingCommand.execute(argument);
    }
}

