package Rusile.common.util;

import Rusile.common.interfaces.Data;
import Rusile.common.people.Person;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Deque;

public class Response implements Serializable, Data {

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

    @Override
    public String getData() {
        return "Response contains: " + (messageToResponse == null ? "" : ("\n-Message:\n" + getMessageToResponse()) )
                + (personToResponse == null ? "" : ("\n-Person's data:\n" +  getPersonToResponse().toString()) )
                + (collectionToResponse == null ? "" : ("\n-Collection:\n" + getCollectionToResponse()) );
    }

    @Override
    public String toString() {
        return "Response[" + messageToResponse + "]";
    }
}