package Rusile.server.ClientCommands;

import Rusile.common.people.Person;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

/**
 * 'lower_lower' command. Removes elements lower than user entered.
 */
public class RemoveLowerCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower", " remove from the collection all items less than the specified one", 1);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        Person personToFind = collectionManager.getByValue(request.getPersonArgument());
        if (personToFind == null)
            return new Response(TextWriter.getRedText("There is no such person in the collection!"));
        else {
            int collectionSize = collectionManager.getCollection().size();
            collectionManager.removeLower(personToFind);
            return new Response(TextWriter.getGreenText("Remotely users: " + (collectionManager.collectionSize() - collectionSize)));
        }
    }
}