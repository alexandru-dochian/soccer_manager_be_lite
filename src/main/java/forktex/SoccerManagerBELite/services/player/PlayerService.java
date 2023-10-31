package forktex.SoccerManagerBELite.services.player;

import forktex.SoccerManagerBELite.repositories.entities.Player;
import forktex.SoccerManagerBELite.repositories.projections.PlayerProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import forktex.SoccerManagerBELite.controllers.player.resources.PlayerUpdateDTO;
import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.exceptions.PlayerNotFoundByIdException;
import forktex.SoccerManagerBELite.exceptions.PlayerNotOwnedByUserException;
import forktex.SoccerManagerBELite.repositories.PlayerRepository;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public PlayerProjection getPlayerInformation(Long playerId, Long userId) throws ApiException {
        throwIfPlayerNotOwnedByUser(playerId, userId);
        return playerRepository.getPlayerInformation(playerId);
    }

    public void updatePlayer(Long playerId, Long userId, PlayerUpdateDTO playerUpdateDTO) throws ApiException {
        throwIfPlayerNotOwnedByUser(playerId, userId);
        final Player player = findPlayerByIdOrThrow(playerId);

        player.setFirstName(playerUpdateDTO.getFirstName());
        player.setLastName(playerUpdateDTO.getLastName());
        player.setCountry(playerUpdateDTO.getCountry());

        playerRepository.save(player);
    }

    public void throwIfPlayerNotOwnedByUser(Long playerId, Long userId) throws ApiException {
        if (! playerRepository.isPlayerOwnedByUser(playerId, userId)) {
            throw new PlayerNotOwnedByUserException(playerId, userId);
        }
    }

    public void transferPlayerToBuyingTeam(Player player, Long newTeamId) {
        player.increaseMarketValue();
        player.setTeamId(newTeamId);
        playerRepository.save(player);
    }

    public Player findPlayerByIdOrThrow(Long playerId) throws ApiException {
        return playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundByIdException(playerId));
    }
}
