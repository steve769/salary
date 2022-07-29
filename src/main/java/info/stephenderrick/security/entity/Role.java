package info.stephenderrick.security.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;

import java.util.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @NotBlank(message = "Please dd a job title")
    private String title;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
//    @JoinColumn(name = "salaryId", referencedColumnName = "salaryId")
//    private Set<Salary> salariesForThisRole = new HashSet<>();
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rolesOffered")
//    @JoinColumn(
//            name = "companyId", referencedColumnName = "companyId"
//    )
//    private Set<Company> companiesOfferingThisRole = new HashSet<>();
}
