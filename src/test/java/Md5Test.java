import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;


public class Md5Test {
	public static void main(String[] args) {
		PasswordEncoder encoder=new Md5PasswordEncoder();
		System.out.println(encoder.encodePassword("123456", null));
		
	}
}
