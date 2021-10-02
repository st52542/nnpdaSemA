package upce.nnpda.sema.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import upce.nnpda.sema.DTO.NewPasswordDTO;
import upce.nnpda.sema.DTO.ChangePasswordDTO;
import upce.nnpda.sema.DTO.ResetPasswordSendDTO;
import upce.nnpda.sema.DTO.ResetPasswordTokenDTO;
import upce.nnpda.sema.Entity.User;
import upce.nnpda.sema.Repository.UserRepository;
import upce.nnpda.sema.Security.jwt.JwtProvider;
import upce.nnpda.sema.Service.UserServiceInt;
import upce.nnpda.sema.DTO.LoginFormDTO;
import upce.nnpda.sema.DTO.SignUpFormDTO;
import upce.nnpda.sema.DTO.JwtResponseDTO;
import upce.nnpda.sema.DTO.ResponseMessageDTO;


import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserServiceInt userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginFormDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpFormDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessageDTO("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessageDTO("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(),
                signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        user.setUsername(signUpRequest.getUsername());
        user.setName(signUpRequest.getFirstname());
        user.setSurname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessageDTO("User "+ signUpRequest.getFirstname() + " is registered successfully!"), HttpStatus.OK);
    }

    @PostMapping("/newpassword")
    public String newPassword(Authentication authentication, @RequestBody NewPasswordDTO newPasswordDTO) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user!=null) {
            user.get().setPassword(encoder.encode(newPasswordDTO.getPassword()));
            userRepository.save(user.get());
            return "OK";
        }
        return null;
    }
    @PostMapping("/changepassword")
    public String setPassword(Authentication authentication, @RequestBody ChangePasswordDTO changePasswordDTO) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (encoder.matches(changePasswordDTO.getOldPassword(),user.get().getPassword())) {
            user.get().setPassword(encoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user.get());
            return "OK";
        }
        return null;
    }

    @PostMapping("/resetpasswordsend")
    public String resetPasswordSend(@RequestBody ResetPasswordSendDTO resetPasswordSendDTO) throws IOException, MessagingException {
        User user = userRepository.findByEmail(resetPasswordSendDTO.getMail());
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid.toString());
        userRepository.save(user);
        userService.sendMail(user.getEmail(),user.getUuid());
        return "mail send";
    }
    @PostMapping("/resetpasswordtoken")
    public String resetPasswordToken(@RequestBody ResetPasswordTokenDTO resetPasswordTokenDTO) throws IOException, MessagingException {
        User user = userRepository.findByUuid(resetPasswordTokenDTO.getToken());
        user.setPassword(encoder.encode(resetPasswordTokenDTO.getPass()));
        userRepository.save(user);
        return "password change";
    }
}
