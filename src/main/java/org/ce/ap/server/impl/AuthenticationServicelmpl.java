package main.java.org.ce.ap.server.impl;
import main.java.org.ce.ap.server.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class can sign up persons in tweeter and also can allow to previous users to sign in tweeter
 * @author ashkan_mogharab
 * @version version 1 of AuthenticationService implementation
 */
public class AuthenticationServicelmpl implements AuthenticationService {
    //an object of usefulMethods
    private usefulMethods usefulmethods = new usefulMethods();
    //index of user who signs in or signs up
    private int j;
    // select of the person
    private int select;
    //users of ServerSide
    private ArrayList<User> users;

    /**
     * creates a new  authentication service
     */
    public AuthenticationServicelmpl(ArrayList<User> users) {
        this.users = users;
        act();
    }

    /**
     * this method handles the works that should be done in authentication service
     */
    private void act() {
        System.out.println("Welcome to Tweeter" + "\n" + "1:sign up" + '\n' + "2:Sign in" + '\n' + "3:Quit");
        while (true) {
            Scanner input = new Scanner(System.in);
            select = input.nextInt();
            if (select == 1 || select == 2 || select == 3)
                break;
        }
        if (select == 1)
            signUp();
        else if (select == 2)
            signIn();
        else
            System.exit(0);
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
        System.out.println("sign up form :");
        Scanner input = new Scanner(System.in);
        System.out.println("first name :");
        firstname = input.nextLine();
        System.out.println("last name :");
        lastname = input.nextLine();
        while (true) {
            System.out.println("username :");
            username = input.nextLine();
            if (username_is_valid(username))
                break;
            System.out.println("This username is duplicate" + "\n" + "1:continue attempting" + "\n" + "2: back");
            int se = usefulmethods.continue_or_not();
            if (se == 2)
                act();
        }
        System.out.println("password :");
        password = input.nextLine();
        System.out.println("birthdate :");
        int year = 0;
        int month = 0;
        int day = 0;
        int flag = 0;
        System.out.println("year :");
        year = input.nextInt();
        while (flag == 0) {
            System.out.println("month:");
            month = input.nextInt();
            if (month > 12) {
                System.out.println("invalid month" + "\n" + "1:continue attempting" + "\n" + "2: back");
                int se = usefulmethods.continue_or_not();
                if (se == 2)
                    act();
            } else
                flag = 1;
        }
        flag = 0;
        while (flag == 0) {
            System.out.println("day:");
            day = input.nextInt();
            if (day > 30) {
                System.out.println("invalid day" + "\n" + "1:continue attempting" + "\n" + "2: back");
                int se = usefulmethods.continue_or_not();
                if (se == 2)
                    act();
            } else
                flag = 1;
        }
        birthDate = LocalDate.of(year, month, day);
        while (true) {
            System.out.println("Bio:");
            input.nextLine();
            bio = input.nextLine();
            if (bio.length() <= 256)
                break;
            System.out.println("This String is very long .maximum valid length is 256");
            System.out.println("1:continue attempting" + "\n" + "2: back");
            int se = usefulmethods.continue_or_not();
            if (se == 2)
                act();
        }
        User user = new User(firstname, lastname, username, password, birthDate, LocalDate.now(), bio);
        users.add(user);
        System.out.println("Hi " + user.getFirstname() + ".  welcome to your account");
        j = (users.size() - 1);
    }

    /**
     * this method does sign in process
     */
    private void signIn() {
        String username;
        String password;
        int flag = 0;
        usefulMethods usefulmethods = new usefulMethods();
        Scanner input = new Scanner(System.in);
        while (flag == 0) {
            System.out.println("username: ");
            username = input.nextLine();
            int i;
            for (i = 0; i < users.size(); i++)
                if (users.get(i).getUsername().equals(username)) {
                    flag = 1;
                    break;
                }
            if (flag == 1) {
                int flg = 0;
                try {
                    while (flg == 0) {
                        System.out.println("password :");
                        password = usefulmethods.toHexString(usefulmethods.getSHA(input.nextLine()));
                        if (password.equals(users.get(i).getPassword())) {
                            System.out.println("Hi " + users.get(i).getFirstname() + ". welcome to your account");
                            flg = 1;
                            j = i;
                        } else {
                            System.out.println("incorrect password!");
                            System.out.println("1:continue attempting" + "\n" + "2: back");
                            int se = usefulmethods.continue_or_not();
                            if (se == 2)
                                act();
                        }

                    }
                }
                // For specifying wrong message digest algorithms
                catch (NoSuchAlgorithmException e) {
                    System.out.println("Exception thrown for incorrect algorithm: " + e);
                }
            } else {
                System.out.println("There is no user with this username");
                System.out.println("1:continue attempting" + "\n" + "2: back");
                int se = usefulmethods.continue_or_not();
                if (se == 2)
                    act();
            }
        }
    }

    /**
     * this method checks that the username which person inserted is duplicate or not
     *
     * @param username username which is checked that is duplicate or not
     * @return true if it not be duplicate otherwise false
     */
    private boolean username_is_valid(String username) {
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUsername().equals(username))
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
