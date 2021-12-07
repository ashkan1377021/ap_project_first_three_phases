import com.sun.deploy.security.SelectableSecurityManager;

import java.time.*;
import java.util.*;
/**
 * this class holds details of a ServerSide
 * @author ashkan_mogharab
 */
public class ServerSide {
    public static void main(String[] args) {
        //users of ServerSide
        ArrayList<User> users = new ArrayList<>();
        AuthenticationService authenticationService;
        TweetingService tweetingService;
        ObserverService observerService;
        TimelineService timelineService;
        // select of the person
        int select;
        //index of user who signs in or signs up
        int index;
        User user1 = new User("ashkan", "mogharab", "ashkan1998", "ashkan231998", LocalDate.of(1998, 12, 28), LocalDate.now(), "deymahi");
        Tweet Tweet1 = new Tweet(user1, "Today is Monday",java.time.LocalDateTime.now());
        user1.getTweets().add(Tweet1);
        users.add(user1);
        authenticationService = new AuthenticationService(users);
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
                tweetingService = new TweetingService(users, index);
            else if (select == 2)
                 observerService = new ObserverService(users,index);
            else if (select == 3)
                 timelineService = new TimelineService(users.get(index));
            else if (select == 4)
                for (User user : users) {
                    System.out.println(user.getUsername());
                    for (int i = 0; i < user.getTweets().size(); i++)
                        System.out.println("Tweet " + (i + 1) + " : text: " + user.getTweets().get(i).getText() + "  sendTime: " + user.getTweets().get(i).getSendDate() + "  " + user.getTweets().get(i).getLikes().size() + " likes");
                }

            else break;
        }
    }
}

