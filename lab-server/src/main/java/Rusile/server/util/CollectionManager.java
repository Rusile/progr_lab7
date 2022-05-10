package Rusile.server.util;

import Rusile.common.exception.PersonNotMinException;
import Rusile.common.people.Color;
import Rusile.common.people.Person;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * This class realizes all operations with the collection
 */
public class CollectionManager {
    private ArrayDeque<Person> peopleCollection = new ArrayDeque<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;


    public Deque<Person> sort(Deque<Person> collection) {
        Deque<Person> sorted = new ArrayDeque<>();
        collection.stream().sorted().forEach(sorted::addLast);
        return sorted;
    }

    /**
     * @return The collecton itself.
     */
    public Deque<Person> getCollection() {
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
        try {
            return (Person) peopleCollection.stream()
                    .filter(s -> s.getId()
                            .equals(id))
                    .toArray()[0];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Removes a person from the collection.
     *
     * @param id A person id to remove.
     */
    public void removeById(Long id) {
        peopleCollection.removeIf(p -> p.getId().equals(id));
    }

    /**
     * @param personToFind A person who's value will be found.
     * @return A person by his value or null if person isn't found.
     */
    public Person getByValue(Person personToFind) {
        try {
            return peopleCollection.stream().filter(p -> p.equals(personToFind)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void addIfMin(Person personToAdd) throws PersonNotMinException {
        if (personToAdd.compareTo(getFirst()) < 0)
            addToCollection(personToAdd);
        else throw new PersonNotMinException("This person is not minimal!");
    }

    /**
     * Adds a new person to collection.
     *
     * @param person - A person to add.
     */
    public void addToCollection(Person person) {
        peopleCollection.add(person);
        peopleCollection = new ArrayDeque<>(sort(peopleCollection));
        setLastInitTime(LocalDateTime.now());
    }


    public void setLastInitTime(LocalDateTime lastInitTime) {
        this.lastInitTime = lastInitTime;
    }

    public void setLastSaveTime(LocalDateTime lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
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
     *
     * @param personToRemove - person that to compare
     */
    public void removeLower(Person personToRemove) {
        peopleCollection.removeIf(p -> p.compareTo(personToRemove) < 0);
    }


    public void setPeopleCollection(ArrayDeque<Person> peopleCollection) {
        this.peopleCollection = peopleCollection;
    }

    /**
     * Removes person from the collection.
     *
     * @param personToRemove A person to remove.
     */
    public void removeFromCollection(Person personToRemove) {
        peopleCollection.removeIf(person -> person.equals(personToRemove));
    }

    /**
     * Removes person from the collection.
     *
     * @return headPerson The first person in ArrayDeque to remove and show.
     */
    public Person removeHead() {
        return peopleCollection.pollFirst();
    }

    /**
     * Prints the collection in descending order
     */
    public Deque<Person> getDescending() {
        Deque<Person> tmp = new ArrayDeque<>();
        peopleCollection.stream().sorted(Comparator.reverseOrder()).forEach(tmp::addLast);
        return tmp;
    }

    /**
     * Finds the person with the latest date of creation
     *
     * @return personMaxByDate - person with the latest date of creation
     */
    public Person findMaxByDate() {
        return peopleCollection.stream().max(Comparator.comparing(Person::getCreationDate)).get();
    }

    /**
     * Finds all people with the color of hair which is less than specified
     *
     * @param hairColor - a hair's color to compare
     * @return all people with the color of hair which is less than specified
     */
    public Deque<Person> getFilteredLessByHairColorCollection(Color hairColor) {
        ArrayDeque<Person> filteredPeopleCollection = new ArrayDeque<>();
        peopleCollection.stream().filter(person -> person.getHairColor().compareTo(hairColor) < 0).forEach(filteredPeopleCollection::addLast);
        return filteredPeopleCollection;
    }

    public String getInfo() {
        return "Collection type: " + ArrayDeque.class + ", type of elements: "
                + Person.class
                + (getLastInitTime() == null ? "" : (", date of initialization: " + getLastInitTime()))
                + (getLastSaveTime() == null ? "" : (", date of last saving: " + getLastSaveTime()))
                + ", number of elements: " + peopleCollection.size();


    }

    @Override
    public String toString() {
        if (peopleCollection.isEmpty()) return "Collection is empty!";

        StringBuilder info = new StringBuilder();
        for (Person person : peopleCollection) {
            info.append(person);
            if (person != peopleCollection.getLast()) info.append("\n\n");
        }
        return info.toString();
    }

}
