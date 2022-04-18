package Rusile.server.ClientCommands;


import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

/**
 * 'add' command. Add person in collection.
 */

public class AddCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", " adds a new person in the collection", 0);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        collectionManager.addToCollection(request.getPersonArgument());
        return new Response(TextWriter.getGreenText("Данные о персоне успешно добавлены!"), request.getPersonArgument());
    }

}
