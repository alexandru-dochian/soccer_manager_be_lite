package forktex.SoccerManagerBELite.services.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import forktex.SoccerManagerBELite.controllers.profile.resources.ProfileUpdateDTO;
import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.repositories.projections.ProfileProjection;
import forktex.SoccerManagerBELite.services.authentication.AuthenticationService;

@Service
public class ProfileService {
    @Autowired
    private AuthenticationService authenticationService;

    public ProfileProjection getProfile(Long userId) {
        return authenticationService.getProfile(userId);
    }

    public void updateProfile(Long userId, ProfileUpdateDTO profileUpdateDTO) throws ApiException {
        final User user = authenticationService.findUserByIdOrThrow(userId);

        user.setFirstName(profileUpdateDTO.getFirstName());
        user.setLastName(profileUpdateDTO.getFirstName());
        authenticationService.updateUser(user);
    }
}
