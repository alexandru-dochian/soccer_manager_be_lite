package forktex.SoccerManagerBELite.services.team;

import forktex.SoccerManagerBELite.controllers.team.resources.TeamUpdateDTO;
import forktex.SoccerManagerBELite.exceptions.*;
import forktex.SoccerManagerBELite.repositories.PlayerRepository;
import forktex.SoccerManagerBELite.repositories.TeamRepository;
import forktex.SoccerManagerBELite.repositories.entities.Player;
import forktex.SoccerManagerBELite.repositories.entities.Team;
import forktex.SoccerManagerBELite.repositories.projections.PlayerProjection;
import forktex.SoccerManagerBELite.repositories.projections.TeamProjection;
import forktex.SoccerManagerBELite.services.team.resources.PlayerType;
import forktex.SoccerManagerBELite.utils.NumberUtils;
import forktex.SoccerManagerBELite.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import forktex.SoccerManagerBELite.exceptions.*;

import java.util.*;

@Service
public class TeamService {
    static final Double PLAYER_INITIAL_VALUE = 1e6;
    static final Double TEAM_INITIAL_ADDITIONAL_VALUE = 5e6;
    static Map<String, Integer> TEAM_BLUEPRINT = new HashMap<>();

    static {
        TEAM_BLUEPRINT.put(PlayerType.GOALKEEPER.name(), 3);
        TEAM_BLUEPRINT.put(PlayerType.DEFENDER.name(), 6);
        TEAM_BLUEPRINT.put(PlayerType.MIDFIELDER.name(), 6);
        TEAM_BLUEPRINT.put(PlayerType.ATTACKER.name(), 5);
    }

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public TeamProjection getTeamInformation(Long teamId, Long userId) throws ApiException {
        throwIfTeamNotOwnedByUser(teamId, userId);
        return teamRepository.getTeamInformation(teamId);
    }

    public void updateTeamInformation(Long userId, Long teamId, TeamUpdateDTO teamUpdateDTO) throws ApiException {
        throwIfTeamNotOwnedByUser(teamId, userId);
        final Team team = findTeamByIdOrThrow(teamId);

        team.setName(teamUpdateDTO.getName());
        team.setCountry(teamUpdateDTO.getCountry());

        teamRepository.save(team);
    }

    public List<PlayerProjection> getTeamPlayers(Long userId, Long teamId) throws ApiException {
        throwIfTeamNotOwnedByUser(teamId, userId);
        return teamRepository.getTeamPlayers(teamId);
    }

    public void createTeamAndPlayers(Long userId) {
        final Team newTeam = createTeam(userId, StringUtils.generateRandomString(), StringUtils.generateRandomString());
        createPlayers(newTeam);
        saveTeamAndUpdatePlayersValue(newTeam);
    }

    private Team createTeam(Long userId, String teamName, String teamCountry) {
        return teamRepository.save(
                new Team()
                        .setOwnedByUser(userId)
                        .setName(teamName)
                        .setCountry(teamCountry)
                        .setValue(0.0)
                        .setAdditionalValue(TEAM_INITIAL_ADDITIONAL_VALUE)
                        .setCreatedDate(new Date())
        );
    }

    private void createPlayers(Team newTeam) {
        for (Map.Entry<String, Integer> mapEntry : TEAM_BLUEPRINT.entrySet()) {
            final String playerType = mapEntry.getKey();
            final Integer numberOfPlayers = mapEntry.getValue();

            for (int playerIndex = 0; playerIndex < numberOfPlayers; playerIndex++) {
                playerRepository.save(new Player()
                        .setTeamId(newTeam.getId())
                        .setType(playerType)
                        .setFirstName(StringUtils.generateRandomString())
                        .setLastName(StringUtils.generateRandomString())
                        .setCountry(StringUtils.generateRandomString())
                        .setAge(NumberUtils.getRandomAge())
                        .setMarketValue(PLAYER_INITIAL_VALUE)
                );
            }
        }
    }

    public Team findTeamByPlayerId(Long playerId) throws ApiException{
        return teamRepository.findTeamByPlayerId(playerId)
                .orElseThrow(() -> new TeamNotFoundByPlayerIdException(playerId));
    }

    public Team findTeamByUserId(Long userId) {
        return teamRepository.findByOwnedByUser(userId);
    }

    public void updateFundsUponPurchase(Team buyingTeam, Double requestedPrice) throws ApiException {
        if (buyingTeam.getAdditionalValue() < requestedPrice) {
            throw new InsufficientFundsException(buyingTeam.getAdditionalValue(), requestedPrice);
        } else {
            buyingTeam.setAdditionalValue(buyingTeam.getAdditionalValue() - requestedPrice);
        }
        saveTeamAndUpdatePlayersValue(buyingTeam);
    }

    public void updateFundsUponSale(Team sellingTeam, Double requestedPrice) {
        sellingTeam.setAdditionalValue(sellingTeam.getAdditionalValue() + requestedPrice);
        saveTeamAndUpdatePlayersValue(sellingTeam);
    }

    private void saveTeamAndUpdatePlayersValue(Team newTeam) {
        final Double playersValue = teamRepository.getTeamPlayers(newTeam.getId()).stream()
                .map(PlayerProjection::getMarketValue)
                .reduce(0.0, Double::sum);

        teamRepository.save(newTeam.setValue(playersValue));
    }

    private void throwIfTeamNotOwnedByUser(Long teamId, Long userId) throws ApiException {
        if (! teamRepository.isTeamOwnedByUser(teamId, userId)) {
            throw new TeamNotOwnedByTeamException(teamId, userId);
        }
    }

    private Team findTeamByIdOrThrow(Long teamId) throws ApiException{
        return teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundByIdException(teamId));
    }
}
