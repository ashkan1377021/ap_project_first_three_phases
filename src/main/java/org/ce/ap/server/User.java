package main.java.org.ce.ap.server;
import java.time.*;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
// Java program to calculate SHA hash value
/**
 * this class holds details of an user in ServerSide
 * @author ashkan_mogharab
 */
public class User {
    //an object of usefulMethods
    private usefulMethods usefulmethods = new usefulMethods();
    //firstname of the user
    private String firstname;
    //lastname of the user
    private String lastname;
    //username of the user that should be different from others
    private String username ;
    //password of the user that it should be in hash form
    private String password;
    //birthDate of the user
    private LocalDate birthDate;
    //registeryDate of the user
    private LocalDate registeryDate;
    //bio of the user that its  maximum length is 256
    private String bio ;
    //tweets of the user
    private ArrayList<Tweet> tweets;
    //followers of the user
    private ArrayList<User> followers;
    //favorite users of the user
    private ArrayList<User> favoriteUsers;
    //liked tweets
    private ArrayList<Tweet>liked;
    /**
     * creates a new user
     * @param firstname firstname of the user
     * @param lastname  lastname of the user
     * @param username username of the user
     * @param password password of the user
     * @param birthDate birthDate of the user
     * @param registeryDate registeryDate of the user
     * @param bio bio of the user
     */
    public User(String firstname, String lastname, String username, String password, LocalDate birthDate, LocalDate registeryDate, String bio) {
        try {
            this.firstname = firstname;
            this.lastname = lastname;
            this.username = username;
            this.password =usefulmethods.toHexString(usefulmethods.getSHA(password));
            this.birthDate = birthDate;
            this.registeryDate = registeryDate;
            this.bio = bio;
            tweets = new ArrayList<>();
            followers = new ArrayList<>();
            favoriteUsers = new ArrayList<>();
            liked = new ArrayList<>();
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }
    /**
     * getter
     * @return firstname of the user
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     getter
     * @return lastname of the user
     */
    public String getLastname() {
        return lastname;
    }
    /**
     getter
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }
    /**
     getter
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }
    /**
     getter
     * @return birthDate of the user
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }
    /**
     getter
     * @return registeryDate of the user
     */
    public LocalDate getRegisteryDate() {
        return registeryDate;
    }
    /**
     getter
     * @return bio of the user
     */
    public String getBio() {
        return bio;
    }
    /**
     * getter
     * @return tweets of the user
     */
    public ArrayList<Tweet> getTweets() {
        return tweets;
    }
    /**
     * getter
     * @return followers of the user
     */
    public ArrayList<User> getFollowers() {
        return followers;
    }
    /**
     * getter
     * @return favorite users of the user
     */
    public ArrayList<User> getFavoriteUsers() {
        return favoriteUsers;
    }
    /**
     * getter
     * @return liked tweets
     */
    public ArrayList<Tweet> getLiked() {
        return liked;
    }

    @Override
    public String toString() {
        return "User{" +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", registeryDate=" + registeryDate +
                ", bio='" + bio + '\'' +
                ", tweets=" + tweets.size() +
                ", followers=" + followers.size()+'\''+
                ", favoriteUsers=" + favoriteUsers.size() +'\''+
                '}';
    }
}

