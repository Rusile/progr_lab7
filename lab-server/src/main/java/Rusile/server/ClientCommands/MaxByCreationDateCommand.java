package Rusile.server.ClientCommands;

import Rusile.common.exception.CollectionIsEmptyException;
import Rusile.common.exception.WrongAmountOfArgumentsException;
import Rusile.common.people.Person;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;


/**
 * 'max_by_creation_date' command. Prints information of the person with the latest date of creation.
 */
public class MaxByCreationDateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public MaxByCreationDateCommand(CollectionManager collectionManager) {
        super("max_by_creation_date",
                " output any object from the collection whose CreationDate field value is the maximum",
                0);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        if (collectionManager.collectionSize() != 0)
            return new Response(TextWriter.getGreenText("The person with the latest date of entry into the database has been successfully found!"), collectionManager.findMaxByDate());
        else
            return new Response(TextWriter.getRedText("Collection is empty!"));

    }
}
