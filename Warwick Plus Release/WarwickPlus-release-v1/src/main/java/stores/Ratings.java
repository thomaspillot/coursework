package stores;

import java.util.Calendar;
import java.util.Map;

import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     */
    public Ratings() {
        //Since we need to be able to look up ratings by 3 different fields (MovieID, userID, timestamp), three hashmaps will be created (with each key set to one of the lookup fields)
        //List needed so they can be mutated
        HashMap<Integer, NewArrayList<Object>> ratingsByMovieId = new HashMap<>(); //each rating is of form: [userID, rating, timestamp]
        HashMap<Integer, NewArrayList<Object>> ratignsByUserId; = new HashMap<>(); //each rating is of form: [movieID, rating, timestamp]
        HashMap<Calender, NewArrayList<Object>> ratingsByTimestamp = new HashMap<>(); //each rating is of form: [movieID, userID, rating]
        int numOfRatings = 0; //tracks how many ratings have been added in total

    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userID, int movieID, float rating, Calendar timestamp) {
        // TODO Build this function
        try {
            NewArrayList<Object> list1 = new NewArrayList<>();
            list1.add(userID);
            list1.add(rating);
            list1.add(timestamp);
            ratingsByMovieId.add(movieID, list1);

            NewArrayList<Object> list2 = new NewArrayList<>();
            list1.add(movieID);
            list1.add(rating);
            list1.add(timestamp);
            ratingsByUserId.add(userID, list2);

            NewArrayList<Object> list3 = new NewArrayList<>();
            list1.add(movieID);
            list1.add(userID);
            list1.add(rating);
            ratingsByTimeStamp.add(timestamp, list3);

            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userID, int movieID) {
        // TODO Build this function
        // remove rating directly using userID and movieId
        //store timestamp of removed rating
        //use timestamp to remove rating mapped to by timestamp
        
        ratingsByMovieId.removeRating(movieID, userID);
        timestamp =ratingsByUserId.removeRating(userID, movieID);
        ratingsByTimeStamp.removeRatingByTime(movieID, userID, timestamp);

        return false;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userID, int movieID, float rating, Calendar timestamp) {
        // TODO Build this function
        return false;
    }

    /**
     * Find all ratings between a given start date and end date. If a rating falls
     * exactly on a given start date or a given end date, then this should not be
     * included
     * 
     * @param start The start time for the range
     * @param end   The end time for the range
     * @return An array of ratings between start and end. If there are no ratings,
     *         then return an empty array
     */
    @Override
    public float[] getRatingsBetween(Calendar start, Calendar end) {
        // TODO Build this function
        NewArrayList<float> ratings = new NewArrayList<>();
        //increment the start date by 1 day until end date is reached
        while (!start.add(start, 1).equals(end)) {
            //get the linked list of ratings for a certain key 
            ratingsByTimeStamp.get(start);
            //iterate through list, adding each rating to array
        }
        return new float[0];
    }

    /**
     * Find all ratings for a given film, between a given start date and end date.
     * If a rating falls exactly on a given start date or a given end date, then
     * this should not be included
     * 
     * @param movieID The movie ID
     * @param start   The start time for the range
     * @param end     The end time for the range
     * @return An array of ratings between start and end for a given film. If there
     *         are no ratings, then return an empty array
     */
    @Override
    public float[] getMovieRatingsBetween(int movieID, Calendar start, Calendar end) {
        // TODO Build this function
        
        //Maybe sort ratings by timestamp before doing this (in a tree?)

        return new float[0];
    }

    /**
     * Find all ratings for a given user, between a given start date and end date.
     * If a rating falls exactly on a given start date or a given end date, then
     * this should not be included
     * 
     * @param userID The user ID
     * @param start  The start time for the range
     * @param end    The end time for the range
     * @return An array of ratings between start and end for a given user. If there
     *         are no ratings, then return an empty array
     */
    @Override
    public float[] getUserRatingsBetween(int userID, Calendar start, Calendar end) {
        // TODO Build this function
    
        //same as above 
    
    
        return new float[0];
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieID) {
        // TODO Build this function

        head = ratingsByMovieId.get(movieID);
        while (head.next != null) {

            head = head.next;
        }

        return new float[0];
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userID) {
        // TODO Build this function

        head = ratingsByMovieId.get(movieID);
        while (head.next != null) {
            
            head = head.next;
        }

        return new float[0];
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. If the film cannot be
     *         found, or there are no rating, return 0
     */
    @Override
    public float getMovieAverageRatings(int movieID) {
        // TODO Build this function
        
        //same as getMovieRatings() but divide by total number of reviews at end

        ratingCount = 0;     
        tatingTotal = 0;  
        head = ratingsByMovieId.get(movieID);
        while (head.next != null) {
            ratingCount += 1;
            //ratings are stored at index 1
            ratingTotal += head.getValue()[1];
            head = head.next;
        }

        if (ratingCount == 0) {
            return 0;
        }
        return ratingTotal/ratingCount;
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found, or there are no rating, return 0
     */
    @Override
    public float getUserAverageRatings(int userID) {
        // TODO Build this function
        
        // same logic as getMovieAverageRatings above 

        ratingCount = 0;     
        tatingTotal = 0;  
        head = ratingsByUserId.get(userID);
        while (head.next != null) {
            ratingCount += 1;
            //ratings are stored at index 1
            ratingTotal += head.getValue()[1];
            head = head.next;
        }

        if (ratingCount == 0) {
            return 0;
        }
        return ratingTotal/ratingCount;
    }


        return 0;
    }

    /**
     * Gets the top N films with the most ratings, in order from most to least
     * 
     * @param num The number of films that should be returned
     * @return A sorted array of film IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num films in the store,
     *         then the array should be the same length as the number of films
     */
    @Override
    public int[] getTopMovies(int num) {
        // TODO Build this function

        // As ratings are added topMoviesList is updated, so we can just return the values of that
        
        //topMoviesList is a list, so need to store values into an array before returning
        int[] topRatings = new int[10];
        for (int i=0; i<10; i++) {
            topRatings[i] = topMoviesList[i];
        }
        return topRatings;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        // TODO Build this function
        
        // As ratings are added topUsersList is updated, so we can just return the values of that
        
        //topUsersList is a list, so need to store values into an array before returning
        int[] topUsers = new int[10];
        for (int i=0; i<10; i++) {
            topUsers[i] = topUsersList[i];
        }
        return topUsers;

    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return numOfRatings;
    }

}
