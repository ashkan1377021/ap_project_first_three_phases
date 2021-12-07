import java.time.*;
import java.util.ArrayList;

/**
 * this class holds details of a tweet
 * @author ashkan_mogharab
 */
public class Tweet {
    //sender of the tweet
    User sender;
    //users who liked  the tweet
    ArrayList<User>likes;
    //text of the tweet that its maximum length is 256
    String text;
    // send time of the tweet
    LocalDateTime sendTime;
    /**
     * creates a new tweet
     * @param sender sender of the tweet
     * @param text text of the tweet
     * @param sendTime send date of the tweet
     */
    public Tweet(User sender, String text, LocalDateTime sendTime) {
        this.sender = sender;
        this.likes = new ArrayList<>();
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
        return "Tweet{" +
                "sender=" + sender.getUsername() +
                ", likes=" + likes.size() +
                ", text='" + text + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
