package Rusile.server;


import java.util.Scanner;

import Rusile.server.ClientCommands.*;
import Rusile.server.parser.FileManager;
import Rusile.server.util.CollectionManager;
import Rusile.server.util.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConfig {
    public static final Logger logger = LogManager.getLogger();
    public static final Scanner scanner = new Scanner(System.in);
    public static final FileManager fileManager = new FileManager(Server.fileName);
    public static CollectionManager collectionManager = new CollectionManager();
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
            new ExecuteScriptCommand(collectionManager),
            new AddIfMinCommand(collectionManager),
            new RemoveHeadCommand(collectionManager),
            new RemoveLowerCommand(collectionManager),
            new MaxByCreationDateCommand(collectionManager),
            new FilterLessThanHairColorCommand(collectionManager),
            new PrintDescendingCommand(collectionManager)
    );
    protected static int PORT = 45846;
}
