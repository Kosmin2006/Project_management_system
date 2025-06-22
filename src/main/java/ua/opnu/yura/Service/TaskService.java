package ua.opnu.yura.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.yura.DTO.TaskDTO;
import ua.opnu.yura.Model.Employee;
import ua.opnu.yura.Model.Project;
import ua.opnu.yura.Model.Task;
import ua.opnu.yura.Repository.EmployeeRepository;
import ua.opnu.yura.Repository.ProjectRepository;
import ua.opnu.yura.Repository.TaskRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public TaskDTO createTask(TaskDTO dto) {
        Task task = toEntity(dto);
        return toDto(taskRepository.save(task));
    }

    public List<TaskDTO> getTasksByProject(Long projectId) {
        Project project = getProject(projectId);
        return taskRepository.findByProject(project).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByEmployee(Long employeeId) {
        Employee employee = getEmployee(employeeId);
        return taskRepository.findByAssignee(employee).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setCreatedAt(dto.getCreatedAt());
        task.setCompletedAt(dto.getCompletedAt());

        if (dto.getProjectId() != null)
            task.setProject(getProject(dto.getProjectId()));
        if (dto.getAssigneeId() != null)
            task.setAssignee(getEmployee(dto.getAssigneeId()));

        return toDto(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskDTO> getTasksInProgress() {
        return taskRepository.findByStatus("виконується").stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Long countTasksByProject(Long projectId) {
        return taskRepository.countByProject(getProject(projectId));
    }

    public Double getAverageCompletionTime() {
        List<Task> completedTasks = taskRepository.findByStatus("Завершено");

        if (completedTasks.isEmpty()) return 0.0;

        long totalDays = 0;
        int count = 0;

        for (Task task : completedTasks) {
            LocalDate startDate = task.getProject().getStartDate();
            LocalDate endDate = task.getProject().getEndDate();

            if (startDate != null && endDate != null) {
                totalDays += ChronoUnit.DAYS.between(startDate, endDate);
                count++;
            }
        }

        return count == 0 ? 0.0 : (double) totalDays / count;
    }

    public EmployeeTaskStats getEmployeeTaskStatistics(Long employeeId) {
        Employee employee = getEmployee(employeeId);

        long totalTasks = taskRepository.countByAssignee(employee);
        long inProgress = taskRepository.countByAssigneeAndStatus(employee, "виконується");
        long completed = taskRepository.countByAssigneeAndStatus(employee, "Завершено");

        return new EmployeeTaskStats(totalTasks, inProgress, completed);
    }

    // Mapping
    private TaskDTO toDto(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setCompletedAt(task.getCompletedAt());
        if (task.getProject() != null) dto.setProjectId(task.getProject().getId());
        if (task.getAssignee() != null) dto.setAssigneeId(task.getAssignee().getId());
        return dto;
    }

    private Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setCreatedAt(dto.getCreatedAt());
        task.setCompletedAt(dto.getCompletedAt());

        if (dto.getProjectId() != null)
            task.setProject(getProject(dto.getProjectId()));
        if (dto.getAssigneeId() != null)
            task.setAssignee(getEmployee(dto.getAssigneeId()));

        return task;
    }

    private Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public static class EmployeeTaskStats {
        public long totalTasks;
        public long inProgressTasks;
        public long completedTasks;

        public EmployeeTaskStats(long totalTasks, long inProgressTasks, long completedTasks) {
            this.totalTasks = totalTasks;
            this.inProgressTasks = inProgressTasks;
            this.completedTasks = completedTasks;
        }
    }
}
