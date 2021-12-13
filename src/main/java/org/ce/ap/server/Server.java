package main.java.org.ce.ap.server;
import main.java.org.ce.ap.server.impl.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        //users of ServerSide
        ArrayList<User> users = new ArrayList<>();
        AuthenticationServicelmpl authenticationService;
        TweetingServicelmpl tweetingService;
        ObserverServicelmpl observerService;
        TimelineServicelmpl timelineService;
        // select of the person
        int select;
        //index of user who signs in or signs up
        int index;
        User user1 = new User("ashkan", "mogharab", "ashkan1998", "ashkan231998", LocalDate.of(1998, 12, 28), LocalDate.now(), "deymahi");
        User user2 = new User("hossein", "karimi", "hossein1998", "hossein", LocalDate.of(1998, 12, 28), LocalDate.now(), "deymahi");
        Tweet Tweet1 = new Tweet(user1, "Today is Monday",java.time.LocalDateTime.now());
        Tweet Tweet2 = new Tweet(user2, "Today is Tuesday",java.time.LocalDateTime.now());

        user1.getTweets().add(Tweet1);
        user2.getTweets().add(Tweet2);
        Tweet1.getLikes().add(user2);
        user2.getLiked().add(Tweet1);
        users.add(user1);
        users.add(user2);
        authenticationService = new AuthenticationServicelmpl(users);
        index = authenticationService.getJ();
        while (true) {
            System.out.println("Services:" + "\n" + "1:Tweeting Service" + '\n' + "2:Observer Service" + '\n' + "3:Timeline Service" + '\n' +"4:show some information about users" +'\n' + "5:Quit");
            while (true) {
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                if (select == 1 || select == 2 || select == 3 || select == 4 || select == 5)
                    break;
            }
            if (select == 1)
                tweetingService = new TweetingServicelmpl(users, index);
            else if (select == 2)
                observerService = new ObserverServicelmpl(users,index);
            else if (select == 3)
                timelineService = new TimelineServicelmpl(users.get(index));
            else if (select == 4)
                for (User user : users) {
                    int count1 = 1;
                    int count2 = 1;
                    System.out.println(user.getUsername());
                    System.out.println("favorite Tweets: " + user.getLiked());
                    for (Tweet tweet : user.getTweets())
                        if(tweet.getSender().equals(user))
                        System.out.println("Tweet" + (count1++) + ":   " +tweet.toString());
                        else
                            System.out.println("reTweet" + (count2++) + ":   sender:" + tweet.getSender().getUsername()+ ",  " +tweet.toString());

                }
            else break;
        }
    }
}
