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
    private ArrayList<User> users;
    //index of the user who wants to use from tweeting service
    private int index;
    // select of the person
    private int select;

    /**
     * creates a new tweeting service
     *
     * @param users the user who wants to use tweeting service
     */
    public TweetingService(ArrayList<User> users, int index) {
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
            else if (select == 2)
                remove();
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
    private void add() {
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
            if (se == 2)
                act();
        }
        Tweet new_tweet = new Tweet(users.get(index), text, LocalDate.now());
        users.get(index).getTweets().add(new_tweet);
    }

    /**
     * this method removes a tweet or retweet from user's tweets or retweets
     */
    private void remove() {
        System.out.println("1:tweet" + '\n' + "2:retweet" + '\n' + "3:back");
        Scanner input = new Scanner(System.in);
        while (true) {
            select = input.nextInt();
            if (select == 1 || select == 2 || select == 3)
                break;
        }
        if (select == 1) {
            while (true) {
                System.out.println("Tweet number: ");
                int ix = input.nextInt() - 1;
                if (is_valid_index(ix, 1)) {
                    System.out.println(users.get(index).getTweets().get(ix).toString());
                    users.get(index).getTweets().remove(ix);
                    System.out.println("the Tweet removed from your Tweets");
                    System.out.println("" +users.get(index).getTweets().size());
                    break;
                } else {
                    System.out.println("The number you entered is bigger than number of tweets");
                    System.out.println("1:continue attempting" + "\n" + "2: back");
                    int se = usefulmethods.continue_or_not();
                    if (se == 2)
                        remove();
                }
            }
        } else if (select == 2) ;
        else act();
    }

    /**
     * this method checks that ix is a valid index or not
     *
     * @param i  if it be 1 refers to tweets and if it be 2 refers to retweets
     * @param ix the number which is checked that is an index or not
     * @return returns true if it be an index .otherwise returns false
     */
    private boolean is_valid_index(int ix, int i) {
        int flag = 0 ;
        if (i == 1) {
            if (ix < users.get(index).getTweets().size())
                flag = 1;
        }
        else if (i == 2) {
            if (ix < users.get(index).getRetweets().size())
                flag = 1;
        }
        if(flag == 1)
            return true;
        return false;
    }
}