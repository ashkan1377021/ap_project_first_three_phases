package main.java.org.ce.ap.client;

public interface ClientTimelineService {
    /**
     * this method handles the works that should be done inClientTimeline Service
     */
    void act();

    /**
     * this method shows Tweets, reTweets and likes of each favorite user
     */
    void showTweets();

}
