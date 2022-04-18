package Rusile.server;

import java.io.File;
import java.util.Scanner;

import Rusile.server.ClientCommands.*;
import Rusile.server.parser.FileManager;
import Rusile.server.util.CollectionManager;
import Rusile.server.util.CommandManager;
import jdk.tools.jmod.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConfig {
    public static final Logger logger = LogManager.getLogger();
    public static final int SERVER_PORT = 45846;
    public static final Scanner scanner = new Scanner(System.in);
    public static final FileManager fileManager = new FileManager(Server.fileName);
    public static CollectionManager collectionManager = new CollectionManager(fileManager);
    public static CommandManager commandManager = new CommandManager(
            new HelpCommand(),
            new InfoCommand(collectionManager),
            new ShowCommand(collectionManager),
            new AddCommand(collectionManager),
            new UpdateByIdCommand(collectionManager),
            new RemoveByIdCommand(collectionManager),
            new ClearCommand(collectionManager),
            null,
            new ExitCommand(),
            null,
            new AddIfMinCommand(collectionManager),
            new RemoveHeadCommand(collectionManager),
            new RemoveLowerCommand(collectionManager),
            new MaxByCreationDateCommand(collectionManager),
            new FilterLessThanHairColorCommand(collectionManager),
            new PrintDescendingCommand(collectionManager)
    );

}
