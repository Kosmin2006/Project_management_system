package ua.opnu.yura.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.yura.DTO.AssignmentDTO;
import ua.opnu.yura.Model.Assignment;
import ua.opnu.yura.Repository.AssignmentRepository;
import ua.opnu.yura.Repository.EmployeeRepository;
import ua.opnu.yura.Repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public AssignmentDTO createAssignment(AssignmentDTO dto) {
        Assignment assignment = new Assignment();
        assignment.setRole(dto.getRole());
        assignment.setEmployee(employeeRepository.findById(dto.getEmployeeId()).orElse(null));
        assignment.setProject(projectRepository.findById(dto.getProjectId()).orElse(null));

        return toDto(assignmentRepository.save(assignment));
    }

    public List<AssignmentDTO> getAssignmentsByProject(Long projectId) {
        return assignmentRepository.findByProjectId(projectId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public List<AssignmentDTO> getAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AssignmentDTO> getAssignmentById(Long id) {
        return assignmentRepository.findById(id).map(this::toDto);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    private AssignmentDTO toDto(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(assignment.getId());
        dto.setRole(assignment.getRole());
        dto.setEmployeeId(assignment.getEmployee().getId());
        dto.setProjectId(assignment.getProject().getId());
        return dto;
    }
}
