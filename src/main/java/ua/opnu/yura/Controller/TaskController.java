package ua.opnu.yura.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.yura.DTO.TaskDTO;
import ua.opnu.yura.Service.TaskService;
import ua.opnu.yura.Service.TaskService.EmployeeTaskStats;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskDTO>> getTasksByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getTasksByEmployee(employeeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/in-progress")
    public ResponseEntity<List<TaskDTO>> getTasksInProgress() {
        return ResponseEntity.ok(taskService.getTasksInProgress());
    }

    @GetMapping("/project/{projectId}/count")
    public ResponseEntity<Long> countTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.countTasksByProject(projectId));
    }

    @GetMapping("/average-completion-time")
    public ResponseEntity<Double> getAverageCompletionTime() {
        return ResponseEntity.ok(taskService.getAverageCompletionTime());
    }

    @GetMapping("/employee/{employeeId}/statistics")
    public ResponseEntity<EmployeeTaskStats> getEmployeeTaskStatistics(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getEmployeeTaskStatistics(employeeId));
    }
}
