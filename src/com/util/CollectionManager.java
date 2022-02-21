package com.util;

import com.people.Color;
import com.people.Person;

import java.time.LocalDateTime;
import java.util.ArrayDeque;

/**
 * This class realizes all operations with the collection
 */
public class CollectionManager {
    private ArrayDeque<Person> peopleCollection = new ArrayDeque<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;

        loadCollection();
    }

    /**
     * @return The collecton itself.
     */
    public ArrayDeque<Person> getCollection() {
        return peopleCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return peopleCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return peopleCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public Person getFirst() {
        if (peopleCollection.isEmpty()) return null;
        return peopleCollection.getFirst();
    }

    /**
     * @return The last element of the collection or null if collection is empty.
     */
    public Person getLast() {
        if (peopleCollection.isEmpty()) return null;
        return peopleCollection.getLast();
    }

    /**
     * @param id ID of the person.
     * @return A person by his ID or null if person isn't found.
     */
    public Person getById(Long id) {
        for (Person person : peopleCollection) {
            if (person.getId().equals(id)) return person;
        }
        return null;
    }

    /**
     * Removes a person from the collection.
     *
     * @param person A person to remove.
     */
    public void removeById(Person person) {
        ArrayDeque<Person> tmp = new ArrayDeque<>();
        while (!peopleCollection.isEmpty() && !peopleCollection.getFirst().getId().equals(person.getId())) {
            tmp.addLast(peopleCollection.pollFirst());
        }
        peopleCollection.removeFirst();
        while (!tmp.isEmpty()) peopleCollection.addFirst(tmp.pollLast());
    }

    /**
     * @param personToFind A person who's value will be found.
     * @return A person by his value or null if person isn't found.
     */
    public Person getByValue(Person personToFind) {
        for (Person person : peopleCollection) {
            if (person.equals(personToFind)) return person;
        }
        return null;
    }

    /**
     * Adds a new person to collection.
     *
     * @param person - A person to add.
     */
    public void addToCollection(Person person) {
        ArrayDeque<Person> tmp = new ArrayDeque<>();
        while (!peopleCollection.isEmpty() && peopleCollection.getFirst().compareTo(person) < 0) {
            tmp.addLast(peopleCollection.pollFirst());
        }
        peopleCollection.addFirst(person);
        while (!tmp.isEmpty()) peopleCollection.addFirst(tmp.pollLast());
    }


    /**
     * Clears the collection.
     */
    public void clearCollection() {
        peopleCollection.clear();
    }

    /**
     * Generates next ID. It will be the bigger one + 1).
     *
     * @return Next ID.
     */
    public Long generateNextId() {
        if (peopleCollection.isEmpty()) return 1L;
        return peopleCollection.getLast().getId() + 1;
    }

    /**
     * Deletes all people in collection who a lower than specified
     * @param personToRemove - person that to compare
     */
    public void removeLower(Person personToRemove) {
        ArrayDeque<Person> tmp = new ArrayDeque<>();
        while (!personToRemove.equals(peopleCollection.getFirst())) {
            tmp.addLast(peopleCollection.pollFirst());
        }
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        fileManager.writeCollection(peopleCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        peopleCollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Removes person from the collection.
     *
     * @param personToRemove A person to remove.
     */
    public void removeFromCollection(Person personToRemove) {
        ArrayDeque<Person> tmp = new ArrayDeque<>();
        while (!personToRemove.equals(peopleCollection.getFirst())) {
            tmp.addLast(peopleCollection.pollFirst());
        }
        peopleCollection.removeFirst();
        while (!tmp.isEmpty()) peopleCollection.addFirst(tmp.pollLast());
    }

    /**
     * Removes person from the collection.
     *
     * @return headPerson The first person in ArrayDeque to remove and show.
     */
    public Person removeHead() {
        Person headPerson = peopleCollection.pollFirst();
        return headPerson;
    }

    /**
     * Prints the collection in descending order
     */
    public void printDescending() {
        ArrayDeque<Person> tmp = new ArrayDeque<>();
        for (Person person : peopleCollection)
            tmp.addFirst(person);
        while (!tmp.isEmpty())
            System.out.println(tmp.pollFirst());
    }

    /**
     * Finds the person with the latest date of creation
     * @return personMaxByDate - person with the latest date of creation
     */
    public Person findMaxByDate() {
        Person personMaxByDate = peopleCollection.getFirst();
        LocalDateTime maxDate = personMaxByDate.getCreationDate();
        for (Person person : peopleCollection) {
            if (person.getCreationDate().compareTo(maxDate) < 0) {
                personMaxByDate = person;
                maxDate = person.getCreationDate();
            }
        }
        return personMaxByDate;
    }

    /**
     * Finds all people with the color of hair which is less than specified
     * @param hairColor - a hair's color to compare
     * @return all people with the color of hair which is less than specified
     */
    public ArrayDeque<Person> getFilteredLessByHairColorCollection(Color hairColor) {
        ArrayDeque<Person> filteredPeopleCollection = new ArrayDeque<>();
        for (Person person : peopleCollection) {
            if (hairColor.compareTo(person.getHairColor()) > 0)
                filteredPeopleCollection.addLast(person);
        }
        return filteredPeopleCollection;
    }

    @Override
    public String toString() {
        if (peopleCollection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (Person person : peopleCollection) {
            info.append(person);
            if (person != peopleCollection.getLast()) info.append("\n\n");
        }
        return info.toString();
    }

}
