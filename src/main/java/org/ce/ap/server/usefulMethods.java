package main.java.org.ce.ap.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
/**
 * this class has some useful methods
 * @author ashkan_mogharab
 */
public class usefulMethods{
    /**
     * this method hashes a string to an array of bytes
     * @param input an string
     * @return hash of the string
     */
    public  byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    /**
     *
     * @param hash input of toHexString method with array of bytes form
     * @return hex string of password
     */
    public  String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    /**
     * this method checks that person wants to continue attempting or not
     */
    public String continue_or_not(){
        String sel ="-1";
        while((!(sel .equals("1"))) && (!(sel.equals("2")))){
            Scanner input = new Scanner(System.in);
            sel = input.nextLine();
        }
        return sel;
    }

    /**
     * this method sends a message to its destination
     * @param out an output stream
     * @param string a message
     */
    public void send_message(OutputStream out , String string){
        try{
            out.write(string.getBytes());
        }
        catch(IOException ex){
           ex.printStackTrace();
        }
    }

    /**
     * this method reads a message from its source
     * @param in an input stream
     * @return message
     */
    public String read_message(InputStream in){
        String message = null;
        try {
            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
             new String(buffer, 0, read);
             message = new String(buffer,0,read);
        }
        catch(IOException ex){
           ex.printStackTrace();
        }
        return message;
    }
    /**
     * run a few statement that are duplicate in main code
     */
    public String run_few_statement2( OutputStream out, InputStream in, String select, Scanner input) {
        while (true) {
            System.out.println(read_message(in));
            select = input.nextLine();
            send_message(out, select);
            if (read_message(in).equals("true")) {
                select = "-1";
                break;
            }
            else {
                System.out.println( read_message(in));
                select = continue_or_not();
                send_message(out, select);
                if (select.equals("2")) {
                    break;
                }
            }
        }
        return select;
    }

     public void run_few_statement1( OutputStream out, InputStream in, String select, Scanner input) {
        System.out.println(read_message(in));
        select = input.nextLine();
        send_message(out, select);
    }

    /**
     * this method shows users and tweets/retweets & likes of them
     * @param users users of the server
     * @param index index of the user who wants to observe information
     * @param out an output stream
     */
    public void showUsersDetails(ArrayList<User> users , int index , OutputStream out) throws InterruptedException {
        System.out.println("process of observe details of users by " + users.get(index).getUsername()+" started");
        int count = users.size();
        for(User user : users)
            count+= user.getTweets().size() + user.getLiked().size();
        send_message(out,"" + count);
        Thread.sleep(1);
        for (User user : users) {
            send_message(out,user.getUsername());
            Thread.sleep(1);
            ArrayList<Tweet> tweets = user.getTweets();
            int count1 = 1;
            int count2 = 1;
            tweets.sort(new Sort_by_sendTime());
            for (Tweet tweet : tweets) {
                if (tweet.getSender().equals(user))
                   send_message(out, "Tweet " + (count1++) + " : text: " + tweet.getText() + "  sendTime: " + tweet.getSendDate() + "  " + tweet.getLikes().size() + " likes  " + tweet.getRetweets().size() + " retweets");
                else
                    send_message(out,"reTweet " + (count2++) + ": Sender: " + tweet.getSender().getUsername() + " : text: " + tweet.getText() + "  sendTime: " + tweet.getSendDate() + "  " + tweet.getLikes().size() + " likes  " + tweet.getRetweets().size() + " retweets");
                Thread.sleep(1);
            }
            ArrayList<Tweet> liked_tweets = user.getLiked();
            liked_tweets.sort(new Sort_by_sendTime());
            for (Tweet liked_tweet : liked_tweets) {
                send_message(out,"liked by " + user.getUsername() + '\n' + "Sender: " + liked_tweet.getSender().getUsername() + " text: " + liked_tweet.getText() + " SendTime: " + liked_tweet.getSendDate() + " " + liked_tweet.getLikes().size() + " likes " + liked_tweet.getRetweets().size() + " retweets");
                Thread.sleep(1);
            }
            System.out.println('\n');

        }
        System.out.println("process of observe details of users by " + users.get(index).getUsername()+" ended");
    }
}
