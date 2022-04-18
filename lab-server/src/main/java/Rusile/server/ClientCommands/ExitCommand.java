package Rusile.server.ClientCommands;

import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;

/**
 * 'exit' command. Closes the program.
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", " exits app", 0);
    }

    /**
     * Executes the command.
     *
     * @return Command execute status.
     */
    @Override
    public Response execute(Request request) {
        return new Response(TextWriter.getRedText("Connection disabled"));
    }
}
