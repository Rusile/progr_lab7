package Rusile.client;

import Rusile.client.CommandDispatcher.CommandToSend;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScriptReader {
    private static Set<String> namesOfRanScripts = new HashSet<>();
    private final String filename;
    private final File path;

    public ScriptReader(CommandToSend commandToSend) {
        if (scriptAlreadyRan(commandToSend.getCommandArgs()[0])) {
            throw new IllegalArgumentException("This script already ran");
        }
        this.filename = commandToSend.getCommandArgs()[0];
        namesOfRanScripts.add(filename);
        path = new File(new File(System.getProperty("user.dir")), filename);//Path.of(filename).toAbsolutePath();
    }

    private boolean scriptAlreadyRan(String commandLine) {
        return namesOfRanScripts.contains(commandLine);
    }

    public void stopScriptReading() {
        namesOfRanScripts.remove(filename);
    }

    public File getPath() {
        return path;
    }

}
