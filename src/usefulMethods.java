import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

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
   public int continue_or_not(){
        int sel = 0;
        while(sel != 1 && sel != 2){
            Scanner input = new Scanner(System.in);
            sel = input.nextInt();
        }
        return sel;
    }

}