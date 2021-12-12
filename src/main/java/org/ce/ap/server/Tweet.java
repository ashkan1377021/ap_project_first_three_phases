package main.java.org.ce.ap.server;
import java.time.*;
import java.util.ArrayList;
/**
 * this class holds details of a tweet
 * @author ashkan_mogharab
 */
public class Tweet {
    //sender of the tweet
    private User sender;
    //users who liked  the tweet
    private ArrayList<User>likes;
    // users who retweeted the tweet
    private ArrayList<User>retweets;
    //text of the tweet that its maximum length is 256
    private String text;
    // send time of the tweet
    private LocalDateTime sendTime;
    /**
     * creates a new tweet
     * @param sender sender of the tweet
     * @param text text of the tweet
     * @param sendTime send date of the tweet
     */
    public Tweet(User sender, String text, LocalDateTime sendTime) {
        this.sender = sender;
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
        this.text = text;
        this.sendTime = sendTime;
    }

    /**
     * getter
     * @return sender of the tweet
     */
    public User getSender() {
        return sender;
    }
    /**
     * getter
     * @return users who liked  the tweet
     */
    public ArrayList<User> getLikes() {
        return likes;
    }
    /**
     * getter
     * @return usernames of users who retweeted the tweet
     */
    public ArrayList<User> getRetweets() {
        return retweets;
    }
    /**
     * getter
     * @return text of the tweet
     */
    public String getText() {
        return text;
    }
    /**
     * getter
     * @return send time of the tweet
     */
    public LocalDateTime getSendDate() {
        return sendTime;
    }

    /**
     * setter
     * @param sender sender of the tweet
     */
    public void setSender(User sender) {
        this.sender = sender;
    }
    /**
     * setter
     * @param text text of the tweet
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * setter
     * @param sendTime send date of the tweet
     */
    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        ArrayList<String>liked_usernames = new ArrayList<>();
        ArrayList<String>retweeted_usernames = new ArrayList<>();
        for(int i = 0 ; i<likes.size();i++)
            liked_usernames.add(likes.get(i).getUsername());
        for(int i = 0 ; i<retweets.size();i++)
            retweeted_usernames.add(retweets.get(i).getUsername());
        return "text=" + text + ",   " +
                "likes=" + liked_usernames.size()+",  "+
                "reTweets=" + retweeted_usernames.size()+",    "+
                "sendTime=" + sendTime;
    }
}
