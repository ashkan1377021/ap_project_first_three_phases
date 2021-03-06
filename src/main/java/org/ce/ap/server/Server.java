package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.AuthenticationService_impl;
import main.java.org.ce.ap.server.impl.ObserverService_impl;
import main.java.org.ce.ap.server.impl.TimelineService_impl;
import main.java.org.ce.ap.server.impl.TweetingService_impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        File_utility file_utility = new File_utility();
        usefulMethods usefulmethods = new usefulMethods();
        ArrayList<User> users = file_utility.read_users();
        usefulmethods.sync(users);
        ExecutorService pool = Executors.newCachedThreadPool();
        int count = 0;
        try (ServerSocket welcomingSocket = new ServerSocket(7600);
             FileWriter fileWriter = new FileWriter("C:\\Users\\ashkan mogharab\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\log\\log.txt")) {
            System.out.println("Server started");
            file_utility.record_events("Server started");
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                System.out.println("client accepted!");
                file_utility.record_events("client accepted!");
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
            File_utility file_utility = new File_utility();
            String select;
            AuthenticationService_impl authenticationService_impl;
            authenticationService_impl = new AuthenticationService_impl(users, clientNum, connectionSocket);
            int index = authenticationService_impl.getJ();
            if (index >= 0)
                while (true) {
                    select = usefulmethods.read_message(in);
                    if (select.equals("1"))
                        new TweetingService_impl(users, index, connectionSocket);
                    else if (select.equals("2"))
                        new ObserverService_impl(users, index, connectionSocket);
                    else if (select.equals("3"))
                        new TimelineService_impl(users, index, connectionSocket);
                    else if (select.equals("4")) {
                        usefulmethods.showUsersDetails(users, index, out);
                    } else {
                        usefulmethods.send_message(out, "Goodbye.coming back soon");
                        System.out.println(users.get(index).getUsername() + " Quited");
                        file_utility.record_events(users.get(index).getUsername() + " Quited");
                        connectionSocket.close();
                        break;
                    }
                }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

