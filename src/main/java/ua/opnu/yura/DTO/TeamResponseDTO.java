package ua.opnu.yura.DTO;

import lombok.Data;
import java.util.List;

@Data
public class TeamResponseDTO {
    private Long id;
    private String name;
    private List<EmployeeDTO> members;
}