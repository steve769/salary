package info.stephenderrick.security.service;


import info.stephenderrick.security.model.AuthenticationResponse;
import info.stephenderrick.security.model.LoginRequest;
import info.stephenderrick.security.model.SignUpModel;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void signup(SignUpModel signUpModel);

    void verifyRegistration(String token);

    AuthenticationResponse login(LoginRequest loginRequest);
}
