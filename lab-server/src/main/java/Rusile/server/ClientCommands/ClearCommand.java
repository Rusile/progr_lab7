package Rusile.server.ClientCommands;

import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

/**
 * 'clear' command. Clears the collection.
 */
public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", " clears the collection", 0);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.getCollection().isEmpty())
            return new Response(TextWriter.getRedText("Collection is empty!"));
        else {
            collectionManager.clearCollection();
            return new Response(TextWriter.getGreenText("Collection have been cleared!"));
        }
    }
}
