package main.java.org.ce.ap.client;

public interface ClientObserverService {
    /**
     * this method handles the works that should be done in ClientObserver Service
     */
    void act();

    /**
     * this method handles process of following/unfollowing a user
     */
    void follow_unfollow();

    /**
     * this method observes tweets of favorite users of the user
     */
    void observe();
}
