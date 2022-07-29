package info.stephenderrick.security.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    private int numberOfEmployees;
    private String economicSector;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Role rolesOffered;
//    @OneToOne
//    private User user;
}
