package Rusile.common.util;

import Rusile.common.interfaces.Data;
import Rusile.common.people.Color;
import Rusile.common.people.Person;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Request implements Serializable, Data {

    private final String commandName;
    private String clientInfo;
    private LocalDateTime currentTime;
    private Long numericArgument;
    private Person personArgument;

    private Color hairColor;

    public Request(String commandName) {
        this.commandName = commandName;
    }

    public Request(String commandName, Long numericArgument) {
        this.commandName = commandName;
        this.numericArgument = numericArgument;
    }

    public Request(String commandName, Color hairColor) {
        this.commandName = commandName;
        this.hairColor = hairColor;
    }

    public Request(String commandName, Person personArgument) {
        this.commandName = commandName;
        this.personArgument = personArgument;
    }

    public Request(String commandName, Long numericArgument, Person personArgument) {
        this.commandName = commandName;
        this.numericArgument = numericArgument;
        this.personArgument = personArgument;
    }

    public String getCommandName() {
        return commandName;
    }

    public Long getNumericArgument() {
        return numericArgument;
    }

    public Person getPersonArgument() {
        return personArgument;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    @Override
    public String getData(){
        return "Name of command to send: " + commandName
                + (personArgument == null ? "" : ("\nInfo about person to send:\n " + personArgument) )
                + (numericArgument == null ? "" : ("\nNumeric argument to send:\n " + numericArgument) )
                + (hairColor == null ? "" : ("\n HairColor argument to send:\n " + hairColor) ) ;
    }


    @Override
    public String toString() {
        return "Request[" + commandName + "]" ;
    }
}
