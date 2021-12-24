package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class has Methods that a user can observe its favorite users Tweets with their likes and reTweets
 * @author ashkan_mogharab
 * @version version 1 of TimelineService implementation
 */
public class TimelineService_impl implements TimelineService {
    //the user which wants to observe its favorite users Tweets with their likes and reTweets
    private final User user;
    // select of the person
    private int select;

    /**
     * creates a new timeline service
     * @param user the user which wants to observe its favorite users Tweets with their likes and reTweets
     */
    public TimelineService_impl(User user) {
        this.user = user;
        act();
    }
    /**
     * this method handles the works that should be done in observer service
     */
    private void act() {
        while (true) {
            System.out.println("Welcome to Timeline service" + "\n" + "1:Tweets and reTweets of favorite users" + '\n' + "2:back");
            while (true) {
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if (select == 1)
                showTweets();
            else
                break;
        }
    }
    /**
     * this method shows Tweets of each favorite user
     */
    private void showTweets(){
        while(true) {
            System.out.println("1:show Tweets" + '\n' + "2:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if(select == 1) {
                for (User favorite : user.getFavoriteUsers()) {
                    System.out.println(favorite.getUsername());
                    ArrayList<Tweet> tweets = favorite.getTweets();
                    int count1 = 1;
                    int count2 = 1;
                    tweets.sort(new Sort_by_sendTime());
                    for (int i = 0; i < tweets.size(); i++) {
                        if (tweets.get(i).getSender().equals(favorite))
                            System.out.println("Tweet " + (count1++) + " : text: " + tweets.get(i).getText() + "  sendTime: " + tweets.get(i).getSendDate() + "  " + tweets.get(i).getLikes().size() + " likes  " + tweets.get(i).getRetweets().size() + " retweets");
                        else
                            System.out.println("reTweet " + (count2++) + ": Sender: " + tweets.get(i).getSender().getUsername() + " text: " + tweets.get(i).getText() + "  sendTime: " + tweets.get(i).getSendDate() + "  " + tweets.get(i).getLikes().size() + " likes  " + tweets.get(i).getRetweets().size() + " retweets");

                        ArrayList<User> special_likers = new ArrayList<>();
                        for (User liker : tweets.get(i).getLikes())
                            if (user.getFavoriteUsers().contains(liker))
                                special_likers.add(liker);
                        ArrayList<String> special_likers_username = new ArrayList<>();
                        for (int j = 0; j < special_likers.size(); j++)
                            special_likers_username.add(special_likers.get(j).getUsername());
                        System.out.println("liked by " + special_likers_username);
                    }
                }
            }
            else
                break;
        }
    }

}
