package Rusile.server;

import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.Serializers;
import Rusile.server.ClientCommands.AbstractCommand;
import Rusile.server.exceptions.DisconnectInitException;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public final class Server {
    private static Selector selector;
    protected static String fileName;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        fileName = args[0];
        ConsoleThread consoleThread = new ConsoleThread();
        consoleThread.start();
        startServer(args);
        consoleThread.shutdown();
    }

    private static void startServer(String[] args) {
        ServerConfig.logger.info("Server started");
        String fileName = args[0];
        ServerConfig.fileManager.readCollection(fileName);
        try {
            selector = Selector.open();
            ServerSocketChannel server = initChannel(selector);
            startSelectorLoop(server);
        } catch (IOException e) {
            ServerConfig.logger.fatal("Some problems with IO. Try again");
        } catch (ClassNotFoundException e) {
            ServerConfig.logger.error("Trying to serialize non-serializable object");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startSelectorLoop(ServerSocketChannel channel) throws IOException, ClassNotFoundException, InterruptedException {
        while (channel.isOpen()) {
            selector.select();
            startIteratorLoop(channel);
        }
    }

    private static void startIteratorLoop(ServerSocketChannel channel) throws IOException, ClassNotFoundException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();

            if (key.isAcceptable()) {
                SocketChannel socketChannel = channel.accept();
                ServerConfig.logger.info("Server get connection from " + socketChannel.getLocalAddress());
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ServerConfig.logger.info("Client " + socketChannel.getLocalAddress() + " trying to send message");
                Request request = IOController.getRequest(socketChannel);
                AbstractCommand command = ServerConfig.commandManager.initCommand(request);
                ServerConfig.logger.info("Server receive [" + request.getCommandName() + "] command");
                try {
                    Response response = IOController.buildResponse(command, request);
                    ByteBuffer buffer = Serializers.serializeResponse(response);
                    socketChannel.write(buffer);
                    ServerConfig.logger.info("Server wrote response to client");
                } catch (DisconnectInitException e) {
                    ServerConfig.fileManager.writeCollection(ServerConfig.collectionManager.getCollection());
                    ServerConfig.logger.info("Client " + socketChannel.getLocalAddress() + " init disconnect. Collection successfully saved");
                    socketChannel.close();
                    break;
                }
            }
        }
    }

    private static ServerSocketChannel initChannel(Selector selector) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        ServerConfig.logger.info("Socket opened");
        server.socket().bind(new InetSocketAddress(ServerConfig.SERVER_PORT));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        return server;
    }

}
