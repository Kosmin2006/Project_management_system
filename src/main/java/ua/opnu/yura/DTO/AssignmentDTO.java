package ua.opnu.yura.DTO;

import lombok.Data;

@Data
public class AssignmentDTO {
    private Long id;
    private String role;
    private Long employeeId;
    private Long projectId;
}
