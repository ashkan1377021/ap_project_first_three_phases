package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class has Methods that a user can observe its favorite users Tweets with their likes and reTweets
 * @author ashkan_mogharab
 * @version version 1 of TimelineService implementation
 */
public class TimelineService_impl implements TimelineService {
    //a socket for connecting  to user
    private final Socket connectionSocket;
    // an output stream
    private OutputStream out;
    // an input stream
    private InputStream in;
    //an object of usefulMethods
    private final usefulMethods usefulmethods = new usefulMethods();
    //the user which wants to observe its favorite users Tweets with their likes and reTweets
    private final User user;
    // select of the person
    private String select;

    /**
     * creates a new timeline service
     * @param user the user which wants to observe its favorite users Tweets with their likes and reTweets
     */
    public TimelineService_impl(User user , Socket connectionSocket) {
        this.user = user;
        this.connectionSocket = connectionSocket;
        act();
    }
    /**
     * this method handles the works that should be done in observer service
     */
    private void act() {
        try{
            out = connectionSocket.getOutputStream();
            in = connectionSocket.getInputStream();
        while (true) {
            String msg;
            usefulmethods.send_message(out,"Welcome to Timeline service" + "\n" + "1:Tweets and reTweets of favorite users" + '\n' + "2:back");
            System.out.println("welcoming message of Timeline service sent to " + user.getUsername());
            System.out.println("receive from " +user.getUsername() + " : " + (msg = usefulmethods.read_message(in)));
            if (msg.equals("1"))
                showTweets();
            else {
                System.out.println(user.getUsername() + " Quited from Timeline service");
                break;
            }
        }
    }
        catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * this method shows Tweets,reTweets & likes of each favorite user
     */
    private void showTweets() throws InterruptedException {
        while(true) {
            usefulmethods.send_message(out,"1:show Tweets" + '\n' + "2:back");
            System.out.println("process of observe Timeline by " + user.getUsername()+"  started");
            select = usefulmethods.read_message(in);
            if(select.equals("1")) {
                int count = user.getFavoriteUsers().size();
                for(User favorite : user.getFavoriteUsers())
                    count+= favorite.getTweets().size() + favorite.getLiked().size();
                usefulmethods.send_message(out,"" + count);
                Thread.sleep(1);
                for (User favorite : user.getFavoriteUsers()) {
                    usefulmethods.send_message(out,favorite.getUsername());
                    Thread.sleep(1);
                    ArrayList<Tweet> tweets = favorite.getTweets();
                    int count1 = 1;
                    int count2 = 1;
                    tweets.sort(new Sort_by_sendTime());
                    for (Tweet tweet : tweets) {
                        if (tweet.getSender().equals(favorite))
                            usefulmethods.send_message(out, "Tweet " + (count1++) + " : text: " + tweet.getText() + "  sendTime: " + tweet.getSendDate() + "  " + tweet.getLikes().size() + " likes  " + tweet.getRetweets().size() + " retweets");
                        else
                            usefulmethods.send_message(out,"reTweet " + (count2++) + ": Sender: " + tweet.getSender().getUsername() + " : text: " + tweet.getText() + "  sendTime: " + tweet.getSendDate() + "  " + tweet.getLikes().size() + " likes  " + tweet.getRetweets().size() + " retweets");
                        Thread.sleep(1);
                    }
                        ArrayList<Tweet> liked_tweets = favorite.getLiked();
                    liked_tweets.sort(new Sort_by_sendTime());
                    for (Tweet liked_tweet : liked_tweets) {
                        usefulmethods.send_message(out,"liked by " + favorite.getUsername() + '\n' + "Sender: " + liked_tweet.getSender().getUsername() + " text: " + liked_tweet.getText() + " SendTime: " + liked_tweet.getSendDate() + " " + liked_tweet.getLikes().size() + " likes " + liked_tweet.getRetweets().size() + " retweets");
                        Thread.sleep(1);
                    }
                        System.out.println('\n');

                }
                System.out.println("process of observe Timeline by " + user.getUsername()+"ended");
            }
            else {
                System.out.println(user.getUsername()+ " backed to Timeline service menu");
                break;
            }
        }
    }

}
