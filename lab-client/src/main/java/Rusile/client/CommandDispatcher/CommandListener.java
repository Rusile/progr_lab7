package Rusile.client.CommandDispatcher;

import Rusile.client.util.ScannerManager;
import Rusile.common.util.TextWriter;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandListener {
    private final Scanner sc;


    public CommandListener(InputStream inputStream) {
        sc = new Scanner(inputStream);
    }

    public CommandToSend readCommand(Scanner scanner) {
        try {
            TextWriter.printInfoMessage("Enter a command:");
            System.out.print(ScannerManager.INPUT_COMMAND);
            String[] splitedInput = scanner.nextLine().trim().split(" ");
            if (splitedInput[0].equals("EOF")) return null;
            String commandName = splitedInput[0].toLowerCase(Locale.ROOT);
            String[] commandsArgs = Arrays.copyOfRange(splitedInput, 1, splitedInput.length); //?
            return new CommandToSend(commandName, commandsArgs);

        } catch (NoSuchElementException e) {
            TextWriter.printErr("An invalid character has been entered, forced shutdown!");
            //System.exit(1);
            return null;
        }
    }
}
