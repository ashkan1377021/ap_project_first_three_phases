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
        User user1 = new User("hossein", "karimi", "hossein1998", "hossein231998", LocalDate.of(1998, 12, 28), LocalDate.now(), "Hello World");
        Tweet tweet1 = new Tweet(user1,"Today is Saturday",java.time.LocalDateTime.now());
        user1.getTweets().add(tweet1);
        User user2 = new User("ali", "karimi", "ali1998", "ali231998", LocalDate.of(1998, 12, 28), LocalDate.now(), "Hello World");
        users.add(user1);
        users.add(user2);
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
                int index = 1;
                //new TweetingService_impl(users,index,connectionSocket);
                //new ObserverService_impl(users,index,connectionSocket);

                Thread.sleep(5000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

               }
        }

