package forktex.SoccerManagerBELite.controllers.profile;

import forktex.SoccerManagerBELite.controllers.profile.resources.ProfileUpdateDTO;
import forktex.SoccerManagerBELite.enums.LogCategoryType;
import forktex.SoccerManagerBELite.enums.LogPurposeType;
import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.repositories.projections.ProfileProjection;
import forktex.SoccerManagerBELite.services.authentication.AuthenticationService;
import forktex.SoccerManagerBELite.services.profile.ProfileService;
import forktex.SoccerManagerBELite.utils.JsonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.services.logs.LogService;

import java.security.Principal;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {
    @Autowired
    private LogService logService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<ProfileProjection> getProfile(Principal principal) throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.GET_PROFILE,
                LogPurposeType.START,
                user.getId(),
                "Received GET /profile",
                null);

        final ProfileProjection profileProjection = profileService.getProfile(user.getId());

        logService.saveLog(LogCategoryType.GET_PROFILE,
                LogPurposeType.END,
                user.getId(),
                "Responded GET /profile",
                null);

        return ResponseEntity.ok(profileProjection);
    }


    @PutMapping
    public ResponseEntity<?> updateProfile(Principal principal,
            @RequestBody @Validated ProfileUpdateDTO profileUpdateDTO)
            throws ApiException {
        final User user = authenticationService.getUserFromPrincipalOrThrow(principal);

        logService.saveLog(LogCategoryType.UPDATE_PROFILE,
                LogPurposeType.START,
                user.getId(),
                "Received PUT /profile",
                JsonObjectMapper.getJsonStringFromObject(profileUpdateDTO));

        profileService.updateProfile(user.getId(), profileUpdateDTO);

        logService.saveLog(LogCategoryType.UPDATE_PROFILE,
                LogPurposeType.END,
                user.getId(),
                "Responded PUT /profile",
                null);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
