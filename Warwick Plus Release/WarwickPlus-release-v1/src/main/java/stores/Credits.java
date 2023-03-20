package stores;

import structures.*;

import interfaces.ICredits;

public class Credits implements ICredits {

    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     */
    public Credits() {

    }

    /**
     * Adds data about the people who worked on a given film
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(Cast[] cast, Crew[] crew, int id) {
        // TODO Build this function
        return false;
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        // TODO Build this function
        return false;
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs
     */
    @Override
    public int[] getFilmIDs() {
        // TODO Build this function
        return new int[0];
    }

    /**
     * Gets all the films worked on by a given cast ID (not cast element ID)
     * 
     * @param castID The ID of the cast member to be found
     * @return An array of film IDs relating to all films worked on by the requested
     *         cast member. If the cast member cannot be found, then return null
     */
    @Override
    public int[] getFilmIDsFromCastID(int castID) {
        // TODO Build this function
        return new int[0];
    }

    /**
     * Gets all the films worked on by a given crew ID (not crew element ID)
     * 
     * @param crewID The ID of the cast member to be found
     * @return An array of film IDs relating to all films worked on by the requested
     *         crew member. If the crew member cannot be found, then return null
     */
    @Override
    public int[] getFilmIDsFromCrewID(int crewID) {
        // TODO Build this function
        return new int[0];
    }

    /**
     * Gets all the cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return An array of Cast objects for all people that worked on a requested
     *         film. If the film cannot be found, then return null
     */
    @Override
    public Cast[] getCast(int filmID) {
        // TODO Build this function
        return new Cast[0];
    }

    /**
     * Gets all the cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return An array of Cast objects for all people that worked on a requested
     *         film. If the film cannot be found, then return null
     */
    @Override
    public Crew[] getCrew(int filmID) {
        // TODO Build this function
        return new Crew[0];
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        // TODO Build this function
        return -1;
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeofCrew(int filmID) {
        // TODO Build this function
        return -1;
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Gets the cast name for a given cast ID
     * 
     * @param castID The ID of the cast member to be found
     * @return The name of the cast member for the given ID. If the ID is invalid,
     *         then null should be returned
     */
    @Override
    public String getCastName(int castID) {
        // TODO Build this function
        return null;
    }

    /**
     * Gets the crew name for a given crew ID
     * 
     * @param crewID The ID of the crew member to be found
     * @return The name of the crew member for the given ID. If the ID is invalid,
     *         then null should be returned
     */
    @Override
    public String getCrewName(int crewID) {
        // TODO Build this function
        return null;
    }

    /**
     * Gets a list of all unique cast IDs present in the data structure
     * 
     * @return An array of all unique cast IDs. If there are no cast IDs, then
     *         return an empty array
     */
    @Override
    public int[] getUniqueCastIDs() {
        // TODO Build this function
        return new int[0];
    }

    /**
     * Gets a list of all unique crew IDs present in the data structure
     * 
     * @return An array of all unique crew IDs. If there are no crew IDs, then
     *         return an empty array
     */
    @Override
    public int[] getUniqueCrewIDs() {
        // TODO Build this function
        return new int[0];
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of Cast objects of all cast members that have the requested
     *         string in their name
     */
    @Override
    public Cast[] findCast(String cast) {
        // TODO Build this function
        return new Cast[0];
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of Crew objects of all crew members that have the requested
     *         string in their name
     */
    @Override
    public Crew[] findCrew(String crew) {
        // TODO Build this function
        return new Crew[0];
    }

    /**
     * Finds all stars. A star is the following person: a star actor is
     * a cast member who have appeared in 3 or more movies, where each movie
     * has an average score of 4 or higher.
     * 
     * @param ratings The ratings for all films
     * @return An array of Cast IDs that are stars
     */
    @Override
    public int[] findStarCastID(Ratings ratings) {
        // TODO Build this function
        return new int[0];
    }

    /**
     * Finds all superstars. A superstar is the following person: a star actor is
     * also a superstar if they have played in at least two movies with another star
     * actor.
     * 
     * @param ratings The ratings for all films
     * @return An array of Cast IDs that are super stars
     */
    @Override
    public int[] findSuperStarCastID(Ratings ratings) {
        // TODO Build this function
        return new int[0];
    }

    /**
     * Finds the distance between cast members A and B, by looking at common cast
     * members in films. For example, if A and B were in different movies, but both
     * started in a movie with cast member C, then there distance would be 1.
     * 
     * @param castIDA The starting cast member
     * @param castIDB The finishing cast member
     * @return If there is no connection, then return an empty array. If castIDA ==
     *         castIDB, then return an array containing ONLY castIDB. If there is a
     *         path from castIDA to castIDB, then all cast IDs in the path should be
     *         listed in order in the returned array, including castIDB. In the
     *         above example, the array should return {castIDC, castIDB}.
     */
    @Override
    public int[] findDistance(int castIDA, int castIDB) {
        // TODO Build this function
        return new int[0];
    }

}
