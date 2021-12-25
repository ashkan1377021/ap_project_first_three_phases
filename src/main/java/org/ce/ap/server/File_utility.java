package main.java.org.ce.ap.server;
import java.io.*;
import java.util.*;
/**
 * This class does things related to the file
 * @author ashkan
 */
public class File_utility {
    /**
     * this method reads users from database
     * @param users an ArrayList that will be fill
     */
    public void read_users(ArrayList<User>users) {
        try{
            FileReader fileReader= new FileReader("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\model\\users\\names.txt");
            Scanner sc = new Scanner(fileReader);
            while(sc.hasNext()){
                String st = sc.next();
                FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\model\\users\\" +st +".txt");
                ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream);
                users.add((User) objectInputStream.readObject());
                fileInputStream.close();
                objectInputStream.close();
            }
            sc.close();

        }
        catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    /**
     * this method adds a user to server's users
     * @param user a user
     */
    public void add_user(User user){
        String st = user.getUsername() ;
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\model\\users\\" +st +".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            FileWriter fileWriter = new FileWriter("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\model\\users\\names.txt",true)){
            st = st + "\n";
            fileWriter.write(st);
            objectOutputStream.writeObject(user);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    /**
     * this method edits information of a user
     * @param user a user
     */
    public void edit_user(User user){
        String st = user.getUsername();
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\model\\users\\" +st +".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    /**
     * this method record events in server
     */
    public void record_events(String st)  {
        try( FileWriter fileWriter = new FileWriter("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\log\\log.txt",true)){
           st = st + "\n";
            fileWriter.write(st);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    /**
     * this method create a tweet in C:\Users\ashkan 1377\IdeaProjects\AP-Project-ashkan_mogharab-9823081\Twitter\files\model\tweets
     * @param tweet a tweet which wants to be added
     */
    public void add_tweet(Tweet tweet){
        String st = tweet.getSender().getUsername() ;
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\model\\tweets\\" +st +".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(tweet);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * this method edits information of a tweet
     * @param tweet an edited tweet
     */
    public void edit_tweet(Tweet tweet){
        String st = tweet.getSender().getUsername();
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ashkan 1377\\IdeaProjects\\AP-Project-ashkan_mogharab-9823081\\Twitter\\files\\model\\tweets\\" +st +".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(tweet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
