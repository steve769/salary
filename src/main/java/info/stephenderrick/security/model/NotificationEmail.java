package info.stephenderrick.security.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    @Value("${spring.mail.username}")
    private String from;
    private String subject;
    private String recipient;
    private String body;
}
