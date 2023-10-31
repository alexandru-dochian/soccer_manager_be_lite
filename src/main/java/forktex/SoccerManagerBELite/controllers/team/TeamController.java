package forktex.SoccerManagerBELite.controllers.team;

import forktex.SoccerManagerBELite.controllers.team.resources.TeamUpdateDTO;
import forktex.SoccerManagerBELite.enums.LogCategoryType;
import forktex.SoccerManagerBELite.enums.LogPurposeType;
import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.repositories.projections.PlayerProjection;
import forktex.SoccerManagerBELite.repositories.projections.TeamProjection;
import forktex.SoccerManagerBELite.services.authentication.AuthenticationService;
import forktex.SoccerManagerBELite.services.logs.LogService;
import forktex.SoccerManagerBELite.utils.JsonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import forktex.SoccerManagerBELite.services.team.TeamService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/team")
public class TeamController {
    @Autowired
    private LogService logService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private TeamService teamService;

    @GetMapping(path = "/{teamId}")
    public ResponseEntity<TeamProjection> getTeamInformation(
            Principal principal, @PathVariable(name = "teamId") Long teamId) throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.GET_TEAM_INFORMATION,
                LogPurposeType.START,
                user.getId(),
                "Received GET /team/" + teamId,
                null);

        final TeamProjection teamProjection = teamService.getTeamInformation(teamId, user.getId());

        logService.saveLog(LogCategoryType.GET_TEAM_INFORMATION,
                LogPurposeType.END,
                user.getId(),
                "Responded GET /team/" + teamId,
                null);

        return ResponseEntity.ok(teamProjection);
    }

    @GetMapping(path = "/{teamId}/players")
    public ResponseEntity<List<PlayerProjection>> getTeamPlayers(
            Principal principal, @PathVariable(name = "teamId") Long teamId) throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.GET_TEAM_PLAYERS,
                LogPurposeType.START,
                user.getId(),
                "Received GET /team/" + teamId + "/players",
                null);

        final List<PlayerProjection> teamPlayers = teamService.getTeamPlayers(user.getId(), teamId);

        logService.saveLog(LogCategoryType.GET_TEAM_PLAYERS,
                LogPurposeType.END,
                user.getId(),
                "Responded GET /team/" + teamId + "/players",
                null);

        return ResponseEntity.ok(teamPlayers);
    }


    @PutMapping(path = "/{teamId}")
    public ResponseEntity<?> updateTeamInformation(Principal principal,
            @PathVariable(name = "teamId") Long teamId, @RequestBody @Validated TeamUpdateDTO teamUpdateDTO)
            throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.UPDATE_TEAM_INFORMATION,
                LogPurposeType.START,
                user.getId(),
                "Received PUT /team/" + teamId,
                JsonObjectMapper.getJsonStringFromObject(teamUpdateDTO));

        teamService.updateTeamInformation(user.getId(), teamId, teamUpdateDTO);

        logService.saveLog(LogCategoryType.UPDATE_TEAM_INFORMATION,
                LogPurposeType.START,
                user.getId(),
                "Received PUT /team/" + teamId,
                null);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
