package ua.opnu.yura.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.yura.DTO.AssignmentDTO;
import ua.opnu.yura.DTO.EmployeeDTO;
import ua.opnu.yura.Model.Project;
import ua.opnu.yura.Repository.ProjectRepository;
import ua.opnu.yura.Service.AssignmentService;
import ua.opnu.yura.Service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AssignmentService assignmentService;
    private final ProjectRepository projectRepository;

    // 5. Додати нового працівника
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    // 6. Отримати всіх працівників
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // 🆕 6.1 Отримати одного працівника за ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 7. Оновити інформацію про працівника
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    // 8. Видалити працівника
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // 16. Отримати всі проєкти працівника
    @GetMapping("/{id}/projects")
    public ResponseEntity<List<Project>> getProjectsByEmployee(@PathVariable Long id) {
        List<AssignmentDTO> assignments = assignmentService.getAssignmentsByEmployee(id);
        List<Project> projects = assignments.stream()
                .map(dto -> projectRepository.findById(dto.getProjectId()).orElse(null))
                .filter(project -> project != null)
                .collect(Collectors.toList());

        return ResponseEntity.ok(projects);
    }
}
