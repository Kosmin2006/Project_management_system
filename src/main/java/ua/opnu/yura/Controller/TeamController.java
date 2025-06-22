package ua.opnu.yura.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.yura.DTO.TeamRequestDTO;
import ua.opnu.yura.DTO.TeamResponseDTO;
import ua.opnu.yura.Service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO dto) {
        return ResponseEntity.ok(teamService.createTeam(dto));
    }

    @PostMapping("/{teamId}/members/{employeeId}")
    public ResponseEntity<TeamResponseDTO> addMemberToTeam(@PathVariable Long teamId, @PathVariable Long employeeId) {
        return ResponseEntity.ok(teamService.addMemberToTeam(teamId, employeeId));
    }

    @DeleteMapping("/{teamId}/members/{employeeId}")
    public ResponseEntity<TeamResponseDTO> removeMemberFromTeam(@PathVariable Long teamId, @PathVariable Long employeeId) {
        return ResponseEntity.ok(teamService.removeMemberFromTeam(teamId, employeeId));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }
}
