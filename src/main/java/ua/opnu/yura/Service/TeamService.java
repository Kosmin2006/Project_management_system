// TeamService.java
package ua.opnu.yura.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.yura.DTO.EmployeeDTO;
import ua.opnu.yura.DTO.TeamRequestDTO;
import ua.opnu.yura.DTO.TeamResponseDTO;
import ua.opnu.yura.Model.Employee;
import ua.opnu.yura.Model.Team;
import ua.opnu.yura.Repository.EmployeeRepository;
import ua.opnu.yura.Repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;

    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        List<Employee> members = dto.getMemberIds() != null
                ? employeeRepository.findAllById(dto.getMemberIds())
                : List.of();

        Team team = Team.builder()
                .name(dto.getName())
                .members(members)
                .build();

        return toResponseDTO(teamRepository.save(team));
    }

    public TeamResponseDTO addMemberToTeam(Long teamId, Long employeeId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        team.getMembers().add(employee);
        return toResponseDTO(teamRepository.save(team));
    }

    public TeamResponseDTO removeMemberFromTeam(Long teamId, Long employeeId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        team.getMembers().remove(employee);
        return toResponseDTO(teamRepository.save(team));
    }

    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private TeamResponseDTO toResponseDTO(Team team) {
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setMembers(team.getMembers().stream().map(this::toEmployeeDTO).toList());
        return dto;
    }

    private EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setEmail(employee.getEmail());
        return dto;
    }
}
