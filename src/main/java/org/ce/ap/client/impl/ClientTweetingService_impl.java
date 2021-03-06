package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.ClientTweetingService;
import main.java.org.ce.ap.server.usefulMethods;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class has Methods that can add ,remove ,like and retweet a tweet
 *
 * @author ashkan_mogharab
 * @version version 1 of ClientTweetingService implementation
 */
public class ClientTweetingService_impl implements ClientTweetingService {
    // a scanner
    private final Scanner input = new Scanner(System.in);
    // a socket
    Socket client;
    //an object of usefulMethods
    private usefulMethods usefulmethods;
    // an input stream
    private InputStream in;
    // an output stream
    private OutputStream out;
    // select of client
    private String select;

    /**
     * creates a new client tweeting service
     *
     * @param client a socket
     */
    public ClientTweetingService_impl(Socket client) {
        this.client = client;
        act();
    }

    /**
     * this method handles the works that should be done in ClientTweeting Service
     */
    public void act() {
        usefulmethods = new usefulMethods();
        try {
            out = client.getOutputStream();
            in = client.getInputStream();
            label:
            while (true) {
                System.out.println(usefulmethods.read_message(in));
                do {
                    select = input.nextLine();
                } while (!select.equals("1") && !select.equals("2") && !select.equals("3") && !select.equals("4"));
                usefulmethods.send_message(out, select);
                switch (select) {
                    case "1":
                        add();
                        break;
                    case "2":
                        remove();
                        break;
                    case "3":
                        like();
                        break;
                    default:
                        break label;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * this method adds a tweet to user's tweets
     */
    public void add() {
        String select2;
        while (true) {
            System.out.println(usefulmethods.read_message(in));
            do {
                select = input.nextLine();
            } while (!select.equals("1") && !select.equals("2") && !select.equals("3"));
            usefulmethods.send_message(out, select);
            if (select.equals("1")) {
                select2 = usefulmethods.run_few_statement2(out, in, select, input);
                if (!(select2.equals("2")))
                    System.out.println(usefulmethods.read_message(in));
            } else if (select.equals("2")) {
                select2 = usefulmethods.run_few_statement2(out, in, select, input);

                if (!(select2.equals("2"))) {
                    select2 = usefulmethods.run_few_statement2(out, in, select, input);
                    if (!(select2.equals("2")))
                        System.out.println(usefulmethods.read_message(in));
                }
            } else break;
        }
    }

    /**
     * this method removes a tweet or retweet from user's tweets or retweets
     */
    public void remove() {
        while (true) {
            System.out.println(usefulmethods.read_message(in));
            do {
                select = input.nextLine();
            } while (!select.equals("1") && !select.equals("2"));
            usefulmethods.send_message(out, select);
            if (select.equals("1")) {
                String select2 = usefulmethods.run_few_statement2(out, in, select, input);
                if (!(select2.equals("2")))
                    System.out.println(usefulmethods.read_message(in));
            } else break;
        }
    }

    /**
     * this method likes a tweet from a user's tweets
     */
    public void like() {
        while (true) {
            System.out.println(usefulmethods.read_message(in));
            do {
                select = input.nextLine();
            } while (!select.equals("1") && !select.equals("2"));
            usefulmethods.send_message(out, select);
            if (select.equals("1")) {
                String select2;
                select2 = usefulmethods.run_few_statement2(out, in, select, input);
                if (!(select2.equals("2"))) {
                    select2 = usefulmethods.run_few_statement2(out, in, select, input);
                    if (!(select2.equals("2"))) {
                        System.out.println(usefulmethods.read_message(in));
                    }
                }
            } else break;
        }
    }
}