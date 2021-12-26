package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * this class can sign up persons in tweeter and also can allow to previous users to sign in tweeter
 * @author ashkan_mogharab
 * @version version 1 of AuthenticationService implementation
 */

    public class AuthenticationService_impl implements AuthenticationService {
        // an object of File_utility
        File_utility file_utility = new File_utility();
        //a socket for connecting  to user
        private final Socket connectionSocket;
        // an input stream
        private InputStream in ;
        // an output stream
        private OutputStream out ;
        //client number
    private final int clientNum;
    //an object of usefulMethods
    private final usefulMethods usefulmethods = new usefulMethods();
    //index of user who signs in or signs up
    private int j = -1;
    //users of ServerSide
    private final ArrayList<User> users;

    /**
     * creates a new  authentication service
     * @param users  users of ServerSide
     * @param clientNum number of client
     * @param connectionSocket  a socket for connecting  to user
     */
    public AuthenticationService_impl(ArrayList<User> users ,int clientNum ,Socket connectionSocket) {
        this.users = users;
        this.clientNum = clientNum;
        this.connectionSocket = connectionSocket;
                act();
    }

    /**
     * this method handles the works that should be done in authentication service
     */
    private void act() {
        String msg = "Welcome to Tweeter" + "\n" + "1:sign up" + '\n' + "2:Sign in" + '\n' + "3:Quit";
        try {
            out = connectionSocket.getOutputStream();
            in = connectionSocket.getInputStream();
            usefulmethods.send_message(out,msg);
            System.out.println("welcoming message sent to client" + clientNum);
            file_utility.record_events("welcoming message sent to client" + clientNum);
            System.out.println("receive from client" + clientNum +" : " +(msg = usefulmethods.read_message(in)));
            file_utility.record_events("receive from client" + clientNum +" : " +msg);
            if(msg.equals("1"))
                signUp();
            else
                if(msg.equals("2"))
                    signIn();
            else{
                usefulmethods.send_message(out,"Goodbye.coming back soon");
                System.out.println("client" + clientNum + " Quited");
                file_utility.record_events("client" + clientNum + " Quited");
                connectionSocket.close();

            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    /**
     * this method does sign up process
     */
    private void signUp() {
        String firstname;
        String lastname;
        String username;
        String password;
        LocalDate birthDate;
        String bio;
        System.out.println("process of client" +clientNum +" sign up started");
        file_utility.record_events("process of client" +clientNum +" sign up started");
        String msg = "sign up form :" +'\n' +"first name :";
            usefulmethods.send_message(out, msg);
            firstname = usefulmethods.read_message(in);
            System.out.println("first name of client" + clientNum + " is:" + firstname);
            file_utility.record_events("first name of client" + clientNum + " is:" + firstname);
            usefulmethods.send_message(out, "last name :");
            lastname = usefulmethods.read_message(in);
            System.out.println("last name of client" + clientNum + " is:" + lastname);
        file_utility.record_events("last name of client" + clientNum + " is:" + lastname);
            while (true) {
                usefulmethods.send_message(out,"username :");
                username = usefulmethods.read_message(in);
                if (username_is_valid(username)) {
                    usefulmethods.send_message(out,"true");
                    System.out.println("username of client" + clientNum + " is:" + username);
                    file_utility.record_events("username of client" + clientNum + " is:" + username);
                    break;
                }
                usefulmethods.send_message(out,"false");
                System.out.println("The username that client" +clientNum +" entered is duplicate.we requested from it to choose continue attempting or back");
                file_utility.record_events("The username that client" +clientNum +" entered is duplicate.we requested from it to choose continue attempting or back");
                usefulmethods.send_message(out,("This username is duplicate" + "\n" + "1:continue attempting" + "\n" + "2: back"));

                if ( usefulmethods.read_message(in) .equals("2")) {
                    System.out.println("client" + clientNum + "chose to back");
                    file_utility.record_events("client" + clientNum + "chose to back");
                    act();
                }
                else {
                    System.out.println("client" + clientNum + " chose to continue attempting");
                    file_utility.record_events("client" + clientNum + " chose to continue attempting");
                }
            }
            usefulmethods.send_message(out,"password :");
            password = usefulmethods.read_message(in);
        System.out.println("password of client" + clientNum + " is:" + password);
        file_utility.record_events("password of client" + clientNum + " is:" + password);
            usefulmethods.send_message(out,("birthdate :" +'\n' +"year :"));
            int year = 0;
            int month = 0;
            int day = 0;
            int flag = 0;
            year = Integer.parseInt(usefulmethods.read_message(in));
            System.out.println("birth_year of client" +clientNum +" is:" +year);
            file_utility.record_events("birth_year of client" +clientNum +" is:" +year);
            while (flag == 0) {
                usefulmethods.send_message(out,"month :");
                month = Integer.parseInt(usefulmethods.read_message(in));
                if (month > 12) {
                    usefulmethods.send_message(out,"false");
                    System.out.println("The month that client" +clientNum +" entered is invalid.we requested from it to choose continue attempting or back");
                    file_utility.record_events("The month that client" +clientNum +" entered is invalid.we requested from it to choose continue attempting or back");
                    usefulmethods.send_message(out,("invalid month" + "\n" + "1:continue attempting" + "\n" + "2: back"));
                    if ( usefulmethods.read_message(in) .equals("2")) {
                        System.out.println("client" + clientNum + " chose to back");
                        file_utility.record_events("client" + clientNum + " chose to back");
                        act();
                    }
                    else {
                        System.out.println("client" + clientNum + " chose to continue attempting");
                        file_utility.record_events("client" + clientNum + " chose to continue attempting");
                    }

                } else {
                    flag = 1;
                    usefulmethods.send_message(out,"true");
                    System.out.println("birth_month of client" + clientNum + " is:" + month);
                    file_utility.record_events("birth_month of client" + clientNum + " is:" + month);
                }
            }
            flag = 0;
            while (flag == 0) {
                usefulmethods.send_message(out,"day :");
                day = Integer.parseInt(usefulmethods.read_message(in));
                if(day > 30){
                    usefulmethods.send_message(out,"false");
                    System.out.println("The day that client" +clientNum +" entered is invalid.we requested from it to choose continue attempting or back");
                    file_utility.record_events("The day that client" +clientNum +" entered is invalid.we requested from it to choose continue attempting or back");
                    usefulmethods.send_message(out,("invalid day" + "\n" + "1:continue attempting" + "\n" + "2: back"));
                    if ( usefulmethods.read_message(in) .equals("2")) {
                        System.out.println("client" + clientNum + " chose to back");
                        file_utility.record_events("client" + clientNum + " chose to back");
                        act();
                    }
                    else {
                        System.out.println("client" + clientNum + " chose to continue attempting");
                        file_utility.record_events("client" + clientNum + " chose to continue attempting");
                    }

                } else {
                    flag = 1;
                    usefulmethods.send_message(out,"true");
                    System.out.println("birthday of client" + clientNum + " is:" + day);
                    file_utility.record_events("birthday of client" + clientNum + " is:" + day);
                }
            }
            birthDate = LocalDate.of(year, month, day);
            while (true) {
                usefulmethods.send_message(out,"Bio :");
                bio = usefulmethods.read_message(in);
                if (bio.length() <= 256) {
                    usefulmethods.send_message(out,"true");
                    System.out.println("Bio of client" + clientNum + " is: " +bio);
                    file_utility.record_events("Bio of client" + clientNum + " is: " +bio);
                    break;
                }
                usefulmethods.send_message(out,"false");
                System.out.println("The String that client"+ clientNum + " entered is very long .maximum valid length is 256. we requested from it to choose continue attempting or back");
               file_utility.record_events("The String that client"+ clientNum + " entered is very long .maximum valid length is 256. we requested from it to choose continue attempting or back");
                usefulmethods.send_message(out,("long String(maximum length is 256)" + "\n" + "1:continue attempting" + "\n" + "2: back"));
                if ( usefulmethods.read_message(in) .equals("2")) {
                    System.out.println("client" + clientNum + " chose to back");
                    file_utility.record_events("client" + clientNum + " chose to back");
                    act();
                }
                else {
                    System.out.println("client" + clientNum + " chose to continue attempting");
                    file_utility.record_events("client" + clientNum + " chose to continue attempting");
                }
            }
            User user = new User(firstname, lastname, username, password, birthDate, LocalDate.now(), bio);
            file_utility.add_user(user);
            users.add(user);
            usefulmethods.send_message(out,("Hi " + user.getFirstname() + ".  welcome to your account"));
            j = (users.size() - 1);
            System.out.println(user.getUsername() + " got " + (j +1) + "th user between users");
            file_utility.record_events(user.getUsername() + " got " + (j +1) + "th user between users");
    }

    /**
     * this method does sign in process
     */
    private void signIn() {
        String username;
        String password;
        int flag = 0;
        usefulMethods usefulmethods = new usefulMethods();
        System.out.println("process of client" +clientNum +" sign in started");
        file_utility.record_events("process of client" +clientNum +" sign in started");
        while (flag == 0) {
            usefulmethods.send_message(out,"username: ");
            username =usefulmethods.read_message(in);
            int i;
            for (i = 0; i < users.size(); i++)
                if (users.get(i).getUsername().equals(username)) {
                    flag = 1;
                    usefulmethods.send_message(out,"true");
                    System.out.println("The username that client" + clientNum + " entered (" + username +") was existed");
                   file_utility.record_events("The username that client" + clientNum + " entered (" + username +") was existed");
                    break;
                }
            if (flag == 1) {
                int flg = 0;
                    while (flg == 0) {
                       usefulmethods.send_message(out,"password :");
                        password = usefulmethods.read_message(in);
                        if (password.equals(users.get(i).getPassword())) {
                            usefulmethods.send_message(out,"true");
                            System.out.println("The password that client" + clientNum + " entered (" +password+ ") was correct and signed in its account");
                            file_utility.record_events("The password that client" + clientNum + " entered (" +password+ ") was correct and signed in its account");
                            usefulmethods.send_message(out,"Hi " + users.get(i).getFirstname() + ". welcome to your account");
                            flg = 1;
                            j = i;
                        } else {
                            usefulmethods.send_message(out,"false");
                            System.out.println("The password that client"+ clientNum + " entered (" + password +") was incorrect . we requested from it to choose continue attempting or back");
                            file_utility.record_events("The password that client"+ clientNum + " entered (" + password +") was incorrect . we requested from it to choose continue attempting or back");
                            usefulmethods.send_message(out,"incorrect password!"+ "\n" + "1:continue attempting" + "\n" + "2: back");
                            if ( usefulmethods.read_message(in) .equals("2")) {
                                System.out.println("client" + clientNum + " chose to back");
                                file_utility.record_events("client" + clientNum + " chose to back");
                                act();
                            }
                            else {
                                System.out.println("client" + clientNum + " chose to continue attempting");
                                file_utility.record_events("client" + clientNum + " chose to continue attempting");
                            }
                        }
                }
            } else {
                usefulmethods.send_message(out,"false");
                System.out.println("The username that client"+ clientNum + " entered (" +username + ") was not in system . we requested from it to choose continue attempting or back");
                file_utility.record_events("The username that client"+ clientNum + " entered (" +username + ") was not in system . we requested from it to choose continue attempting or back");
                usefulmethods.send_message(out,"There is no user with this username"+ "\n" + "1:continue attempting" + "\n" + "2: back");
                if ( usefulmethods.read_message(in) .equals("2")) {
                    System.out.println("client" + clientNum + " chose to back");
                    file_utility.record_events("client" + clientNum + " chose to back");
                    act();
                }
                else {
                    System.out.println("client" + clientNum + " chose to continue attempting");
                    file_utility.record_events("client" + clientNum + " chose to continue attempting");
                }
            }
        }
    }

    /**
     * this method checks that the username which person inserted is duplicate or not
     * @param username username which is checked that is duplicate or not
     * @return true if it not be duplicate otherwise false
     */
    private boolean username_is_valid(String username) {
        for (User user : users)
            if (user.getUsername().equals(username))
                return false;
        return true;
    }

    /**
     * getter
     * @return index of user who signs in or signs up
     */
    public int getJ() {
        return j;
    }
}
