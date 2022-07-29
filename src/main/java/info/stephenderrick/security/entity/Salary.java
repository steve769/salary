package info.stephenderrick.security.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryId;
    @NotBlank(message = "Please enter a value for the salary amount")
    private Integer salaryAmount;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Role role;

}
