package main.java.org.ce.ap.client.impl;

import main.java.org.ce.ap.client.ClientAuthenticationService;
import main.java.org.ce.ap.server.usefulMethods;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * this class can sign up / sign in a person in tweeter
 *
 * @author ashkan_mogharab
 * @version version 1 of ClientAuthenticationService implementation
 */
public class ClientAuthenticationService_impl implements ClientAuthenticationService {
    // a socket
    Socket client;
    //an object of usefulMethods
    private usefulMethods usefulmethods;
    // an input stream
    private InputStream in;
    // an output stream
    private OutputStream out;
    // a scanner
    private Scanner input;
    // select of client
    private String select;

    /**
     * creates a new  client authentication service
     *
     * @param client a socket
     */
    public ClientAuthenticationService_impl(Socket client) {
        this.client = client;
        act();
    }

    /**
     * this method handles the works that should be done in ClientAuthentication Service
     */
    public void act() {
        try {
            usefulmethods = new usefulMethods();
            out = client.getOutputStream();
            in = client.getInputStream();
            input = new Scanner(System.in);
            System.out.println(usefulmethods.read_message(in));
            do {
                select = input.nextLine();
            } while (!select.equals("1") && !select.equals("2") && !select.equals("3"));
            usefulmethods.send_message(out, select);
            if (select.equals("3")) {
                System.out.println(usefulmethods.read_message(in));
                System.exit(0);
            } else if (select.equals("1"))
                signUp();

            else
                signIn();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }

    }

    /**
     * this method does sign up process
     */
    public void signUp() {
        usefulmethods.run_few_statement1(out, in, select, input);
        usefulmethods.run_few_statement1(out, in, select, input);
        usefulmethods.run_few_statement2(out, in, select, input);
        usefulmethods.run_few_statement1(out, in, select, input);
        usefulmethods.run_few_statement1(out, in, select, input);
        usefulmethods.run_few_statement2(out, in, select, input);
        usefulmethods.run_few_statement2(out, in, select, input);
        usefulmethods.run_few_statement2(out, in, select, input);
        System.out.println(usefulmethods.read_message(in));
    }

    /**
     * this method does sign in process
     */
    public void signIn() throws NoSuchAlgorithmException {
        usefulmethods.run_few_statement2(out, in, select, input);
        while (true) {
            System.out.println(usefulmethods.read_message(in));
            usefulmethods.send_message(out, usefulmethods.toHexString(usefulmethods.getSHA(input.nextLine())));
            if (usefulmethods.read_message(in).equals("true"))
                break;
            else {
                System.out.println(usefulmethods.read_message(in));
                select = usefulmethods.continue_or_not();
                usefulmethods.send_message(out, select);
                if (select.equals("2"))
                    act();
            }
        }
        System.out.println(usefulmethods.read_message(in));
    }
}