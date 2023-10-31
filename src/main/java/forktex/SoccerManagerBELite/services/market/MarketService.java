package forktex.SoccerManagerBELite.services.market;

import forktex.SoccerManagerBELite.exceptions.PlayerNotActiveOnMarketException;
import forktex.SoccerManagerBELite.exceptions.SameTeamTransferException;
import forktex.SoccerManagerBELite.repositories.entities.Market;
import forktex.SoccerManagerBELite.repositories.entities.Player;
import forktex.SoccerManagerBELite.repositories.entities.Team;
import forktex.SoccerManagerBELite.repositories.projections.PlayerOnMarketProjection;
import forktex.SoccerManagerBELite.services.market.resources.MarketSynchronizationReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import forktex.SoccerManagerBELite.controllers.market.resources.PlacePlayerOnMarketDTO;
import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.exceptions.PlayerAlreadyActiveOnMarketException;
import forktex.SoccerManagerBELite.repositories.MarketRepository;
import forktex.SoccerManagerBELite.services.player.PlayerService;
import forktex.SoccerManagerBELite.services.team.TeamService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MarketService {
    @Autowired
    private MarketRepository marketRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;

    public List<PlayerOnMarketProjection> getPlayersOnMarket() {
        return marketRepository.getPlayersOnMarket();
    }

    public void placePlayerOnMarket(Long playerId, PlacePlayerOnMarketDTO placePlayerOnMarketDTO, Long userId)
            throws ApiException {
        playerService.throwIfPlayerNotOwnedByUser(playerId, userId);
        throwIfPlayerAlreadyActiveOnMarket(playerId);

        marketRepository.save(new Market()
                .setPlayerId(playerId)
                .setCanceled(false)
                .setFinalized(false)
                .setSellingTeamId(teamService.findTeamByPlayerId(playerId).getId())
                .setRequestedPrice(placePlayerOnMarketDTO.getRequestedPrice())
        );
    }

    public void withdrawPlayerFromMarket(Long playerId, Long userId) throws ApiException {
        final String lockId = MarketSynchronizationReason.MODIFY_PLAYER_ON_MARKET_ENTRY.name() + playerId;
        synchronized (lockId.intern()) {
            playerService.throwIfPlayerNotOwnedByUser(playerId, userId);

            final Market market = getPlayerActiveMarketEntry(playerId);
            market.setCanceled(Boolean.TRUE);
            marketRepository.save(market);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void buyPlayerFromMarket(Long playerId, Long userId) throws ApiException {
        final String lockId = MarketSynchronizationReason.MODIFY_PLAYER_ON_MARKET_ENTRY.name() + playerId;
        synchronized (lockId.intern()) {
            final Team sellingTeam = teamService.findTeamByPlayerId(playerId);
            final Team buyingTeam = teamService.findTeamByUserId(userId);
            final Player player = playerService.findPlayerByIdOrThrow(playerId);
            transferPlayer(player, sellingTeam, buyingTeam);
        }
    }

    private void transferPlayer(Player player, Team sellingTeam, Team buyingTeam) throws ApiException {
        throwIfTransferInvalid(sellingTeam, buyingTeam);
        final Market market = getPlayerActiveMarketEntry(player.getId());

        playerService.transferPlayerToBuyingTeam(player, buyingTeam.getId());
        teamService.updateFundsUponPurchase(buyingTeam, market.getRequestedPrice());
        teamService.updateFundsUponSale(sellingTeam, market.getRequestedPrice());

        finalizeTransfer(market, buyingTeam);
    }

    private void throwIfTransferInvalid(Team sellingTeam, Team buyingTeam) throws ApiException {
        if (sellingTeam.getId().equals(buyingTeam.getId())) {
            throw new SameTeamTransferException();
        }
    }

    private void finalizeTransfer(Market market, Team buyingTeam) {
        market.setFinalized(true);
        market.setBuyingTeamId(buyingTeam.getId());
        marketRepository.save(market);
    }

    private Market getPlayerActiveMarketEntry(Long playerId) throws ApiException {
        throwIfPlayerNotActiveOnMarket(playerId);
        return marketRepository.findByPlayerIdAndFinalizedFalseAndCanceledFalse(playerId);
    }

    private void throwIfPlayerNotActiveOnMarket(Long playerId) throws PlayerNotActiveOnMarketException {
        if (!marketRepository.isPlayerAlreadyActiveOnMarket(playerId)) {
            throw new PlayerNotActiveOnMarketException(playerId);
        }
    }

    private void throwIfPlayerAlreadyActiveOnMarket(Long playerId) throws ApiException {
        if (marketRepository.isPlayerAlreadyActiveOnMarket(playerId)) {
            throw new PlayerAlreadyActiveOnMarketException(playerId);
        }
    }
}
