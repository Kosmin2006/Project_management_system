package ua.opnu.yura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.yura.Model.Employee;
import ua.opnu.yura.Model.Project;
import ua.opnu.yura.Model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);
    List<Task> findByAssignee(Employee employee);
    List<Task> findByStatus(String status);
    Long countByProject(Project project);
    Long countByAssignee(Employee employee);
    Long countByAssigneeAndStatus(Employee employee, String status);

}
