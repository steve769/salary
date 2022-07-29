package info.stephenderrick.security.service;


import info.stephenderrick.security.entity.User;
import info.stephenderrick.security.entity.VerificationToken;
import info.stephenderrick.security.exception.SalaryApplicationException;
import info.stephenderrick.security.mail.MailService;
import info.stephenderrick.security.model.NotificationEmail;
import info.stephenderrick.security.model.SignUpModel;
import info.stephenderrick.security.repository.UserRepository;
import info.stephenderrick.security.repository.VerificationTokenRepository;
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
    private final int TOKEN_EXPIRY_TIME = 10; //Set token to expire after 10 minutes
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           MailService mailService) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;

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
        mailService.sendMail(new NotificationEmail("Verify Your Email",user.getEmail(),
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
