import java.time.*;
/**
 * this class holds details of a tweet
 * @author ashkan_mogharab
 */
public class Tweet {
    //sender of the tweet
    User sender;
    //number of likes of the tweet
    int likes;
    //text of the tweet that its maximum length is 256
    String text;
    // send date of the tweet
    LocalDate sendDate;

    /**
     * creates a new tweet
     * @param sender sender of the tweet
     * @param text text of the tweet
     * @param sendDate send date of the tweet
     */
    public Tweet(User sender, String text, LocalDate sendDate) {
        this.sender = sender;
        this.likes = 0;
        this.text = text;
        this.sendDate = sendDate;
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
     * @return number of likes of the tweet
     */
    public int getLikes() {
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
     * @return send date of the tweet
     */
    public LocalDate getSendDate() {
        return sendDate;
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
     * @param likes number of likes of the tweet
     */
    public void setLikes(int likes) {
        this.likes = likes;
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
     * @param sendDate send date of the tweet
     */
    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "sender=" + sender.getUsername() +
                ", likes=" + likes +
                ", text='" + text + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
