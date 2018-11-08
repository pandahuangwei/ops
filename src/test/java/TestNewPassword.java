import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author Administrator.
 * @since 2018-10-24 23:50.
 */
public class TestNewPassword {
    public static void main(String[] args) {
        String passwd = new SimpleHash("SHA-1", "admin", "1qaz2wsx").toString();
        System.out.println(passwd);
         passwd = new SimpleHash("SHA-1", "administrator", "1qaz2wsx3edc").toString();
        System.out.println(passwd);
    }
}
