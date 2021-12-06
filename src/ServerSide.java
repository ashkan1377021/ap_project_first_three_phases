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
        // select of the person
        int select;
        //index of user who signs in or signs up
        int index;
        User user1 = new User("ashkan", "mogharab", "ashkan1998", "ashkan231998", LocalDate.of(1998, 12, 28), LocalDate.now(), "deymahi");
        users.add(user1);
        authenticationService = new AuthenticationService(users);
        index = authenticationService.getJ();
        while (true) {
            System.out.println("Services:" + "\n" + "1:Tweeting Service" + '\n' + "2:Observer Service" + '\n' + "3:Timeline Service" + '\n' + "4:Quit");
            while (true) {
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                if (select == 1 || select == 2 || select == 3 || select == 4)
                    break;
            }
                    if (select == 1)
                        tweetingService = new TweetingService(users, index);
                     else if (select == 2) ;
                        //ObserverService observerService = new ObserverService(users,index);
                    else if (select == 3) ;
                        //TimelineService timelineService = new TimelineService(users,index);
                    else
                        break;
                }
            }
        }

