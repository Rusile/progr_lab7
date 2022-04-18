package Rusile.server.ClientCommands;

import Rusile.common.people.Person;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

/**
 * 'remove_head' command. Prints and deletes first element of collection.
 */
public class RemoveHeadCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        super("remove_head", " output the first element of the collection and delete it", 0);
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
        else {
            Person headPerson = collectionManager.removeHead();
            return new Response(TextWriter.getGreenText("This user has been deleted: "), headPerson);
        }
    }
}
