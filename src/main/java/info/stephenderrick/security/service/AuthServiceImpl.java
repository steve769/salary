package info.stephenderrick.security.service;


import info.stephenderrick.security.entity.User;
import info.stephenderrick.security.entity.VerificationToken;
import info.stephenderrick.security.exception.SalaryApplicationException;
import info.stephenderrick.security.jwt.JwtProvider;
import info.stephenderrick.security.mail.MailService;
import info.stephenderrick.security.model.AuthenticationResponse;
import info.stephenderrick.security.model.LoginRequest;
import info.stephenderrick.security.model.NotificationEmail;
import info.stephenderrick.security.model.SignUpModel;
import info.stephenderrick.security.repository.UserRepository;
import info.stephenderrick.security.repository.VerificationTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final int TOKEN_EXPIRY_TIME = 10; //Set token to expire after 10 minutes

    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           MailService mailService,
                           AuthenticationManager authenticationManager, JwtProvider jwtProvider) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    @Transactional
    public void signup(SignUpModel signUpModel) {
        User user = new User();

        user.setEmail(signUpModel.getEmail());
        user.setPassword(passwordEncoder.encode(signUpModel.getPassword()));
        user.setEnabled(false);

        String token = generateVerificationToken(user);
        userRepository.save(user);
        //Send verification email to user
        mailService.sendMail(new NotificationEmail("stephenderrick98@gmail.com","Verify Your Email",user.getEmail(),
                "Please click the link below to verify your email "+
                "http://localhost:8080/api/auth/verifyRegistration/" + token));
    }

    @Override
    public void verifyRegistration(String token) {

        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SalaryApplicationException("Token invalid"));

        Optional<User> userOpt = userRepository.findById(verificationToken.get().getUser().getUserId());
        userOpt.get().setEnabled(true);


        userRepository.save(userOpt.get());

        //After using the token for verification we need to delete it from database
        //TODO: Create delete method for token

    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticateObj = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticateObj);
        String jwtToken = jwtProvider.generateToken(authenticateObj);

        return new AuthenticationResponse(jwtToken, loginRequest.getEmail());

    }


    //method generateVerificationToken
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setCreatedAt(LocalDateTime.now());

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
