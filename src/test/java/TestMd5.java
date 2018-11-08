import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Created by Panda.HuangWei on 2017/5/4 0004.
 */
public class TestMd5 {

    public static void main(String[] args) {
        String passwd = new SimpleHash("SHA-1", "administrator", "123").toString();
        System.out.println(passwd);
    }
}
