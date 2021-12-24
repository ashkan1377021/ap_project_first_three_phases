package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.impl.ClientAuthenticationService_impl;
import main.java.org.ce.ap.client.impl.ClientObserverService_impl;
import main.java.org.ce.ap.client.impl.ClientTweetingService_impl;

public class Client {
    public static void main(String[] args) {
        //new ClientAuthenticationService_impl();
        //new ClientTweetingService_impl();
        new ClientObserverService_impl();
    }
}
