package upce.nnpda.sema.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import upce.nnpda.sema.DTO.*;

import javax.mail.MessagingException;

public interface UserService {
    void resetPasswordToken(ResetPasswordTokenDTO resetPasswordTokenDTO) throws Exception;
    void resetPasswordSend(ResetPasswordSendDTO resetPasswordSendDTO) throws MessagingException;
    void setPassword(Authentication authentication, ChangePasswordDTO changePasswordDTO) throws Exception;
    void newPassword(Authentication authentication, NewPasswordDTO newPasswordDTO) throws Exception;
    ResponseEntity<?> authenticateUser(LoginFormDTO loginRequest);
    ResponseEntity<?> registerUser(SignUpFormDTO signUpRequest);
}
