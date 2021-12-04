import java.time.LocalDate;
import java.time.LocalDate.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// Java program to calculate SHA hash value
/**
 * this class holds details of an user in ServerSide
 * @author ashkan_mogharab
 */
public class User {
    //firstname of the user
    private String firstname;
    //lastname of the user
    private String lastname;
    //username of the user that should be different from others
    private String username ;
    //password of the user that it should be in hash form
    private String password;
    //birthDate of the user
    private LocalDate birthDate;
    //registeryDate of the user
    private LocalDate registeryDate;
    //bio of the user that its  maximum length is 256
    private String bio ;

    /**
     *
     * @param firstname firstname of the user
     * @param lastname  lastname of the user
     * @param username username of the user
     * @param password password of the user
     * @param birthDate birthDate of the user
     * @param registeryDate registeryDate of the user
     * @param bio bio of the user
     */
    public User(String firstname, String lastname, String username, String password, LocalDate birthDate, LocalDate registeryDate, String bio) {
        try {
            this.firstname = firstname;
            this.lastname = lastname;
            this.username = username;
            this.password = toHexString(getSHA(password));
            this.birthDate = birthDate;
            this.registeryDate = registeryDate;
            this.bio = bio;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
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
     * getter
     * @return firstname of the user
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     getter
     * @return lastname of the user
     */
    public String getLastname() {
        return lastname;
    }
    /**
     getter
     * @return username of the user
     */
    public String getUsername() {
        return username;
    }
    /**
     getter
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }
    /**
     getter
     * @return birthDate of the user
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }
    /**
     getter
     * @return registeryDate of the user
     */
    public LocalDate getRegisteryDate() {
        return registeryDate;
    }
    /**
     getter
     * @return bio of the user
     */
    public String getBio() {
        return bio;
    }

    /**
     * setter
     * @param firstname firstname of the user
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * setter
     * @param lastname lastname of the user
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     * setter
     * @param username  username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * setter
     * @param password  password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * setter
     * @param birthDate birthDate of the user
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * setter
     * @param registeryDate registeryDate of the user
     */
    public void setRegisteryDate(LocalDate registeryDate) {
        this.registeryDate = registeryDate;
    }
    /**
     * setter
     * @param bio bio of the user
     */
    public void setBio(String bio) {
        this.bio = bio;
    }
    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", registeryDate=" + registeryDate +
                ", bio='" + bio + '\'' +
                '}';
    }
}
