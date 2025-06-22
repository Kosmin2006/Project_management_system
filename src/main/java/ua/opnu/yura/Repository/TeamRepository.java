package ua.opnu.yura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.yura.Model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
