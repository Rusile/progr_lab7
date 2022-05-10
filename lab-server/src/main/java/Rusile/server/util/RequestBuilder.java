package Rusile.server.util;

import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.server.ClientCommands.AbstractCommand;
import Rusile.server.ServerConfig;
import Rusile.server.exceptions.DisconnectInitException;

public class RequestBuilder {
    public static Response build(AbstractCommand command, Request request) throws DisconnectInitException {
        if (request.getPersonArgument() != null)
            request.getPersonArgument().setId(ServerConfig.collectionManager.generateNextId());
        if (command.getName().equals("exit")) {
            throw new DisconnectInitException("Client initialize disconnect");
        }
        return command.execute(request);
    }
}
