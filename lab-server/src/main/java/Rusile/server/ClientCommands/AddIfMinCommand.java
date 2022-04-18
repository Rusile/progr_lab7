package Rusile.server.ClientCommands;

import Rusile.common.exception.*;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

/**
 * 'add_if_min {element}' command. Add person if he will be min in the collection.
 */
public class AddIfMinCommand extends AbstractCommand {
    private final CollectionManager collectionManager;



    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min", " add a new item to the collection if its value is less than that of the smallest item in this collection", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        try {
            collectionManager.addIfMin(request.getPersonArgument());
            return new Response(TextWriter.getGreenText("New person successfully added!"), request.getPersonArgument());
        } catch (PersonNotMinException e) {
            return new Response(TextWriter.getRedText(e.getMessage()));
        }


    }
}
