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
        User user1 = new User("ashkan", "mogharab", "ashkan1998", "ashkan231998", LocalDate.of(1998, 12, 28), LocalDate.now(), "deymahi");
        users.add(user1);
        ExecutorService pool = Executors.newCachedThreadPool();
        int count = 0 ;
        try (ServerSocket welcomingSocket = new ServerSocket(7600)) {
            System.out.println("Server started");
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                count++;
                pool.execute(new ClientHandler(connectionSocket, users, count));
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
    class ClientHandler implements Runnable {
        private Socket connectionSocket;
        private ArrayList<User> users;
        private int clientNum;

        public ClientHandler(Socket connectionSocket, ArrayList<User> users, int clientNum) {
            this.connectionSocket = connectionSocket;
            this.users = users;
            this.clientNum = clientNum;
        }

        @Override
        public void run() {
            try {
                AuthenticationServicelmpl authenticationService;
                TweetingServicelmpl tweetingService;
                ObserverServicelmpl observerService;
                TimelineServicelmpl timelineService;

                authenticationService = new AuthenticationServicelmpl(users, clientNum, connectionSocket);
                Thread.sleep(5000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

               }
        }

