package Rusile.server;

import Rusile.common.people.Person;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;
import Rusile.server.ClientCommands.AbstractCommand;
import Rusile.server.exceptions.DisconnectInitException;
import Rusile.server.util.RequestBuilder;
import Rusile.server.util.ServerSocketIO;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Server {
    static String fileName;
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static ConsoleThread consoleThread;

    private static boolean reconnectionMode = false;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            ServerConfig.logger.error("Filename of collection have not been entered!");
            System.exit(1);
        }
        fileName = args[0];

        try {
            if (reconnectionMode) {
                serverSocket.close();
                serverSocket = new ServerSocket(ServerConfig.PORT);
                reconnectionMode = false;
                startServer(args, serverSocket);
            } else {
                createServerSocket();
                ServerConfig.fileManager.readCollection(fileName);
                consoleThread = new ConsoleThread();
                consoleThread.start();

                startServer(args, serverSocket);
                consoleThread.shutdown();
            }
        } catch (IOException e) {
            ServerConfig.logger.fatal("Apparently client disconnected. Trying to reconnect.");
            reconnectionMode = true;
            main(args);
        }
    }

    private static void startServer(String[] args, ServerSocket serverSocket) throws IOException {
        try {
            ServerConfig.logger.info("Server started, trying to get the connection...");
            clientSocket = serverSocket.accept();
            ServerConfig.logger.info("Server get connection from " + clientSocket.getLocalAddress());
            startSelectorLoop(clientSocket);
        } catch (ClassNotFoundException e) {
            ServerConfig.logger.error("Trying to serialize non-serializable object");
        } catch (InterruptedException e) {
            ServerConfig.logger.error(e.getMessage());
        }
    }

    private static void startSelectorLoop(Socket socket) throws IOException, ClassNotFoundException, InterruptedException {
        while (socket.isConnected()) {
            startIteratorLoop(socket);
        }
    }

    private static void startIteratorLoop(Socket socket) throws IOException, ClassNotFoundException {
        try {

            ServerSocketIO socketIO = new ServerSocketIO(socket);
            Request request = (Request) socketIO.receive();

            AbstractCommand command = ServerConfig.commandManager.initCommand(request);
            ServerConfig.logger.info("Server receive [" + request.getCommandName() + "] command");

            Response response = RequestBuilder.build(command, request);

            socketIO.send(response);

            ServerConfig.logger.info("Server wrote response to client");
        } catch (DisconnectInitException e) {
            serverSocket.close();
            socket.close();
            ServerConfig.fileManager.writeCollection((ArrayDeque<Person>) ServerConfig.collectionManager.getCollection());
            ServerConfig.logger.info("Server stopped. Collection successfully saved");
        }
    }


    private static void createServerSocket() {
        Scanner sc = ServerConfig.scanner;
        TextWriter.printInfoMessage("Do you want to use a default port? [y/n]");
        try {
            String s = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if ("n".equals(s)) {
                TextWriter.printInfoMessage("Please enter the port (1-65535)");
                String port = sc.nextLine();
                try {
                    int portInt = Integer.parseInt(port);
                    if (portInt > 0 && portInt <= 65535) {
                        ServerConfig.PORT = portInt;
                        serverSocket = new ServerSocket(portInt);
                    } else {
                        TextWriter.printErr("The number did not fall within the limits, repeat the input");
                        createServerSocket();
                    }
                } catch (IllegalArgumentException e) {
                    TextWriter.printErr("Error processing the number, repeat the input");
                    createServerSocket();
                }
            } else if ("y".equals(s)) {
                serverSocket = new ServerSocket(ServerConfig.PORT);
            } else {
                TextWriter.printErr("You entered not valid symbol, try again");
                createServerSocket();
            }
        } catch (NoSuchElementException e) {
            TextWriter.printErr("An invalid character has been entered, forced shutdown!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            TextWriter.printErr("Error processing the number, repeat the input");
            createServerSocket();
        } catch (BindException e) {
            TextWriter.printErr("This port is unavailable. Enter another one!");
            createServerSocket();
        } catch (IOException e) {
            TextWriter.printErr("Some problems with IO. ServerSocket with this port can't be created.");
            createServerSocket();
        }
    }

}
