package main.java.org.ce.ap.client;

import java.security.NoSuchAlgorithmException;

public interface ClientAuthenticationService {
    /**
     * this method handles the works that should be done in ClientAuthentication Service
     */
    void act();

    /**
     * this method does sign up process
     */
    void signUp();

    /**
     * this method does sign in process
     */
    void signIn() throws NoSuchAlgorithmException;
}
