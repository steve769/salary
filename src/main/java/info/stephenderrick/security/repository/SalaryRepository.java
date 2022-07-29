package info.stephenderrick.security.repository;

import info.stephenderrick.security.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
