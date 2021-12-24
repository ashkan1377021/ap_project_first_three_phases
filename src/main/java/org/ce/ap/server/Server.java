package main.java.org.ce.ap.server;
import main.java.org.ce.ap.server.impl.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
public class Server {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ExecutorService pool = Executors.newCachedThreadPool();
        int count = 0 ;
        try (ServerSocket welcomingSocket = new ServerSocket(7600)) {
            System.out.println("Server started");
            while (count<5) {
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
                InputStream in = connectionSocket.getInputStream();
                OutputStream out = connectionSocket.getOutputStream();
                usefulMethods usefulmethods = new usefulMethods();
                String select;
                AuthenticationService_impl authenticationService_impl;
                authenticationService_impl = new AuthenticationService_impl(users, clientNum, connectionSocket);
                int index = authenticationService_impl.getJ();
                if(index >= 0)
                    while (true) {
                        select = usefulmethods.read_message(in);
                        if (select.equals("1"))
                            new TweetingService_impl(users,index,connectionSocket);
                       else if(select.equals("2"))
                            new ObserverService_impl(users,index,connectionSocket);
                       else if(select.equals("3"))
                             new TimelineService_impl(users.get(index),connectionSocket);
                       else if(select.equals("4")){
                            usefulmethods.showUsersDetails(users,index,out);
                        }
                       else{
                            usefulmethods.send_message(out,"Goodbye.coming back soon");
                            System.out.println( users.get(index).getUsername()+ " Quited");
                            connectionSocket.close();
                            break;
                        }
                    }
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
               }
        }

