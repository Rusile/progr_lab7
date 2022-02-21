package com.commands;

/**
 * Abstract Command class contains name and description.
 */
public abstract class Command {

    private String name;
    private String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @param argument after command name.
     * @return execute status
     */
    public abstract boolean execute(String argument);

    /**
     * @return Name and usage way of the command.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Description of the command.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }

    ;

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Command other = (Command) obj;
        return name.equals(other.name) && description.equals(other.description);
    }
}
