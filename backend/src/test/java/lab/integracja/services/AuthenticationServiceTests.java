package lab.integracja.services;

import lab.integracja.config.JwtService;
import lab.integracja.dtos.AuthenticationRequest;
import lab.integracja.dtos.AuthenticationResponse;
import lab.integracja.dtos.RegisterRequest;
import lab.integracja.entities.Role;
import lab.integracja.entities.User;
import lab.integracja.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void register_NewUser_Success() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("newuser", "newuser@example.com", "password");
        when(userRepository.existsByLogin(registerRequest.getLogin())).thenReturn(false);

        // Act
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void register_UserAlreadyExists_ConflictException() {
        // Arrange
        RegisterRequest existingUserRequest = new RegisterRequest("existinguser", "existinguser@example.com", "password");
        when(userRepository.existsByLogin(existingUserRequest.getLogin())).thenReturn(true);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> authenticationService.register(existingUserRequest));
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void authenticate_ValidCredentials_Success() {
        // Arrange
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("existinguser", "password");
        User existingUser = new User(1L, "existinguser", "existinguser@example.com", "encodedPassword", Role.USER);
        when(userRepository.findByLogin(authenticationRequest.getLogin())).thenReturn(java.util.Optional.of(existingUser));
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtService.generateToken(existingUser)).thenReturn("mockToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        // Assert
        assertNotNull(response.getToken());
        verify(jwtService, times(1)).generateToken(existingUser);
    }

    @Test
    void authenticate_InvalidCredentials_ThrowsResponseStatusException() {
        // Arrange
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("nonexistentuser", "invalidPassword");
        doThrow(new UsernameNotFoundException("User not found")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(authenticationRequest));
        verify(jwtService, never()).generateToken(any(User.class));
    }
}
