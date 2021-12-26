package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
/**
 * this class has Methods that a user can follow and unfollow another user and also can observe Tweets of its favorite users
 @author  ashkan_mogharab
 @version version 1 of  ObserverService implementation
 */
public class ObserverService_impl implements ObserverService {
    // an object of File_utility
    File_utility file_utility = new File_utility();
    //a socket for connecting  to user
    private final Socket connectionSocket;
    // an output stream
    private OutputStream out;
    // an input stream
    private InputStream in;
    //an object of usefulMethods
    private final usefulMethods usefulmethods = new usefulMethods();
    //users of ServerSide
    private final ArrayList<User> users;
    //index of the user who wants to use from tweeting service
    private final int index;
    // select of the person
    private String select;

    /**
     * creates a new observer service
     * @param users users of ServerSide
     * @param index index of the user who wants to use observer service
     * @param connectionSocket a socket for connecting  to user
     */
    public ObserverService_impl(ArrayList<User> users, int index,Socket connectionSocket) {
        this.users = users;
        this.index = index;
        this.connectionSocket = connectionSocket;
        act();
    }
    /**
     * this method handles the works that should be done in observer service
     */
    private void act() {
        try {
            out = connectionSocket.getOutputStream();
             in = connectionSocket.getInputStream();
            label:
            while (true) {
                String msg;
                usefulmethods.send_message(out,"Welcome to observer service" + "\n" + "1:follow a user " + '\n' + "2:unfollow a user" + '\n' + "3:show Tweets of favorite users" + '\n' + "4:back");
                System.out.println("welcoming message of observer service sent to " + users.get(index).getUsername());
                file_utility.record_events("welcoming message of observer service sent to " + users.get(index).getUsername());
                System.out.println("receive from " + users.get(index).getUsername() + " : " + (msg= usefulmethods.read_message(in)));
                file_utility.record_events("receive from " + users.get(index).getUsername() + " : " + msg);
                switch (msg) {
                    case "1":
                        follow();
                        break;
                    case "2":
                        unfollow();
                        break;
                    case "3":
                        observe();
                        break;
                    default:
                        System.out.println(users.get(index).getUsername() + " Quited from observer service");
                        file_utility.record_events(users.get(index).getUsername() + " Quited from observer service");
                        break label;
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }
    /**
     * this method handles process of following a user
     */
    private void follow() throws InterruptedException {
        while (true) {
            usefulmethods.send_message(out,"1:follow" + '\n' + "2:back");
            System.out.println("process of follow a user by " + users.get(index).getUsername()+"  started");
            file_utility.record_events("process of follow a user by " + users.get(index).getUsername()+"  started");
            select = usefulmethods.read_message(in);
            System.out.println("received " + select);
            file_utility.record_events("received " + select);
            if (select.equals("1")) {
                int ix1;
                while (true) {
                    usefulmethods.send_message(out,"which user you want to follow?(user number)" + '\n' +
                            "pay attention That you can not follow yourself");
                    ix1 = Integer.parseInt(usefulmethods.read_message(in)) - 1;
                    System.out.println(users.get(index).getUsername() + " wants to follow user " + (ix1+1));
                    file_utility.record_events(users.get(index).getUsername() + " wants to follow user " + (ix1+1));
                    if (ix1 < users.size() && ix1 != index) {
                        usefulmethods.send_message(out,"true");
                        ArrayList<String>favorites= new ArrayList<>();
                        for(User user : users.get(index).getFavoriteUsers())
                            favorites.add(user.getUsername());
                        if (favorites.contains(users.get(ix1).getUsername())){
                            usefulmethods.send_message(out, "you have already followed this user");
                            System.out.println(users.get(index).getUsername() + " have already followed this user");
                            file_utility.record_events(users.get(index).getUsername() + " have already followed this user");
                        }

                        else {
                            users.get(ix1).getFollowers().add(users.get(index));
                            users.get(index).getFavoriteUsers().add(users.get(ix1));
                            file_utility.make_changes(users);
                            usefulmethods.send_message(out,"follow this user successfully done");
                            System.out.println("follow a user process by " + users.get(index).getUsername()+" successfully done");
                            file_utility.record_events("follow a user process by " + users.get(index).getUsername()+" successfully done");
                        }
                        Thread.sleep(1);
                        break;
                    } else {
                        usefulmethods.send_message(out,"false");
                        if (ix1 == index){
                            usefulmethods.send_message(out, ("you can not follow yourself." + '\n' + "1:continue attempting" + "\n" + "2: back "));
                            System.out.println(users.get(index).getUsername() + " wanted to follow itself.we requested from it to choose continue attempting or back");
                           file_utility.record_events(users.get(index).getUsername() + " wanted to follow itself.we requested from it to choose continue attempting or back");
                        }
                        else{
                            usefulmethods.send_message(out, ("This user does not exist." + '\n' + "1:continue attempting" + "\n" + "2: back "));
                            System.out.println("the number that " + users.get(index).getUsername() + "  entered was bigger than number of users.we requested from it to choose continue attempting or back");
                            file_utility.record_events("the number that " + users.get(index).getUsername() + "  entered was bigger than number of users.we requested from it to choose continue attempting or back");
                        }
                        if ( usefulmethods.read_message(in) .equals("2")) {
                            System.out.println(users.get(index).getUsername() + " chose to back to follow method of observer service");
                            file_utility.record_events(users.get(index).getUsername() + " chose to back to follow method of observer service");
                            break;
                        }
                        else {
                            System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                            file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                        }
                    }
                }
            } else {
                System.out.println(users.get(index).getUsername()+ " backed to observer service menu");
                file_utility.record_events(users.get(index).getUsername()+ " backed to observer service menu");
                break;
            }
        }
    }
    /**
     * this method handles process of unfollowing a user
     */
    private void unfollow() throws InterruptedException {
            while (true) {
                usefulmethods.send_message(out, "1:unfollow" + '\n' + "2:back");
                System.out.println("process of unfollow a user by " + users.get(index).getUsername() + "  started");
                file_utility.record_events("process of unfollow a user by " + users.get(index).getUsername() + "  started");
                select = usefulmethods.read_message(in);
                if (select.equals("1")) {
                    int ix1;
                    while (true) {
                        usefulmethods.send_message(out, "which of your favorite users you want to unfollow?(favorite user number)");
                        ix1 = Integer.parseInt(usefulmethods.read_message(in)) - 1;
                        System.out.println(users.get(index).getUsername() + " wants to unfollow " + (ix1 + 1) + "th of its favorite users");
                        file_utility.record_events(users.get(index).getUsername() + " wants to unfollow " + (ix1 + 1) + "th of its favorite users");
                        if (ix1 < users.get(index).getFavoriteUsers().size()) {
                            usefulmethods.send_message(out, "true");
                           for(int i = 0 ; i < users.get(index).getFavoriteUsers().get(ix1).getFollowers().size();i++)
                               if(users.get(index).getFavoriteUsers().get(ix1).getFollowers().get(i).equals(users.get(index))){
                                   users.get(index).getFavoriteUsers().get(ix1).getFollowers().remove(i);
                                   break;
                               }
                            users.get(index).getFavoriteUsers().remove(ix1);
                            file_utility.make_changes(users);
                            System.out.println("unfollow a user process by " + users.get(index).getUsername() + " successfully done");
                            file_utility.record_events("unfollow a user process by " + users.get(index).getUsername() + " successfully done");
                            usefulmethods.send_message(out, "unfollow this user successfully done");
                            Thread.sleep(1);
                            break;
                        } else {
                            usefulmethods.send_message(out, "false");
                            usefulmethods.send_message(out, ("This user does not exist." + '\n' + "1:continue attempting" + "\n" + "2: back "));
                            System.out.println("the number that " + users.get(index).getUsername() + "  entered was bigger than number of its favorite users.we requested from it to choose continue attempting or back");
                            file_utility.record_events("the number that " + users.get(index).getUsername() + "  entered was bigger than number of its favorite users.we requested from it to choose continue attempting or back");
                            if (usefulmethods.read_message(in).equals("2")) {
                                System.out.println(users.get(index).getUsername() + " chose to back to unfollow method of observer service");
                                file_utility.record_events(users.get(index).getUsername() + " chose to back to unfollow method of observer service");
                                break;
                            } else {
                                System.out.println(users.get(index).getUsername() + " chose to continue attempting");
                                file_utility.record_events(users.get(index).getUsername() + " chose to continue attempting");
                            }
                        }
                    }
                } else {
                    System.out.println(users.get(index).getUsername() + " backed to observer service menu");
                    file_utility.record_events(users.get(index).getUsername() + " backed to observer service menu");
                    break;
                }
            }
        }
    /**
     * this method shows tweets of favorite users of the user
     */
    private void observe() throws InterruptedException {
        while (true) {
            usefulmethods.send_message(out, "1:observe" + '\n' + "2:back");
            System.out.println("process of observe favorite users Tweets by " + users.get(index).getUsername() + "  started");
            file_utility.record_events("process of observe favorite users Tweets by " + users.get(index).getUsername() + "  started");
            select = usefulmethods.read_message(in);
            if (select.equals("1")) {
            int count = 0 ;
            for(User favorite : users.get(index).getFavoriteUsers())
                for(Tweet tweet : favorite.getTweets())
                    if(tweet.getSender().equals(favorite))
                        count ++;
                    count+= users.get(index).getFavoriteUsers().size();
                    usefulmethods.send_message(out,"" + count);
                    for(User favorite : users.get(index).getFavoriteUsers()){
                        int count1 = 1;
                        usefulmethods.send_message(out,favorite.getUsername());
                        Thread.sleep(1);
                        for(Tweet tweet : favorite.getTweets())
                            if(tweet.getSender().equals(favorite))
                        usefulmethods.send_message(out,"Tweet " + (count1++) + " : text: " +tweet.getText() + "  sendTime: " +tweet.getSendDate() + "  " + tweet.getLikes().size() + " likes" + "  " + tweet.getRetweets().size() + " retweets");
                        Thread.sleep(1);
                    }
                System.out.println("process of observe favorite users Tweets by " + users.get(index).getUsername() + "  ended");
                    file_utility.record_events("process of observe favorite users Tweets by " + users.get(index).getUsername() + "  ended");
            }
            else {
                System.out.println(users.get(index).getUsername() + " backed to observer service menu");
                file_utility.record_events(users.get(index).getUsername() + " backed to observer service menu");
                break;
            }
        }
    }
}
