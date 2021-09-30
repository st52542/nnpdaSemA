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
import upce.nnpda.sema.Entity.User;
import upce.nnpda.sema.Repository.UserRepository;
import upce.nnpda.sema.Security.jwt.JwtProvider;
import upce.nnpda.sema.message.request.LoginForm;
import upce.nnpda.sema.message.request.SignUpForm;
import upce.nnpda.sema.message.response.JwtResponse;
import upce.nnpda.sema.message.response.ResponseMessage;


import javax.validation.Valid;
import java.util.Optional;

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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
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

        return new ResponseEntity<>(new ResponseMessage("User "+ signUpRequest.getFirstname() + " is registered successfully!"), HttpStatus.OK);
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
}
