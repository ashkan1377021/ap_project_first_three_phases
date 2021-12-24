package main.java.org.ce.ap.server;
import main.java.org.ce.ap.server.impl.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
import java.time.*;
public class Server {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ExecutorService pool = Executors.newCachedThreadPool();
        int count = 0 ;
        try (ServerSocket welcomingSocket = new ServerSocket(7600)) {
            System.out.println("Server started");
            while (count<3) {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                count++;
                pool.execute(new ClientHandler(connectionSocket, users, count));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
    class ClientHandler implements Runnable {
           Socket connectionSocket;
           ArrayList<User> users;
          int clientNum;

        public ClientHandler(Socket connectionSocket, ArrayList<User> users, int clientNum) {
            this.connectionSocket = connectionSocket;
            this.users = users;
            this.clientNum = clientNum;
        }

        @Override
        public void run() {
            try {
                //AuthenticationService_impl authenticationService_impl;
                //authenticationService_impl = new AuthenticationService_impl(users, clientNum, connectionSocket);
               //int index = authenticationService_impl.getJ();
                int index = 2;
                //new TweetingService_impl(users,index,connectionSocket);
                //new ObserverService_impl(users,index,connectionSocket);
                new TimelineService_impl(users.get(index),connectionSocket);
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

               }
        }

