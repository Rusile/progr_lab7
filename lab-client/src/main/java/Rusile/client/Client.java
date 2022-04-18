package Rusile.client;

import Rusile.client.CommandDispatcher.CommandListener;
import Rusile.client.CommandDispatcher.CommandToSend;
import Rusile.client.CommandDispatcher.CommandValidators;
import Rusile.client.NetworkManager.RequestCreator;
import Rusile.client.NetworkManager.RequestSender;
import Rusile.client.NetworkManager.ResponseReceiver;
import Rusile.common.exception.WrongAmountOfArgumentsException;
import Rusile.common.util.Request;
import Rusile.common.util.Response;
import Rusile.common.util.TextWriter;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.*;

public final class Client {

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    private static int PORT = 45846;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static Selector selector;
    private static final int maxPort = 65535;
    private static final CommandListener commandListener = new CommandListener(System.in);
    private static final RequestCreator requestCreator = new RequestCreator();


    public static void main(String[] args) {

        try {
            TextWriter.printInfoMessage("Enter hostname: ");
            String hostname = SCANNER.nextLine();
            inputPort();
            InetSocketAddress hostAddress = new InetSocketAddress(hostname, PORT);
            selector = Selector.open();
            SocketChannel client = SocketChannel.open(hostAddress);
            TextWriter.printSuccessfulMessage("Connected!");
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_WRITE);
            startSelectorLoop(client, SCANNER);
        } catch (ConnectException e) {
            TextWriter.printErr("Server with this host is temporarily unavailable. Try again later");
            main(args);
        } catch (StreamCorruptedException e) {
            TextWriter.printErr("Disconnected.");
        } catch (ClassNotFoundException e) {
            TextWriter.printErr("Trying to serialize non-serializable object");
        } catch (InterruptedException e) {
            TextWriter.printErr("Thread was interrupt while sleeping. Restart client");
        } catch (UnresolvedAddressException e) {
            TextWriter.printErr("Server with this host not found. Try again");
            main(args);
        } catch (IOException e) {
            TextWriter.printErr("Server invalid or closed. Try to connect again");
            main(args);
        } catch (NoSuchElementException e) {
            TextWriter.printErr("An invalid character has been entered, forced shutdown!");
            System.exit(1);
        }
    }

    private static void startSelectorLoop(SocketChannel channel, Scanner sc) throws IOException, ClassNotFoundException, InterruptedException {
        do {
            selector.select();
        } while (startIteratorLoop(channel, sc));
    }

    private static boolean startIteratorLoop(SocketChannel channel, Scanner sc) throws IOException, ClassNotFoundException, InterruptedException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isReadable()) {
                ResponseReceiver responseReceiver = new ResponseReceiver(channel, key, selector);
                Response response = responseReceiver.receive();
                TextWriter.printInfoMessage(response.getInfoAboutResponse());
                if (response.getCollectionToResponse() != null) {
                    TextWriter.printInfoMessage(response.getCollectionToResponse().toString());
                }
            } else if (key.isWritable()) {
                try {
                    CommandToSend commandToSend = commandListener.readCommand(sc);
                    if (commandToSend == null) return false;
                    if (commandToSend.getCommandName().equalsIgnoreCase("execute_script")) {
                        CommandValidators.validateAmountOfArgs(commandToSend.getCommandArgs(), 1);
                        ScriptReader scriptReader = new ScriptReader(commandToSend);
                        startSelectorLoop(channel, new Scanner(scriptReader.getPath()));
                        scriptReader.stopScriptReading();
                        startSelectorLoop(channel, SCANNER);

                    }
                    RequestSender requestSender = new RequestSender(channel, selector);
                    Request request = requestCreator.createRequestOfCommand(commandToSend);
                    if (request == null) throw new NullPointerException();
                    requestSender.send(request);
                } catch (NullPointerException | IOException | IllegalArgumentException | WrongAmountOfArgumentsException e) {
                    TextWriter.printErr(e.getMessage());
                }


            }

        }
        return true;
    }


    private static void inputPort() {
        TextWriter.printInfoMessage("Do you want to use a default port? [y/n]");
        try {
            String s = SCANNER.nextLine().trim().toLowerCase(Locale.ROOT);
            if ("n".equals(s)) {
                TextWriter.printInfoMessage("Please enter the remote host port (1-65535)");
                String port = SCANNER.nextLine();
                try {
                    int portInt = Integer.parseInt(port);
                    if (portInt > 0 && portInt <= maxPort) {
                        PORT = portInt;
                    } else {
                        TextWriter.printErr("The number did not fall within the limits, repeat the input");
                        inputPort();
                    }
                } catch (IllegalArgumentException e) {
                    TextWriter.printErr("Error processing the number, repeat the input");
                    inputPort();
                }
            } else if (!"y".equals(s)) {
                TextWriter.printErr("You entered not valid symbol, try again");
                inputPort();
            }
        } catch (NoSuchElementException e) {
            TextWriter.printErr("An invalid character has been entered, forced shutdown!");
            System.exit(1);
        }
    }


}
