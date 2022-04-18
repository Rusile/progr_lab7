package Rusile.server.ClientCommands;

import Rusile.common.exception.CollectionIsEmptyException;
import Rusile.common.exception.WrongAmountOfArgumentsException;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.server.util.CollectionManager;

/**
 * 'show' command. Shows information about all elements of the collection.
 */
public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", " output all elements of the collection", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
       return new Response(collectionManager.toString());
    }
}

