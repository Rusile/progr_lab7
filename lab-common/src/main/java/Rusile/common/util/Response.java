package Rusile.common.util;

import Rusile.common.people.Person;

import java.io.Serializable;
import java.util.Deque;

public class Response implements Serializable {

    private String messageToResponse;
    private Person personToResponse;
    private Deque<Person> collectionToResponse;

    public Response(String messageToResponse) {
        this.messageToResponse = messageToResponse;
    }

    public Response(String messageToResponse, Person personToResponse) {
        this.messageToResponse = messageToResponse;
        this.personToResponse = personToResponse;
    }

    public Response(String messageToResponse, Deque<Person> collectionToResponse) {
        this.messageToResponse = messageToResponse;
        this.collectionToResponse = collectionToResponse;
    }

    public Response(Person personToResponse) {
        this.personToResponse = personToResponse;
    }

    public Response(Deque<Person> collectionToResponse) {
        this.collectionToResponse = collectionToResponse;
    }

    public String getMessageToResponse() {
        return messageToResponse;
    }

    public Person getPersonToResponse() {
        return personToResponse;
    }

    public Deque<Person> getCollectionToResponse() {
        return collectionToResponse;
    }

    public String getInfoAboutResponse() {
        return "Response contains: " + (messageToResponse == null ? "" : "\nmessage:\n " + getMessageToResponse() )
                + (personToResponse == null ? "" : "\nperson info:\n" + getPersonToResponse().toString())
                + (collectionToResponse == null ? "" : "\ncollection:\n" + getCollectionToResponse().toString());
    }

    @Override
    public String toString() {
        return (messageToResponse == null ? "" : messageToResponse)
                + (personToResponse == null ? "" : "\n" + personToResponse)
                + ((collectionToResponse == null) ? "" : (collectionToResponse.isEmpty()) ? "Collection is empty." : "\n"
                + collectionToResponse);
    }
}