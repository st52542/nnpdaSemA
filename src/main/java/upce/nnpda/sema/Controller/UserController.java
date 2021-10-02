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
import org.springframework.web.server.ResponseStatusException;
import upce.nnpda.sema.DTO.NewPasswordDTO;
import upce.nnpda.sema.DTO.ChangePasswordDTO;
import upce.nnpda.sema.DTO.ResetPasswordSendDTO;
import upce.nnpda.sema.DTO.ResetPasswordTokenDTO;
import upce.nnpda.sema.Entity.User;
import upce.nnpda.sema.Repository.UserRepository;
import upce.nnpda.sema.Security.jwt.JwtProvider;
import upce.nnpda.sema.Service.UserService;
import upce.nnpda.sema.DTO.LoginFormDTO;
import upce.nnpda.sema.DTO.SignUpFormDTO;
import upce.nnpda.sema.DTO.JwtResponseDTO;
import upce.nnpda.sema.DTO.ResponseMessageDTO;


import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginFormDTO loginRequest) {
        try {
            return userService.authenticateUser(loginRequest);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpFormDTO signUpRequest) throws AddressException {
        try {
            return userService.registerUser(signUpRequest);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/newpassword")
    public String newPassword(Authentication authentication, @RequestBody NewPasswordDTO newPasswordDTO) {
        try {
            userService.newPassword(authentication, newPasswordDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "Done";
    }
    @PostMapping("/changepassword")
    public String setPassword(Authentication authentication, @RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            userService.setPassword(authentication, changePasswordDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "Done";
    }

    @PostMapping("/resetpasswordsend")
    public String resetPasswordSend(@RequestBody ResetPasswordSendDTO resetPasswordSendDTO) {
        try {
            userService.resetPasswordSend(resetPasswordSendDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "mail send";
    }
    @PostMapping("/resetpasswordtoken")
    public String resetPasswordToken(@RequestBody ResetPasswordTokenDTO resetPasswordTokenDTO) throws Exception {
        try {
            userService.resetPasswordToken(resetPasswordTokenDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "password change";
    }
}
