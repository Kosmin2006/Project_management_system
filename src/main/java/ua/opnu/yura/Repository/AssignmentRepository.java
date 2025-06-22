package ua.opnu.yura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.yura.Model.Assignment;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // Отримати всі призначення працівника
    List<Assignment> findByEmployeeId(Long employeeId);

    // Отримати всі призначення проєкту
    List<Assignment> findByProjectId(Long projectId);
}
