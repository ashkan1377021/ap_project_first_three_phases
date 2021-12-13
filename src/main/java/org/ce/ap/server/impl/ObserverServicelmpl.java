package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class has Methods that a user can follow and unfollow another userand also can observe Tweets of its favorite users
 @author  ashkan_mogharab
 @version version 1 of  ObserverService implementation
 */
public class ObserverServicelmpl implements ObserverService {
    //an object of usefulMethods
    private usefulMethods usefulmethods = new usefulMethods();
    //users of ServerSide
    private ArrayList<User> users;
    //index of the user who wants to use from tweeting service
    private int index;
    // select of the person
    private int select;

    /**
     * creates a new observer service
     *
     * @param users users of ServerSide
     * @param index index of the user who wants to use observer service
     */
    public ObserverServicelmpl(ArrayList<User> users, int index) {
        this.users = users;
        this.index = index;
        act();
    }


    /**
     * this method handles the works that should be done in observer service
     */
    private void act() {
        while (true) {
            System.out.println("Welcome to observer service" + "\n" + "1:follow a user " + '\n' + "2:unfollow a user" + '\n' + "3:observe Tweets of favorite users" + '\n' + "4:back");
            while (true) {
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                if (select == 1 || select == 2 || select == 3 || select == 4)
                    break;
            }

            if (select == 1)
                follow();
            else if (select == 2)
                unfollow();
            else if (select == 3)
                observe();
            else
                break;
        }
    }

    /**
     * this method handles process of following a user
     */
    private void follow() {
        while (true) {
            System.out.println("1:follow" + '\n' + "2:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if (select == 1) {
                int flag = 0;
                int ix1;
                while (true) {
                    System.out.println("which user you want to follow?(user number)" + '\n' +
                            "pay attention That you can not follow yourself");
                    ix1 = input.nextInt() - 1;
                    if (ix1 < users.size() && ix1 != index) {
                        flag = 1;
                        if (users.get(index).getFavoriteUsers().contains(users.get(ix1)))
                            System.out.println("You have already followed this user");
                        else {
                            users.get(ix1).getFollowers().add(users.get(index));
                            users.get(index).getFavoriteUsers().add(users.get(ix1));
                            System.out.println("following this user successfully done");
                        }
                        break;
                    } else {
                        if (ix1 == index)
                            System.out.println("You can not follow yourself!");
                        else
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
     * this method handles process of unfollowing a user
     */
    private void unfollow() {
        while (true) {
            System.out.println("1:unfollow" + '\n' + "2:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if (select == 1) {
                int flag = 0;
                int ix1;
                while (true) {
                    System.out.println("which of your favorite users you want to unfollow?(favorite user number)");
                    ix1 = input.nextInt() - 1;
                    if (ix1 < users.get(index).getFavoriteUsers().size()) {
                        flag = 1;
                        users.get(index).getFavoriteUsers().remove(users.get(ix1));
                        users.get(ix1).getFollowers().remove(users.get(index));
                        System.out.println("unfollowing this user successfully done");
                        break;
                    } else {
                        System.out.println("The number you entered is bigger than number of favorite users!");
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
     * this method shows tweets of favorite users of the user
     */
    private void observe(){
        while (true) {
            System.out.println("1:observe" + '\n' + "2:back");
            Scanner input = new Scanner(System.in);
            while (true) {
                select = input.nextInt();
                if (select == 1 || select == 2)
                    break;
            }
            if (select == 1) {
                int count = 1;
                for(User user : users.get(index).getFavoriteUsers()) {
                    System.out.println(user.getUsername());
                    for(int i = 0 ;i < user.getTweets().size();i++)
                        if(user.getTweets().get(i).getSender().equals(user))
                        System.out.println("Tweet " + (count++) + " : text: " +user.getTweets().get(i).getText() + "  sendTime: " +user.getTweets().get(i).getSendDate() + "  " + user.getTweets().get(i).getLikes().size() + " likes" + "  " + user.getTweets().get(i).getRetweets().size() + " retweets");

                }

            }
            else break;
        }
    }
}
