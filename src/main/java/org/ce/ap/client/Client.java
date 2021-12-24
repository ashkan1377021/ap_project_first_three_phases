package main.java.org.ce.ap.client;
import main.java.org.ce.ap.client.impl.ClientAuthenticationService_impl;
import main.java.org.ce.ap.client.impl.ClientObserverService_impl;
import main.java.org.ce.ap.client.impl.ClientTimelineService_impl;
import main.java.org.ce.ap.client.impl.ClientTweetingService_impl;
import main.java.org.ce.ap.server.usefulMethods;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
public class Client {
    public static void main(String[] args) {
        try (Socket client = new Socket("127.0.0.1", 7600)) {
            usefulMethods usefulmethods = new usefulMethods();
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            Scanner input = new Scanner(System.in);
            String select;
            System.out.println("connecting ....");
            new ClientAuthenticationService_impl(client);
            while (true) {
                System.out.println("commands:" + '\n' + "1:go to Tweeting service" + '\n' + "2:go to Observer service" + '\n' + "3:go to Timeline service" + '\n'  + "4:show users and Tweets/reTweets/likes of them " +'\n' +"5:Quit");
                do {
                    select = input.nextLine();
                } while (!select.equals("1") && !select.equals("2") && !select.equals("3") && !select.equals("4") && !select.equals("5"));
                usefulmethods.send_message(out,select);
                if (select.equals("1"))
                new ClientTweetingService_impl(client);
                else if (select.equals("2"))
                new ClientObserverService_impl(client);
                else if (select.equals("3"))
                new ClientTimelineService_impl(client);
                else if(select.equals("4")){
                    int count  = Integer.parseInt(usefulmethods.read_message(in));
                    int i = 0 ;
                    while(i!=count){
                        System.out.println("" + usefulmethods.read_message(in));
                        i++;
                    }
                }
                else {
                    System.out.println(usefulmethods.read_message(in));
                    System.exit(0);
                }
            }
            //new ClientTimelineService_impl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
