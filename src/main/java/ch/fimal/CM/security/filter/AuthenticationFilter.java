package ch.fimal.CM.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.fimal.CM.model.RefreshToken;
import ch.fimal.CM.model.TokenResponse;
import ch.fimal.CM.model.User;
import ch.fimal.CM.repository.RefreshTokenRepository;
import ch.fimal.CM.security.manager.CustomAuthenticationManager;
import ch.fimal.CM.service.AuthServiceImpl;
import ch.fimal.CM.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final AuthServiceImpl authService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        User user = userService.getUser(username);

        RefreshToken refreshToken = authService.createNewRefreshToken(user);
        String newAccessToken = authService.generateAccessToken(user);

        refreshTokenRepository.save(refreshToken);

        response.setContentType("application/json");

        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        new TokenResponse(
                                newAccessToken,
                                refreshToken.getToken())));
    }

}
