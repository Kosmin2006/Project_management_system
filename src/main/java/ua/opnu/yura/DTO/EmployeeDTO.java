package ua.opnu.yura.DTO;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private String position;
    private String email;
}
