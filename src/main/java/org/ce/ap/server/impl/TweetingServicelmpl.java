package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class has Methods that can add ,remove ,like and retweet a tweet
 * @author ashkan_mogharab
 *  @version version 1 of TweetingService implementation
 */
public class TweetingServicelmpl implements TweetingService {
    //an object of usefulMethods
    private usefulMethods usefulmethods = new usefulMethods();
    //users of ServerSide
    private ArrayList<User> users;
    //index of the user who wants to use from tweeting service
    private int index;
    // select of the person
    private int select;

    /**
     * creates a new tweeting service
     * @param users users of ServerSide
     * @param index index of the user who wants to use tweeting service
     */
    public TweetingServicelmpl(ArrayList<User> users, int index) {
        this.users = users;
        this.index = index;
        act();
    }

    /**
     * this method handles the works that should be done in tweeting service
     */
    private void act() {
        while (true) {
            System.out.println("Welcome to tweeting service" + "\n" + "1:add" + '\n' + "2:remove" + '\n' + "3:like" + '\n' + "4:back");
            while (true) {
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                if (select == 1 || select == 2 || select == 3 || select == 4 )
                    break;
            }

            if (select == 1)
                add();
            else if (select == 2)
                remove();
            else if (select == 3)
                like();
            else
                break;
        }
    }

    /**
     * this method adds a tweet to user's tweets
     */
    private void add() {
        while(true){
            System.out.println("1:tweet" + '\n' + "2:retweet" + '\n' + "3:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2 || select == 3)
                    break;
            }
            if (select == 1) {
                int flag = 0;
                String text;
                while (true) {
                    System.out.println("text:");
                    input.nextLine();
                    text = input.nextLine();
                    if (text.length() <= 256) {
                        System.out.println("the Tweet created and added to your Tweets");
                        flag = 1;
                        break;
                    }
                    System.out.println("This String is very long .maximum valid length is 256");
                    System.out.println("1:continue attempting" + "\n" + "2: back ");
                    String se = usefulmethods.continue_or_not();
                    if (se == "2")
                        break;
                }
                if (flag == 1) {
                    Tweet new_tweet = new Tweet(users.get(index), text, java.time.LocalDateTime.now());
                    users.get(index).getTweets().add(new_tweet);
                }
            } else if (select == 2) {
                int flag = 0;
                int flag1 = 0;
                int ix1;
                int ix2 = -1;
                while (true) {
                    System.out.println("from Tweets of which user you want to reTweet?(user number)");
                    ix1 = input.nextInt() - 1;
                    if (ix1 < users.size()) {
                        while (true) {
                            System.out.println("which Tweet you want to reTweet?(Tweet number)");
                            ix2 = input.nextInt() - 1;
                            if (is_valid_index(ix1, ix2)) {
                                flag = 1;
                                if(users.get(ix1).getTweets().get(ix2).getRetweets().contains(users.get(index)))
                                    System.out.println("You have already reTweeted this Tweet");
                                else {
                                    users.get(index).getTweets().add(users.get(ix1).getTweets().get(ix2));
                                    users.get(ix1).getTweets().get(ix2).getRetweets().add(users.get(index));
                                    System.out.println("reTweeting process successfully done");
                                }
                                break;
                            } else {
                                System.out.println("The number you entered is bigger than number of Tweets that this user has!");
                                System.out.println("1:continue attempting" + "\n" + "2: back");
                                String se = usefulmethods.continue_or_not();
                                if (se == "2"){
                                    flag1 = 1 ;
                                    break;
                                }
                            }
                        }
                        if(flag == 1 || flag1 == 1)
                            break;
                    } else {
                        System.out.println("The number you entered is bigger than number of users!");
                        System.out.println("1:continue attempting" + "\n" + "2: back");
                        String se = usefulmethods.continue_or_not();
                        if (se == "2")
                            break;
                    }
                }

            } else break;
        }
    }

    /**
     * this method removes a tweet or retweet from user's tweets or retweets
     */
    private void remove() {
        while(true){
            System.out.println("1:remove_tweet" + '\n' + "2:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if (select == 1) {
                while (true) {
                    System.out.println("Tweet number: ");
                    int ix = input.nextInt() - 1;
                    if (is_valid_index(index, ix)) {
                        users.get(index).getTweets().remove(ix);
                        System.out.println("the Tweet removed from your Tweets");
                        break;
                    } else {
                        System.out.println("The number you entered is bigger than number of tweets");
                        System.out.println("1:continue attempting" + "\n" + "2: back");
                        String se = usefulmethods.continue_or_not();
                        if (se == "2")
                            break;
                    }
                }
            } else break;
        }
    }

    /**
     *  * this method likes a tweet from a user's tweets
     */
    private void like(){
        while(true){
            System.out.println("1:like_tweet" + '\n' + "2:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if (select == 1) {
                int flag = 0;
                int flag1 = 0;
                int ix1;
                int ix2 = -1;
                while (true) {
                    System.out.println("from Tweets of which user you want to like a Tweet?(user number)"+'\n'+
                            "pay attention That you can not like your Tweets");
                    ix1 = input.nextInt() - 1;
                    if (ix1 < users.size() && ix1!=index) {
                        while (true) {
                            System.out.println("which Tweet you want to like?(Tweet number)");
                            ix2 = input.nextInt() - 1;
                            if (is_valid_index(ix1, ix2)) {
                                flag = 1;
                                if(users.get(ix1).getTweets().get(ix2).getLikes().contains(users.get(index)))
                                    System.out.println("You have already liked this Tweet");
                                else {
                                    System.out.println("This Tweet liked successfully");
                                    users.get(ix1).getTweets().get(ix2).getLikes().add(users.get(index));
                                    users.get(index).getLiked().add(users.get(ix1).getTweets().get(ix2));
                                }
                                break;
                            } else {
                                System.out.println("The number you entered is bigger than number of Tweets that this user has!");
                                System.out.println("1:continue attempting" + "\n" + "2: back");
                                String se = usefulmethods.continue_or_not();
                                if (se == "2"){
                                    flag1 = 1 ;
                                    break;
                                }
                            }
                        }
                        if(flag == 1 || flag1 == 1)
                            break;
                    } else {
                        if(ix1 == index)
                            System.out.println("You can not like a Tweet of yourself!");
                        else
                            System.out.println("The number you entered is bigger than number of users!");
                        System.out.println("1:continue attempting" + "\n" + "2: back");
                        String se = usefulmethods.continue_or_not();
                        if (se == "2")
                            break;
                    }
                }

            }
            else break;
        }
    }
    /**
     * this method checks that ix is a valid index or not
     *
     * @param index index of the user which we want to use from it
     * @param ix the number which is checked that is an index or not
     * @return returns true if it be an index .otherwise returns false
     */
    private boolean is_valid_index(int index ,int ix) {
        int flag = 0 ;
        if (ix < users.get(index).getTweets().size())
            flag = 1;
        if(flag == 1)
            return true;
        return false;
    }
}
