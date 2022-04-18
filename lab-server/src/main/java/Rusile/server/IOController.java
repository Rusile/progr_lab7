package Rusile.server;

import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.Serializers;
import Rusile.server.ClientCommands.AbstractCommand;
import Rusile.server.exceptions.DisconnectInitException;
import Rusile.server.util.CollectionManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class IOController {

    public static Request getRequest(SocketChannel in) throws IOException, ClassNotFoundException {
        ByteBuffer readBuffer = ByteBuffer.allocate(4096);
        in.read(readBuffer);
        return Serializers.deSerializeRequest(readBuffer.array());
    }

    public static Response buildResponse(AbstractCommand command, Request request) throws DisconnectInitException {
        if (request.getPersonArgument() != null)
            request.getPersonArgument().setId(ServerConfig.collectionManager.generateNextId());
        if (command.getName().equals("exit")) {
            throw new DisconnectInitException("Client initialize disconnect");
        }
        return command.execute(request);
    }
}