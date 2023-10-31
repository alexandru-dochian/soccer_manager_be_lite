package forktex.SoccerManagerBELite.controllers.player;

import forktex.SoccerManagerBELite.controllers.player.resources.PlayerUpdateDTO;
import forktex.SoccerManagerBELite.enums.LogCategoryType;
import forktex.SoccerManagerBELite.enums.LogPurposeType;
import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.repositories.projections.PlayerProjection;
import forktex.SoccerManagerBELite.services.authentication.AuthenticationService;
import forktex.SoccerManagerBELite.services.logs.LogService;
import forktex.SoccerManagerBELite.services.player.PlayerService;
import forktex.SoccerManagerBELite.utils.JsonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private LogService logService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(path = "/{playerId}")
    public ResponseEntity<PlayerProjection> getPlayerInformation(
                Principal principal, @PathVariable(name = "playerId") Long playerId) throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);
        logService.saveLog(LogCategoryType.GET_PLAYER_INFORMATION,
                LogPurposeType.START,
                user.getId(),
                "Received GET /player/" + playerId,
                null);

        final PlayerProjection playerProjection = playerService.getPlayerInformation(playerId, user.getId());

        logService.saveLog(LogCategoryType.GET_PLAYER_INFORMATION,
                LogPurposeType.START,
                user.getId(),
                "Responded GET /player/" + playerId,
                null);

        return ResponseEntity.ok(playerProjection);
    }

    @PutMapping(path = "/{playerId}")
    public ResponseEntity<?> updatePlayerInformation(Principal principal,
            @PathVariable(name = "playerId") Long playerId, @RequestBody @Validated PlayerUpdateDTO playerUpdateDTO)
            throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);
        logService.saveLog(LogCategoryType.UPDATE_PLAYER_INFORMATION,
                LogPurposeType.START,
                user.getId(),
                "Received PUT /player/" + playerId,
                JsonObjectMapper.getJsonStringFromObject(playerUpdateDTO));

        playerService.updatePlayer(playerId, user.getId(), playerUpdateDTO);

        logService.saveLog(LogCategoryType.UPDATE_PLAYER_INFORMATION,
                LogPurposeType.END,
                user.getId(),
                "Responded PUT /player/" + playerId,
                null);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
