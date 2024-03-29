package info.stephenderrick.security.controller;


import info.stephenderrick.security.model.AuthenticationResponse;
import info.stephenderrick.security.model.LoginRequest;
import info.stephenderrick.security.model.SignUpModel;
import info.stephenderrick.security.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    //Signup
    @PostMapping("signup/")
    public ResponseEntity<String> signUpToSalary(@RequestBody SignUpModel signUpModel){

        authService.signup(signUpModel);
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }
    //VerifyRegistration
    @GetMapping("verifyRegistration/{token}")
    public ResponseEntity<String> verifyRegistration(@PathVariable("token") String token){
        authService.verifyRegistration(token);
        return new ResponseEntity<String>("Account verified successfully", HttpStatus.OK);
    }
    //Login
    @PostMapping("login/")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

}
