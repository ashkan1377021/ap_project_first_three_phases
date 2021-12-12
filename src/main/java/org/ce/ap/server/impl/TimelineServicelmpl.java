package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class has Methods that a user can observe its favorite users Tweets with their likes and reTweets
 * @author ashkan_mogharab
 * @version version 1 of TimelineService implementation
 */
public class TimelineServicelmpl implements TimelineService {
    //the user which wants to observe its favorite users Tweets with their likes and reTweets
    private User user;
    //retweets of each favorite user
    private ArrayList<Tweet> retweets;
    //tweets of each favorite user
    private ArrayList<Tweet>tweets ;
    // select of the person
    private int select;

    /**
     * creates a new timeline service
     * @param user the user which wants to observe its favorite users Tweets with their likes and reTweets
     */
    public TimelineServicelmpl(User user) {
        this.user = user;
        act();
    }
    /**
     * this method handles the works that should be done in observer service
     */
    private void act() {
        while (true) {
            System.out.println("Welcome to Timeline service" + "\n" + "1:Tweets of favorite users" + '\n' + "2:reTweets of favorite users" + '\n' + "3:back");
            while (true) {
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                if (select == 1 || select == 2 || select == 3)
                    break;
            }
            if (select == 1)
                showTweets();
            else if (select == 2)
                show_reTweets();
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
            if(select == 1){
                tweets = new ArrayList<>();
                for(User favorite : user.getFavoriteUsers()) {
                    for (int i = 0; i < favorite.getTweets().size(); i++) {
                        if (favorite.getTweets().get(i).getSender().equals(favorite)) {
                            tweets.add(favorite.getTweets().get(i));
                        }
                    }
                    System.out.println(favorite.getUsername());
                    tweets .sort(new Sort_by_sendTime());
                    for(int i = 0 ;i < tweets.size();i++)
                        System.out.println("Tweet " + (i+1) + " : text: " +tweets.get(i).getText() + "  sendTime: " +tweets.get(i).getSendDate() + "  " + tweets.get(i).getLikes().size() + " likes" + tweets.get(i).getRetweets().size() + " retweets");
                }
            }
            else
                break;
        }
    }
    /**
     * this method shows reTweets of each favorite user
     */
    private void show_reTweets(){
        while(true) {
            System.out.println("1:show reTweets" + '\n' + "2:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if(select == 1){
                retweets =  new ArrayList<>();
                for(User favorite : user.getFavoriteUsers()) {
                    for (int i = 0; i < favorite.getTweets().size(); i++) {
                        if (!(favorite.getTweets().get(i).getSender().equals(favorite))) {
                            retweets.add(favorite.getTweets().get(i));
                        }
                    }
                    System.out.println(favorite.getUsername());
                    retweets .sort(new Sort_by_sendTime());
                    for(int i = 0 ;i < retweets.size();i++)
                        System.out.println("reTweet " + (i+1) + " : sender: " +retweets.get(i).getSender() +  "  text : " + "  sendTime: " +retweets.get(i).getSendDate() + "  " + retweets.get(i).getLikes().size() + " likes" + "  " + retweets.get(i).getRetweets().size() + " retweets");
                }

            }
            else
                break;




        }

    }
}
