package Rusile.server.ClientCommands;

import Rusile.common.people.Color;
import Rusile.common.people.Person;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.util.CollectionManager;

import java.util.ArrayDeque;

/**
 * 'filter_less_than_hair_color' command. Add person if his hair's color will be min in the collection.
 */
public class FilterLessThanHairColorCommand extends AbstractCommand {
    private final CollectionManager collectionManager;


    public FilterLessThanHairColorCommand(CollectionManager collectionManager) {
        super("filter_less_than_hair_color", " output elements whose hairColor field value is less than the specified one", 1);
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        Color filterColor = request.getHairColor();
        ArrayDeque<Person> filteredCollection = (ArrayDeque<Person>) collectionManager.getFilteredLessByHairColorCollection(filterColor);
        if (!filteredCollection.isEmpty())
            return new Response(TextWriter.getGreenText("The collection has been filtered out successfully!"), filteredCollection);
        else
            return new Response(TextWriter.getRedText("There are no items with a smaller color in the collection!"));


    }
}

