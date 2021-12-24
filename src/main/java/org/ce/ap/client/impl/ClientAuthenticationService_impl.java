package main.java.org.ce.ap.client.impl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import main.java.org.ce.ap.client.ClientAuthenticationService;
import main.java.org.ce.ap.server.usefulMethods;
import java.security.NoSuchAlgorithmException;
/**
 * this class can sign up / sign in a person in tweeter
 * @author ashkan_mogharab
 * @version version 1 of ClientAuthenticationService implementation
 */
public class ClientAuthenticationService_impl implements ClientAuthenticationService {
    //an object of usefulMethods
    private usefulMethods usefulmethods;
    // an input stream
    private InputStream in ;
    // an output stream
    private OutputStream out ;
    // a scanner
    private Scanner input;
    // select of client
    private String select;
    /**
     * creates a new  client authentication service
     */
    public ClientAuthenticationService_impl(){
        act();
    }
   private void act() {
         usefulmethods = new usefulMethods();
        try (Socket client = new Socket("127.0.0.1", 7600)) {
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
                }
                else if (select.equals("1"))
                    signUp();

                else
                    signIn();
            }catch (IOException ex) {
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
    private void signUp(){
        usefulmethods.run_few_statement1(out,in,select,input);
        usefulmethods.run_few_statement1(out,in,select,input);
        usefulmethods.run_few_statement2(out,in,select,input);
        usefulmethods.run_few_statement1(out,in,select,input);
        usefulmethods.run_few_statement1(out,in,select,input);
        usefulmethods.run_few_statement2(out,in,select,input);
        usefulmethods.run_few_statement2(out,in,select,input);
        usefulmethods.run_few_statement2(out,in,select,input);
        System.out.println(usefulmethods.read_message(in));
    }
    private void signIn() throws NoSuchAlgorithmException {
        usefulmethods.run_few_statement2(out,in,select,input);
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