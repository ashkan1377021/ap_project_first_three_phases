package main.java.org.ce.ap.client;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import main.java.org.ce.ap.server.usefulMethods;
import java.security.NoSuchAlgorithmException;
public class Client {
    public static void main(String[] args) {
        usefulMethods usefulmethods = new usefulMethods();
        String select;
        try (Socket client = new Socket("127.0.0.1", 7600)) {
            System.out.println("Connected to server.");
            OutputStream out = client.getOutputStream();
             InputStream in = client.getInputStream();
            Scanner input = new Scanner(System.in);
                System.out.println(usefulmethods.read_message(in));
                while (true) {
                    select = input.nextLine();
                    if (select.equals("1") || select.equals("2") || select.equals("3"))
                        break;
                }
                usefulmethods.send_message(out, select);
                if (select.equals("3")) {
                    System.out.println(usefulmethods.read_message(in));
                    System.exit(0);
                }
                if (select.equals("1")) {

                    run_afew_statement1(usefulmethods,out,in,select,input);
                    run_afew_statement1(usefulmethods,out,in,select,input);
                   run_afew_statement2(usefulmethods,out,in,select,input);
                    run_afew_statement1(usefulmethods,out,in,select,input);
                    run_afew_statement1(usefulmethods,out,in,select,input);
                    run_afew_statement2(usefulmethods,out,in,select,input);
                    run_afew_statement2(usefulmethods,out,in,select,input);
                    run_afew_statement2(usefulmethods,out,in,select,input);
                    System.out.println(usefulmethods.read_message(in));
                }
                if(select.equals("2")){
                    run_afew_statement2(usefulmethods,out,in,select,input);
                    while (true) {
                        System.out.println(usefulmethods.read_message(in));
                        usefulmethods.send_message(out, usefulmethods.toHexString(usefulmethods.getSHA(input.nextLine())));
                        if (usefulmethods.read_message(in).equals("true"))
                            break;
                        else {
                            System.out.println(usefulmethods.read_message(in));
                            select = usefulmethods.continue_or_not();
                            usefulmethods.send_message(out, select);
                            //should back to method act that will be write in future
                            if (select.equals("2")) ;

                        }
                    }
                    System.out.println(usefulmethods.read_message(in));
                }
            }catch (IOException ex) {
            System.err.println(ex);
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }

    }

    /**
     * run a few statement that are duplicate in main code
     */
     private static void run_afew_statement2(usefulMethods usefulmethods ,OutputStream out ,InputStream in ,String select , Scanner input){
        while (true) {
            System.out.println(usefulmethods.read_message(in));
            select = input.nextLine();
            usefulmethods.send_message(out, select);
            if (usefulmethods.read_message(in).equals("true"))
                break;
            else {
                System.out.println(usefulmethods.read_message(in));
                select = usefulmethods.continue_or_not();
                usefulmethods.send_message(out, select);
                //should back to method act that will be write in future
                if (select.equals("2")) ;

            }
        }
    }
    private static void run_afew_statement1(usefulMethods usefulmethods ,OutputStream out ,InputStream in ,String select , Scanner input){
        System.out.println(usefulmethods.read_message(in));
        select = input.nextLine();
        usefulmethods.send_message(out, select);
    }
}