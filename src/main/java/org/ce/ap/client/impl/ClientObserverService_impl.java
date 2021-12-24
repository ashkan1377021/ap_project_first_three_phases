package main.java.org.ce.ap.client.impl;
import main.java.org.ce.ap.client.ClientObserverService;
import main.java.org.ce.ap.server.usefulMethods;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * this class has Methods that a user can follow and unfollow another user and also can observe Tweets of its favorite users
 @author  ashkan_mogharab
 @version version 1 of  ClientObserverService implementation
 */
    public class ClientObserverService_impl implements ClientObserverService {
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
         * creates a new client tweeting service
         */
        public ClientObserverService_impl(){
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
                } while (!select.equals("1") && !select.equals("2") && !select.equals("3") && !select.equals("4"));
                usefulmethods.send_message(out, select);
              if(select.equals("1") || select.equals("2"))
                  follow_unfollow();
              else if (select.equals("3"))
                  observe();
              else
                  break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * this method handles process of following/unfollowing a user
     */
    private void follow_unfollow() {
        while (true) {
            System.out.println(usefulmethods.read_message(in) );
            do {
                select = input.nextLine();
            } while (!select.equals("1") && !select.equals("2"));
            usefulmethods.send_message(out, select);
            if (select.equals("1")) {
                String select2;
                select2 = usefulmethods.run_few_statement2(out, in, select, input);
                if (!(select2.equals("2")))
                    System.out.println(usefulmethods.read_message(in));
            }
            else break;
        }
    }

    /**
     * this method observes tweets of favorite users of the user
     */
    private void observe() {
        while (true) {
            System.out.println(usefulmethods.read_message(in));
            do {
                select = input.nextLine();
            } while (!select.equals("1") && !select.equals("2"));
            usefulmethods.send_message(out, select);
            if(select.equals("1")){
                int count  = Integer.parseInt(usefulmethods.read_message(in));
                int i = 0 ;
                while(i!=count){
                    System.out.println("started");
                    System.out.println(usefulmethods.read_message(in));
                    System.out.println("ended");
                    i++;
                }
            }
            else break;
        }
    }
}
