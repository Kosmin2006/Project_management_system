package ua.opnu.yura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.yura.Model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
