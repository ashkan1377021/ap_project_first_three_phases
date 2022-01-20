package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * this class has Methods that can add ,remove ,like and retweet a tweet
 *
 * @author ashkan_mogharab
 * @version version 1 of TweetingService implementation
 */
public class TweetingService_impl implements TweetingService {
    //a socket for connecting  to user
    private final Socket connectionSocket;
    //an object of usefulMethods
    private final usefulMethods usefulmethods = new usefulMethods();
    //users of ServerSide
    private final ArrayList<User> users;
    //index of the user who wants to use from tweeting service
    private final int index;
    // an object of File_utility
    File_utility file_utility = new File_utility();
    // an input stream
    private InputStream in;
    // an output stream
    private OutputStream out;
    // select of the person
    private String select;

    /**
     * creates a new tweeting service
     *
     * @param users            users of ServerSide
     * @param index            index of the user who wants to use tweeting service
     * @param connectionSocket a socket for connecting  to user
     */
    public TweetingService_impl(ArrayList<User> users, int index, Socket connectionSocket) {
        this.users = users;
        this.index = index;
        this.connectionSocket = connectionSocket;
        act();
    }

    /**
     * this method handles the works that should be done in tweeting service
     */
    public void act() {
        try {
            out = connectionSocket.getOutputStream();
            in = connectionSocket.getInputStream();
            label:
            while (true) {
                String msg;
                usefulmethods.send_message(out, "Welcome to tweeting service" + "\n" + "1:add" + '\n' + "2:remove" + '\n' + "3:like" + '\n' + "4:back");
                System.out.println("welcoming message of Tweeting service sent to " + users.get(index).getUsername());
                file_utility.record_events("welcoming message of Tweeting service sent to " + users.get(index).getUsername());
                System.out.println("receive from " + users.get(index).getUsername() + " : " + (msg = usefulmethods.read_message(in)));
                file_utility.record_events("receive from " + users.get(index).getUsername() + " : " + msg);
                switch (msg) {
                    case "1":
                        add();
                        break;
                    case "2":
                        remove();
                        break;
                    case "3":
                        like();
                        break;
                    default:
                        System.out.println(users.get(index).getUsername() + " Quited from Tweeting service");
                        file_utility.record_events(users.get(index).getUsername() + " Quited from Tweeting service");
                        break label;
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * this method adds a tweet to user's tweets
     */
    public void add() throws InterruptedException {
        while (true) {
            usefulmethods.send_message(out, "1:tweet" + '\n' + "2:retweet" + '\n' + "3:back");
            select = usefulmethods.read_message(in);
            if (select.equals("1")) {
                System.out.println("process of add a tweet by " + users.get(index).getUsername() + "  started");
                file_utility.record_events("process of add a tweet by " + users.get(index).getUsername() + "  started");
                int flag = 0;
                String text;
                while (true) {
                    usefulmethods.send_message(out, "text:");
                    text = usefulmethods.read_message(in);
                    if (text.length() <= 256) {
                        System.out.println("the Tweet (" + text + ") created by " + users.get(index).getUsername());
                        file_utility.record_events("the Tweet (" + text + ") created by " + users.get(index).getUsername());
                        usefulmethods.send_message(out, "true");
                        usefulmethods.send_message(out, "the Tweet created and added to your Tweets");
                        Thread.sleep(1);
                        flag = 1;
                        break;
                    }
                    usefulmethods.send_message(out, "false");
                    usefulmethods.send_message(out, ("This String is very long .maximum valid length is 256" + '\n' + "1:continue attempting" + "\n" + "2: back "));
                    System.out.println("the length of the text that " + users.get(index).getUsername() + " wanted to tweet was more than 256.we requested from it to choose continue attempting or back");
                    file_utility.record_events("the length of the text that " + users.get(index).getUsername() + " wanted to tweet was more than 256.we requested from it to choose continue attempting or back");
                    if (usefulmethods.read_message(in).equals("2")) {
                        System.out.println(users.get(index).getUsername() + " chose to back to add method of tweeting service");
                        file_utility.record_events(users.get(index).getUsername() + " chose to back to add method of tweeting service");
                        break;
                    } else {
                        System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                        file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                    }

                }
                if (flag == 1) {
                    Tweet new_tweet = new Tweet(users.get(index), text, java.time.LocalDateTime.now());
                    users.get(index).getTweets().add(new_tweet);
                    users.get(index).getTweets().sort(new Sort_by_sendTime());
                    file_utility.make_changes(users);
                }
            } else if (select.equals("2")) {
                System.out.println("process of retweet a tweet by " + users.get(index).getUsername() + "  started");
                file_utility.record_events("process of retweet a tweet by " + users.get(index).getUsername() + "  started");
                int ix1;
                int ix2;
                while (true) {
                    usefulmethods.send_message(out, ("from Tweets of which user you want to reTweet?(user number)"));
                    ix1 = Integer.parseInt(usefulmethods.read_message(in)) - 1;
                    System.out.println(users.get(index).getUsername() + " wants to retweet from user " + (ix1 + 1));
                    file_utility.record_events(users.get(index).getUsername() + " wants to retweet from user " + (ix1 + 1));
                    if (ix1 < users.size() && ix1 != index) {
                        usefulmethods.send_message(out, "true");
                        while (true) {
                            usefulmethods.send_message(out, "which Tweet you want to reTweet?(Tweet number)");
                            ix2 = Integer.parseInt(usefulmethods.read_message(in)) - 1;
                            System.out.println(users.get(index).getUsername() + " wants to retweet " + (ix2 + 1) + "th tweet of user " + (ix1 + 1));
                            file_utility.record_events(users.get(index).getUsername() + " wants to retweet " + (ix2 + 1) + "th tweet of user " + (ix1 + 1));
                            if (is_valid_index(ix1, ix2)) {
                                usefulmethods.send_message(out, "true");
                                ArrayList<String> retweets = new ArrayList<>();
                                for (User user : users.get(ix1).getTweets().get(ix2).getRetweets())
                                    retweets.add(user.getUsername());
                                if (retweets.contains(users.get(index).getUsername())) {
                                    System.out.println(users.get(index).getUsername() + "have already retweeted this tweet");
                                    file_utility.record_events(users.get(index).getUsername() + "have already retweeted this tweet");
                                    usefulmethods.send_message(out, "you have already retweeted this tweet");
                                } else {
                                    users.get(ix1).getTweets().get(ix2).getRetweets().add(users.get(index));
                                    users.get(index).getTweets().add(users.get(ix1).getTweets().get(ix2));
                                    users.get(index).getTweets().sort(new Sort_by_sendTime());
                                    file_utility.make_changes(users);
                                    System.out.println("reTweeting process by " + users.get(index).getUsername() + " successfully done");
                                    file_utility.record_events("reTweeting process by " + users.get(index).getUsername() + " successfully done");
                                    usefulmethods.send_message(out, "retweeting successfully done");
                                }
                                Thread.sleep(1);
                                break;
                            } else {
                                usefulmethods.send_message(out, "false");
                                System.out.println("The number that " + users.get(index).getUsername() + " entered is bigger than number of Tweets that user " + users.get(ix1).getUsername() + " has!we requested from it to choose continue attempting or back");
                                file_utility.record_events("The number that " + users.get(index).getUsername() + " entered is bigger than number of Tweets that user " + users.get(ix1).getUsername() + " has!we requested from it to choose continue attempting or back");
                                usefulmethods.send_message(out, "The number that you entered is bigger than number of Tweets that " + users.get(ix1).getUsername() + " has!" + '\n' + "1:continue attempting" + "\n" + "2: back ");
                                if (usefulmethods.read_message(in).equals("2")) {
                                    System.out.println(users.get(index).getUsername() + " chose to back to add method of tweeting service");
                                    file_utility.record_events(users.get(index).getUsername() + " chose to back to add method of tweeting service");
                                    break;
                                } else {
                                    System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                                    file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                                }
                            }

                        }
                        break;

                    } else {
                        usefulmethods.send_message(out, "false");
                        if (ix1 != index) {
                            usefulmethods.send_message(out, ("This user does not exist." + '\n' + "1:continue attempting" + "\n" + "2: back "));
                            System.out.println("the number that " + users.get(index).getUsername() + "  entered was bigger than number of users.we requested from it to choose continue attempting or back");
                            file_utility.record_events("the number that " + users.get(index).getUsername() + "  entered was bigger than number of users.we requested from it to choose continue attempting or back");
                        } else {
                            usefulmethods.send_message(out, ("you can not retweet a tweet from yourself." + '\n' + "1:continue attempting" + "\n" + "2: back "));
                            System.out.println(users.get(index).getUsername() + " wanted to retweet a tweet from itself.we requested from it to choose continue attempting or back");
                            file_utility.record_events(users.get(index).getUsername() + " wanted to retweet a tweet from itself.we requested from it to choose continue attempting or back");
                        }
                        if (usefulmethods.read_message(in).equals("2")) {
                            System.out.println(users.get(index).getUsername() + " chose to back to add method of tweeting service");
                            file_utility.record_events(users.get(index).getUsername() + " chose to back to add method of tweeting service");
                            break;
                        } else {
                            System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                            file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                        }
                    }
                }

            } else {
                System.out.println(users.get(index).getUsername() + " backed to tweeting service menu");
                file_utility.record_events(users.get(index).getUsername() + " backed to tweeting service menu");
                break;
            }
        }
    }

    /**
     * this method removes a tweet or retweet from user's tweets or retweets
     */
    public void remove() throws InterruptedException {
        while (true) {
            usefulmethods.send_message(out, "1:remove_tweet" + '\n' + "2:back");
            System.out.println("process of remove a tweet by " + users.get(index).getUsername() + "  started");
            file_utility.record_events("process of remove a tweet by " + users.get(index).getUsername() + "  started");
            select = usefulmethods.read_message(in);
            if (select.equals("1")) {
                while (true) {
                    usefulmethods.send_message(out, "Tweet number: ");
                    int ix = Integer.parseInt(usefulmethods.read_message(in)) - 1;
                    if (is_valid_index(index, ix)) {
                        usefulmethods.send_message(out, "true");
                        System.out.println("the tweet(" + users.get(index).getTweets().get(ix).getText() + ") removed by " + users.get(index).getUsername());
                        file_utility.record_events("the tweet(" + users.get(index).getTweets().get(ix).getText() + ") removed by " + users.get(index).getUsername());
                        if (!(users.get(index).getTweets().get(ix).getSender().equals(users.get(index)))) {
                            for (int i = 0; i < users.get(index).getTweets().get(ix).getRetweets().size(); i++)
                                if (users.get(index).getTweets().get(ix).getRetweets().get(i).equals(users.get(index))) {
                                    users.get(index).getTweets().get(ix).getRetweets().remove(i);
                                    break;
                                }
                        } else {
                            ArrayList<User> likes = users.get(index).getTweets().get(ix).getLikes();
                            ArrayList<User> retweets = users.get(index).getTweets().get(ix).getRetweets();
                            for (User like : likes) {
                                for (int i = 0; i < like.getLiked().size(); i++)
                                    if (like.getLiked().get(i).equals(users.get(index).getTweets().get(ix))) {
                                        like.getLiked().remove(i);
                                        break;
                                    }
                            }
                            for (User retweet : retweets) {
                                for (int i = 0; i < retweet.getTweets().size(); i++)
                                    if (retweet.getTweets().get(i).equals(users.get(index).getTweets().get(ix))) {
                                        retweet.getTweets().remove(i);
                                        break;
                                    }
                            }

                        }
                        users.get(index).getTweets().remove(ix);
                        file_utility.make_changes(users);
                        usefulmethods.send_message(out, "the Tweet removed from your Tweets");
                        Thread.sleep(1);
                        break;
                    } else {
                        usefulmethods.send_message(out, "false");
                        usefulmethods.send_message(out, ("The number you entered is bigger than number of tweets" + '\n' + "1:continue attempting" + "\n" + "2: back"));
                        System.out.println(users.get(index).getUsername() + " wanted to remove " + (ix + 1) + "th of its tweets but this tweet was not exist.we requested from it to choose continue attempting or back");
                        file_utility.record_events(users.get(index).getUsername() + " wanted to remove " + (ix + 1) + "th of its tweets but this tweet was not exist.we requested from it to choose continue attempting or back");
                        if (usefulmethods.read_message(in).equals("2")) {
                            System.out.println(users.get(index).getUsername() + " chose to back to remove method of tweeting service");
                            file_utility.record_events(users.get(index).getUsername() + " chose to back to remove method of tweeting service");
                            break;
                        } else {
                            System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                            file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                        }
                    }
                }
            } else {
                System.out.println(users.get(index).getUsername() + " backed to tweeting service menu");
                file_utility.record_events(users.get(index).getUsername() + " backed to tweeting service menu");
                break;
            }
        }
    }

    /**
     * * this method likes a tweet from a user's tweets
     */
    public void like() throws InterruptedException {
        while (true) {
            usefulmethods.send_message(out, "1:like_tweet" + '\n' + "2:back");
            System.out.println("process of like a tweet by " + users.get(index).getUsername() + "  started");
            file_utility.record_events("process of like a tweet by " + users.get(index).getUsername() + "  started");
            select = usefulmethods.read_message(in);
            if (select.equals("1")) {
                int ix1;
                int ix2;
                while (true) {
                    usefulmethods.send_message(out, "from Tweets of which user you want to like a Tweet?(user number)" + '\n' +
                            "pay attention That you can not like your Tweets");
                    ix1 = Integer.parseInt(usefulmethods.read_message(in)) - 1;
                    System.out.println(users.get(index).getUsername() + " wants to like a tweet from user " + (ix1 + 1));
                    file_utility.record_events(users.get(index).getUsername() + " wants to like a tweet from user " + (ix1 + 1));
                    if (ix1 < users.size() && ix1 != index) {
                        usefulmethods.send_message(out, "true");
                        while (true) {
                            usefulmethods.send_message(out, "which Tweet you want to like?(Tweet number)");
                            ix2 = Integer.parseInt(usefulmethods.read_message(in)) - 1;
                            System.out.println(users.get(index).getUsername() + " wants to like " + (ix2 + 1) + "th tweet of user " + (ix1 + 1));
                            file_utility.record_events(users.get(index).getUsername() + " wants to like " + (ix2 + 1) + "th tweet of user " + (ix1 + 1));
                            if (is_valid_index(ix1, ix2)) {
                                ArrayList<String> likes = new ArrayList<>();
                                for (User user : users.get(ix1).getTweets().get(ix2).getLikes())
                                    likes.add(user.getUsername());
                                usefulmethods.send_message(out, "true");
                                if (likes.contains(users.get(index).getUsername())) {
                                    System.out.println(users.get(index).getUsername() + "have already liked this tweet");
                                    file_utility.record_events(users.get(index).getUsername() + "have already liked this tweet");
                                    usefulmethods.send_message(out, "you have already liked this tweet");
                                } else {
                                    users.get(ix1).getTweets().get(ix2).getLikes().add(users.get(index));
                                    users.get(index).getLiked().add(users.get(ix1).getTweets().get(ix2));
                                    users.get(index).getLiked().sort(new Sort_by_sendTime());
                                    file_utility.make_changes(users);
                                    System.out.println("like a tweet process by " + users.get(index).getUsername() + " successfully done");
                                    file_utility.record_events("like a tweet process by " + users.get(index).getUsername() + " successfully done");
                                    usefulmethods.send_message(out, "like this tweet successfully done");
                                }
                                Thread.sleep(1);
                                break;
                            } else {
                                usefulmethods.send_message(out, "false");
                                System.out.println("The number that " + users.get(index).getUsername() + " entered is bigger than number of Tweets that user " + users.get(ix1).getUsername() + " has!we requested from it to choose continue attempting or back");
                                file_utility.record_events("The number that " + users.get(index).getUsername() + " entered is bigger than number of Tweets that user " + users.get(ix1).getUsername() + " has!we requested from it to choose continue attempting or back");
                                usefulmethods.send_message(out, "The number that you entered is bigger than number of Tweets that " + users.get(ix1).getUsername() + " has!" + '\n' + "1:continue attempting" + "\n" + "2: back ");
                                if (usefulmethods.read_message(in).equals("2")) {
                                    System.out.println(users.get(index).getUsername() + " chose to back to like method of tweeting service");
                                    file_utility.record_events(users.get(index).getUsername() + " chose to back to like method of tweeting service");
                                    break;
                                } else {
                                    System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                                    file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                                }
                            }
                        }
                        break;
                    } else {
                        usefulmethods.send_message(out, "false");
                        if (ix1 != index) {
                            usefulmethods.send_message(out, ("This user does not exist." + '\n' + "1:continue attempting" + "\n" + "2: back "));
                            System.out.println("the number that " + users.get(index).getUsername() + "  entered was bigger than number of users.we requested from it to choose continue attempting or back");
                            file_utility.record_events("the number that " + users.get(index).getUsername() + "  entered was bigger than number of users.we requested from it to choose continue attempting or back");
                        } else {
                            usefulmethods.send_message(out, ("you can not like a tweet from yourself." + '\n' + "1:continue attempting" + "\n" + "2: back "));
                            System.out.println(users.get(index).getUsername() + " wanted to retweet a tweet from itself.we requested from it to choose continue attempting or back");
                            file_utility.record_events(users.get(index).getUsername() + " wanted to retweet a tweet from itself.we requested from it to choose continue attempting or back");
                        }
                        if (usefulmethods.read_message(in).equals("2")) {
                            System.out.println(users.get(index).getUsername() + " chose to back to like method of tweeting service");
                            file_utility.record_events(users.get(index).getUsername() + " chose to back to like method of tweeting service");
                            break;
                        } else {
                            System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                            file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                        }
                    }
                }

            } else {
                System.out.println(users.get(index).getUsername() + " backed to tweeting service menu");
                file_utility.record_events(users.get(index).getUsername() + " backed to tweeting service menu");
                break;
            }
        }
    }

    /**
     * this method checks that ix is a valid index or not
     *
     * @param index index of the user which we want to use from it
     * @param ix    the number which is checked that is an index or not
     * @return returns true if it be an index .otherwise returns false
     */
    private boolean is_valid_index(int index, int ix) {
        int flag = 0;
        if (ix < users.get(index).getTweets().size())
            flag = 1;
        return flag == 1;
    }
}
