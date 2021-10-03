package upce.nnpda.sema.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import upce.nnpda.sema.DTO.*;
import upce.nnpda.sema.Entity.User;
import upce.nnpda.sema.Repository.UserRepository;
import upce.nnpda.sema.Security.jwt.JwtProvider;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    String SERVERMAIL = "";
    String PASS = "";
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService userService;
    private void sendMail(String mail, String uuid) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SERVERMAIL, PASS);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(SERVERMAIL, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject("Reset Password");
        msg.setContent("Token", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Token is: " + uuid, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

    @Override
    public void resetPasswordToken(ResetPasswordTokenDTO resetPasswordTokenDTO) throws Exception {
        Optional<User> user = userRepository.findByUuidAndUuidNotNull(resetPasswordTokenDTO.getToken());
        if (user.get().getUuid()!=null&& Objects.equals(user.get().getUuid(), resetPasswordTokenDTO.getToken())){
            user.get().setPassword(encoder.encode(resetPasswordTokenDTO.getPass()));
            user.get().setUuid(null);
            userRepository.save(user.get());
        }else {
            throw new Exception();
        }
    }
    @Override
    public void resetPasswordSend(ResetPasswordSendDTO resetPasswordSendDTO) throws MessagingException {
        Optional<User> user = userRepository.findByEmail(resetPasswordSendDTO.getMail());
        UUID uuid = UUID.randomUUID();
        user.get().setUuid(uuid.toString());
        userRepository.save(user.get());
        sendMail(user.get().getEmail(), user.get().getUuid());
    }

    @Override
    public void setPassword(Authentication authentication, ChangePasswordDTO changePasswordDTO) throws Exception {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (encoder.matches(changePasswordDTO.getOldPassword(),user.get().getPassword())) {
            user.get().setPassword(encoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user.get());
        }else {
            throw new Exception();
        }
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginFormDTO loginRequest) {
        UsernamePasswordAuthenticationToken test = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(test);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpFormDTO signUpRequest) {
        try {
            InternetAddress emailAddress = new InternetAddress(signUpRequest.getEmail());
            emailAddress.validate();
            userRepository.existsByUsername(signUpRequest.getUsername());
            userRepository.existsByEmail(signUpRequest.getEmail());
        } catch (AddressException ex) {
            return new ResponseEntity<>(new ResponseMessageDTO("Fail -> Invalid email or password"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());
        user.setName(signUpRequest.getFirstname());
        user.setSurname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessageDTO("User "+ signUpRequest.getFirstname() + " is registered successfully!"), HttpStatus.OK);
    }
}
