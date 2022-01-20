package main.java.org.ce.ap.server;

public interface TimelineService {
    /**
     * this method handles the works that should be done in TimeLine service
     */
    void act();

    /**
     * this method shows Tweets,reTweets and likes of each favorite user to it
     */
    void showTweets() throws InterruptedException;
}
