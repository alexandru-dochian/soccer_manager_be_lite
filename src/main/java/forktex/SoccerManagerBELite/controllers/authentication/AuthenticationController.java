package forktex.SoccerManagerBELite.controllers.authentication;

import forktex.SoccerManagerBELite.controllers.authentication.resources.LoginRequestDTO;
import forktex.SoccerManagerBELite.controllers.authentication.resources.LoginResponseDTO;
import forktex.SoccerManagerBELite.controllers.authentication.resources.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import forktex.SoccerManagerBELite.enums.LogCategoryType;
import forktex.SoccerManagerBELite.enums.LogPurposeType;
import forktex.SoccerManagerBELite.enums.UserType;
import forktex.SoccerManagerBELite.exceptions.InvalidCredentialsException;
import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.services.authentication.AuthenticationService;
import forktex.SoccerManagerBELite.services.logs.LogService;
import forktex.SoccerManagerBELite.utils.JsonObjectMapper;
import forktex.SoccerManagerBELite.utils.JwtUtils;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private LogService logService;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
            throws Exception {
        logService.saveLog(LogCategoryType.LOGIN,
                LogPurposeType.START,
                UserType.UNKNOWN.getValue(),
                "Received POST /login",
                JsonObjectMapper.getJsonStringFromObject(loginRequestDTO));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()));
        } catch (Exception exc) {
            throw new InvalidCredentialsException();
        }

        final UserDetails userDetails = authenticationService.loadUserByUsername(loginRequestDTO.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails);
        final User user = authenticationService.findByEmailOrThrowException(loginRequestDTO.getEmail());

        final LoginResponseDTO loginResponseDTO = new LoginResponseDTO()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setToken(jwt);

        logService.saveLog(LogCategoryType.LOGIN,
                LogPurposeType.END,
                user.getId(),
                "Responded POST /login",
                JsonObjectMapper.getJsonStringFromObject(loginResponseDTO));

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Validated RegisterRequestDTO registerRequestDTO)
            throws Exception {
        logService.saveLog(LogCategoryType.CREATE_ACCOUNT,
                LogPurposeType.START,
                UserType.UNKNOWN.getValue(),
                "Received POST /register",
                JsonObjectMapper.getJsonStringFromObject(registerRequestDTO));

        final User user = authenticationService.register(registerRequestDTO);

        logService.saveLog(LogCategoryType.CREATE_ACCOUNT,
                LogPurposeType.END,
                user.getId(),
                "Responded POST /register",
                null);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
