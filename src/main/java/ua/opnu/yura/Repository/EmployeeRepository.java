package ua.opnu.yura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.yura.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
