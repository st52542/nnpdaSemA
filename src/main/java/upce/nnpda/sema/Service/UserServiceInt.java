package upce.nnpda.sema.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

public interface UserServiceInt {
    void sendMail(String mail, String uuid) throws AddressException, MessagingException, IOException;
}
