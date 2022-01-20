package main.java.org.ce.ap.client;

public interface ClientTweetingService {
    /**
     * this method handles the works that should be done in ClientTweeting Service
     */
    void act();

    /**
     * this method adds a tweet to user's tweets
     */
    void add();

    /**
     * this method removes a tweet or retweet from user's tweets or retweets
     */
    void remove();

    /**
     * this method likes a tweet from a user's tweets
     */
    void like();
}
