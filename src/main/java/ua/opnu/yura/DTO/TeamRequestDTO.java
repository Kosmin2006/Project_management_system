package ua.opnu.yura.DTO;

import lombok.Data;
import java.util.List;

@Data
public class TeamRequestDTO {
    private String name;
    private List<Long> memberIds;
}
