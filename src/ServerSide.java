import java.time.*;
/**
 * this class holds details of a ServerSide
 * @author ashkan_mogharab
 */
public class ServerSide{
    public static void main(String[]args){
        User user1 = new User("ashkan","mogharab","ashkan1377","ashkan13771998",LocalDate.of(1377,10,7),LocalDate.of(1400,9,14),"deymahi");
        Tweet tweet1 = new Tweet(user1,"Today,it is rainy",LocalDate.of(1400,9,14));
        System.out.print(tweet1.toString());
    }
}
