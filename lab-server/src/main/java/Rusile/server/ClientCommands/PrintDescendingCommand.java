package Rusile.server.ClientCommands;

import Rusile.common.exception.CollectionIsEmptyException;
import Rusile.common.exception.WrongAmountOfArgumentsException;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

/**
 * 'print_descending' command. Prints the collection in descending order.
 */
public class PrintDescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", " display the elements of the collection in descending order", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Collection is empty!"));
        else
            return new Response(TextWriter.getGreenText("A collection with elements in descending order has been successfully obtained!"), collectionManager.getDescending());
    }
}

