package forktex.SoccerManagerBELite.services.authentication;

import forktex.SoccerManagerBELite.controllers.authentication.resources.RegisterRequestDTO;
import forktex.SoccerManagerBELite.exceptions.*;
import forktex.SoccerManagerBELite.repositories.entities.User;
import forktex.SoccerManagerBELite.repositories.projections.ProfileProjection;
import forktex.SoccerManagerBELite.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import forktex.SoccerManagerBELite.exceptions.*;
import forktex.SoccerManagerBELite.repositories.UserRepository;
import forktex.SoccerManagerBELite.services.team.TeamService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TeamService teamService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }

    @Transactional(rollbackOn = Exception.class)
    public User register(RegisterRequestDTO registerRequestDTO) throws ApiException {
        throwIfInvalidCredentials(registerRequestDTO);
        final User user = createUser(registerRequestDTO.getEmail(), registerRequestDTO.getPassword());
        teamService.createTeamAndPlayers(user.getId());
        return user;
    }

    private User createUser(String email, String password) {
        return userRepository.save(
                new User()
                        .setEmail(email)
                        .setFirstName(StringUtils.generateRandomString())
                        .setLastName(StringUtils.generateRandomString())
                        .setPassword(passwordEncoder.encode(password))
        );
    }

    public User getUserFromPrincipalOrThrow(Principal principal) throws ApiException {
        if (principal == null) {
            throw new NullPrincipalException();
        }
        final User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            throw new UserNotFoundByEmailException(principal.getName());
        }
        return user;
    }


    public User findByEmailOrThrowException(String email) throws UserNotFoundByEmailException {
        final User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundByEmailException(email);
        }

        return user;
    }

    private void throwIfInvalidCredentials(RegisterRequestDTO registerRequestDTO) throws ApiException {
        final User user = userRepository.findByEmail(registerRequestDTO.getEmail());

        if (user != null) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    public ProfileProjection getProfile(Long userId) {
        return userRepository.getProfile(userId);
    }

    public User findUserByIdOrThrow(Long userId) throws ApiException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundByIdException(userId));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
