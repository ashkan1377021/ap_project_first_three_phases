package main.java.org.ce.ap.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
/**
 * this class in Run method converts password to hexstring
 * @author ashkan_mogharab
 */
public class usefulMethods{
    public  byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    /**
     *
     * @param hash input of Run method with array of bytes form
     * @return hexstring of password
     */
    public  String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    /**
     * this method checks that person wants to continue attempting or back to main menu
     */
    public String continue_or_not(){
        String sel ="-1";
        while((!(sel .equals("1"))) && (!(sel.equals("2")))){
            Scanner input = new Scanner(System.in);
            sel = input.nextLine();
        }
        return sel;
    }

    /**
     * this method sends a message to its destination
     * @param out an output stream
     * @param string a message
     */
    public void send_message(OutputStream out , String string){
        try{
            out.write(string.getBytes());
        }
        catch(IOException ex){
            System.out.println(ex);
        }
    }

    /**
     * this method reads a message from its source
     * @param in an input stream
     * @return message
     */
    public String read_message(InputStream in){
        String message = null;
        try {
            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
             new String(buffer, 0, read);
             message = new String(buffer,0,read);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        return message;
    }
}
