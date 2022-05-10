package Rusile.server.ClientCommands;


import Rusile.common.exception.CollectionIsEmptyException;
import Rusile.common.exception.PersonNotFoundException;
import Rusile.common.exception.WrongAmountOfArgumentsException;
import Rusile.common.people.Person;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

/**
 * 'remove_by_id <ID>' command. Deletes the person with the similar id.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", " delete an item from the collection by ID", 1);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        Long id = request.getNumericArgument();
        Person personToRemove = collectionManager.getById(id);
        if (personToRemove == null)
            return new Response(TextWriter.getRedText("The person with such an id does not exist!"));
        else {
            collectionManager.removeById(id);
            return new Response(TextWriter.getGreenText("The person was successfully deleted!"));
        }
    }
}