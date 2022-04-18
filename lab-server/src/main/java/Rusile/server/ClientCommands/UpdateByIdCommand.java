package Rusile.server.ClientCommands;


import Rusile.common.people.*;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

import java.time.LocalDateTime;

/**
 * 'update' command. Updates the information about selected Person.
 */
public class UpdateByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public UpdateByIdCommand(CollectionManager collectionManager) {
        super("update", " update the value of a collection item by ID", 1);
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
        Person personToUpdate = collectionManager.getById(id);
        if (personToUpdate == null)
            return new Response(TextWriter.getRedText("A person with such an id does not exist!"));
        else {
            collectionManager.removeById(id);
            collectionManager.addToCollection(personToUpdate);
            return new Response(TextWriter.getGreenText("The person's data has been successfully updated!"));
        }
    }
}
