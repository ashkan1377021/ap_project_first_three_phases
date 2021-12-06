import java.time.*;
import java.util.*;
/**
 * this class holds details of a ServerSide
 * @author ashkan_mogharab
 */
public class ServerSide {
    public static void main(String[] args) {
        //users of ServerSide
       ArrayList<User> users = new ArrayList<>();
        //index of user who signs in or signs up
        int index ;
       User user1 = new User("ashkan", "mogharab", "ashkan1998", "ashkan231998", LocalDate.of(1998, 12, 28), LocalDate.now(), "deymahi");
       users.add(user1);
       AuthenticationService authenticationService = new AuthenticationService(users);
        index = authenticationService.getJ();






    }
}
