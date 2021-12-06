import java.util.*;
import java.time.*;
/**
 * this class has Methods that can add ,remove ,like and retweet a tweet
 * @author ashkan_mogharab
 */
public class TweetingService {
    //an object of usefulMethods
    private usefulMethods usefulmethods = new usefulMethods();
    //users of ServerSide
    private  ArrayList<User> users;
    //index of the user who wants to use from tweeting service
    private int index;
    // select of the person
    private int select;
    /**
     * creates a new tweeting service
     * @param users the user who wants to use tweeting service
     */
    public TweetingService(ArrayList<User> users,int index){
        this.users = users;
        this.index = index;
       act();
    }

    /**
     * this method handles the works that should be done in tweeting service
     */
    private void act() {
        while (true) {
            System.out.println("Welcome to tweeting service" + "\n" + "1:add" + '\n' + "2:remove" + '\n' + "3:reTweet"
                    + '\n' + "4:like" + '\n' + "5:back");
            while (true) {
                Scanner input = new Scanner(System.in);
                select = input.nextInt();
                if (select == 1 || select == 2 || select == 3 || select == 4 || select == 5)
                    break;
            }

            if (select == 1)
                add();
            else if (select == 2) ;
                //remove();
            else if (select == 3) ;
                //retweet();
            else if (select == 4) ;
                //like();
            else break;

        }
    }
    /**
     * this method adds a tweet to user's tweets
     */
        private void add () {
            String text;
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("text:");
                text = input.nextLine();
                if (text.length() <= 256) {
                    System.out.println("the Tweet created and added to your Tweets");
                    break;
                }
                System.out.println("This String is very long .maximum valid length is 256");
                System.out.println("1:continue attempting" + "\n" + "2: back ");
                int se = usefulmethods.continue_or_not();
                if(se == 2)
                    act();
            }
            Tweet new_tweet = new Tweet(users.get(index),text,LocalDate.now());
            users.get(index).getTweets().add(new_tweet);
        }
}
