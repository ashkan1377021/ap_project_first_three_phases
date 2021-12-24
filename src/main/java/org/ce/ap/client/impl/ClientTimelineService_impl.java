package main.java.org.ce.ap.client.impl;
import main.java.org.ce.ap.client.ClientTimelineService;
import main.java.org.ce.ap.server.usefulMethods;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class has Methods that a user can observe its favorite users Tweets with their likes and reTweets
 * @author ashkan_mogharab
 * @version version 1 of ClientTimelineService implementation
 */
public class ClientTimelineService_impl implements ClientTimelineService {
    //an object of usefulMethods
    private usefulMethods usefulmethods;
    // an input stream
    private InputStream in;
    // an output stream
    private OutputStream out;
    // select of client
    private String select;
    // a scanner
    private final Scanner input = new Scanner(System.in);

    /**
     * creates a new client timeline service
     */
    public ClientTimelineService_impl() {
        act();
    }

    private void act() {
        usefulmethods = new usefulMethods();
        try (Socket client = new Socket("127.0.0.1", 7600)) {
            out = client.getOutputStream();
            in = client.getInputStream();
            while (true) {
                System.out.println(usefulmethods.read_message(in) + "");
                do {
                    select = input.nextLine();
                } while (!select.equals("1") && !select.equals("2"));
                usefulmethods.send_message(out, select);
                if(select.equals("1"))
                    showTweets();
                else
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * this method shows Tweets,reTweets & likes of each favorite user
     */
    private void showTweets() {
        while (true) {
            System.out.println(usefulmethods.read_message(in));
            do {
                select = input.nextLine();
            } while (!select.equals("1") && !select.equals("2"));
            usefulmethods.send_message(out, select);
            if (select.equals("1")) {
                int count  = Integer.parseInt(usefulmethods.read_message(in));
                int i = 0 ;
                while(i!=count){
                    System.out.println("" + usefulmethods.read_message(in));
                    i++;
                }
            }
            else break;
        }
    }
}
