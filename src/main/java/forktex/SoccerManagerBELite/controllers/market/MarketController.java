package forktex.SoccerManagerBELite.controllers.market;

import forktex.SoccerManagerBELite.controllers.market.resources.PlacePlayerOnMarketDTO;
import forktex.SoccerManagerBELite.enums.LogCategoryType;
import forktex.SoccerManagerBELite.enums.LogPurposeType;
import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.repositories.projections.PlayerOnMarketProjection;
import forktex.SoccerManagerBELite.services.authentication.AuthenticationService;
import forktex.SoccerManagerBELite.services.logs.LogService;
import forktex.SoccerManagerBELite.services.market.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/market")
public class MarketController {
    @Autowired
    private LogService logService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MarketService marketService;

    @GetMapping
    public ResponseEntity<List<PlayerOnMarketProjection>> getPlayersOnMarket(Principal principal) throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.GET_PLAYERS_ON_MARKET,
                LogPurposeType.START,
                user.getId(),
                "Received GET /market",
                null);

        final List<PlayerOnMarketProjection> playersOnMarket = marketService.getPlayersOnMarket();

        logService.saveLog(LogCategoryType.GET_PLAYERS_ON_MARKET,
                LogPurposeType.END,
                user.getId(),
                "Responded GET /market",
                null);

        return ResponseEntity.ok(playersOnMarket);
    }

    @PostMapping(path = "/{playerId}/place")
    public ResponseEntity<?> placePlayerToMarket(Principal principal, @PathVariable(name = "playerId") Long playerId,
                @RequestBody @Validated PlacePlayerOnMarketDTO placePlayerOnMarketDTO) throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.PLACE_PLAYER_ON_MARKET,
                LogPurposeType.START,
                user.getId(),
                "Received POST /market/" + playerId + "/place",
                null);

        marketService.placePlayerOnMarket(playerId, placePlayerOnMarketDTO, user.getId());

        logService.saveLog(LogCategoryType.PLACE_PLAYER_ON_MARKET,
                LogPurposeType.END,
                user.getId(),
                "Responded POST /market/" + playerId + "/place",
                null);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{playerId}/withdraw")
    public ResponseEntity<?> withdrawPlayerFromMarket(Principal principal,
            @PathVariable(name = "playerId") Long playerId) throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.WITHDRAW_PLAYER_FROM_MARKET,
                LogPurposeType.START,
                user.getId(),
                "Received PUT /market/" + playerId + "/withdraw",
                null);

        marketService.withdrawPlayerFromMarket(playerId, user.getId());

        logService.saveLog(LogCategoryType.WITHDRAW_PLAYER_FROM_MARKET,
                LogPurposeType.END,
                user.getId(),
                "Responded PUT /market/" + playerId + "/withdraw",
                null);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{playerId}/buy")
    public ResponseEntity<?> buyPlayer(Principal principal, @PathVariable(name = "playerId") Long playerId)
            throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.BUY_PLAYER_FROM_MARKET,
                LogPurposeType.START,
                user.getId(),
                "Received PUT /market/" + playerId + "/buy",
                null);

        marketService.buyPlayerFromMarket(playerId, user.getId());

        logService.saveLog(LogCategoryType.BUY_PLAYER_FROM_MARKET,
                LogPurposeType.END,
                user.getId(),
                "Responded PUT /market/" + playerId + "/buy",
                null);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
