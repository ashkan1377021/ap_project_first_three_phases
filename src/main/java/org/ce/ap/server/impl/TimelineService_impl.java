package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * this class has Methods that a user can observe its favorite users Tweets with their likes and reTweets
 *
 * @author ashkan_mogharab
 * @version version 1 of TimelineService implementation
 */
public class TimelineService_impl implements TimelineService {
    //a socket for connecting  to user
    private final Socket connectionSocket;
    //an object of usefulMethods
    private final usefulMethods usefulmethods = new usefulMethods();
    //users of ServerSide
    private final ArrayList<User> users;
    //index of the user who wants to observe its favorite users Tweets with their likes and reTweets
    private final int index;
    // an object of File_utility
    File_utility file_utility = new File_utility();
    // an output stream
    private OutputStream out;
    // an input stream
    private InputStream in;
    // select of the person
    private String select;

    /**
     * creates a new timeline service
     *
     * @param users            users of ServerSide
     * @param index            index of the user who wants to observe its favorite users Tweets with their likes and reTweets
     * @param connectionSocket a socket for connecting  to user
     */
    public TimelineService_impl(ArrayList<User> users, int index, Socket connectionSocket) {
        this.users = users;
        this.index = index;
        this.connectionSocket = connectionSocket;
        act();
    }

    /**
     * this method handles the works that should be done in TimeLine service
     */
    public void act() {
        try {
            out = connectionSocket.getOutputStream();
            in = connectionSocket.getInputStream();
            while (true) {
                String msg;
                usefulmethods.send_message(out, "Welcome to Timeline service" + "\n" + "1:Tweets and reTweets of favorite users" + '\n' + "2:back");
                System.out.println("welcoming message of Timeline service sent to " + users.get(index).getUsername());
                file_utility.record_events("welcoming message of Timeline service sent to " + users.get(index).getUsername());
                System.out.println("receive from " + users.get(index).getUsername() + " : " + (msg = usefulmethods.read_message(in)));
                file_utility.record_events("receive from " + users.get(index).getUsername() + " : " + msg);
                if (msg.equals("1"))
                    showTweets();
                else {
                    System.out.println(users.get(index).getUsername() + " Quited from Timeline service");
                    file_utility.record_events(users.get(index).getUsername() + " Quited from Timeline service");
                    break;
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * this method shows Tweets,reTweets and likes of each favorite user to it
     */
    public void showTweets() throws InterruptedException {
        while (true) {
            usefulmethods.send_message(out, "1:show Tweets" + '\n' + "2:back");
            System.out.println("process of observe Timeline by " + users.get(index).getUsername() + "  started");
            file_utility.record_events("process of observe Timeline by " + users.get(index).getUsername() + "  started");
            select = usefulmethods.read_message(in);
            if (select.equals("1")) {
                int count = users.get(index).getFavoriteUsers().size() * 2;
                for (User favorite : users.get(index).getFavoriteUsers())
                    count += favorite.getTweets().size() + favorite.getLiked().size();
                usefulmethods.send_message(out, "" + count);
                Thread.sleep(1);
                for (User favorite : users.get(index).getFavoriteUsers()) {
                    usefulmethods.send_message(out, favorite.getUsername() + "  followers :" + favorite.getFollowers().size() + "  following :" + favorite.getFavoriteUsers().size());
                    Thread.sleep(1);
                    ArrayList<Tweet> tweets = favorite.getTweets();
                    int count1 = 1;
                    int count2 = 1;
                    file_utility.make_changes(users);
                    for (Tweet tweet : tweets) {
                        if (tweet.getSender().equals(favorite))
                            usefulmethods.send_message(out, "Tweet " + (count1++) + " : text: " + tweet.getText() + "  sendTime: " + tweet.getSendDate() + "  " + tweet.getLikes().size() + " likes  " + tweet.getRetweets().size() + " retweets");
                        else
                            usefulmethods.send_message(out, "reTweet " + (count2++) + ": Sender: " + tweet.getSender().getUsername() + " : text: " + tweet.getText() + "  sendTime: " + tweet.getSendDate() + "  " + tweet.getLikes().size() + " likes  " + tweet.getRetweets().size() + " retweets");
                        Thread.sleep(1);
                    }
                    ArrayList<Tweet> liked_tweets = favorite.getLiked();
                    file_utility.make_changes(users);
                    for (Tweet liked_tweet : liked_tweets) {
                        usefulmethods.send_message(out, "liked by " + favorite.getUsername() + '\n' + "Sender: " + liked_tweet.getSender().getUsername() + " text: " + liked_tweet.getText() + " SendTime: " + liked_tweet.getSendDate() + " " + liked_tweet.getLikes().size() + " likes " + liked_tweet.getRetweets().size() + " retweets");
                        Thread.sleep(1);
                    }
                    usefulmethods.send_message(out, "" + '\n');
                    Thread.sleep(1);

                }
                System.out.println("process of observe Timeline by " + users.get(index).getUsername() + " ended");
                file_utility.record_events("process of observe Timeline by " + users.get(index).getUsername() + " ended");
            } else {
                System.out.println(users.get(index).getUsername() + " backed to Timeline service menu");
                file_utility.record_events(users.get(index).getUsername() + " backed to Timeline service menu");
                break;
            }
        }
    }

}
