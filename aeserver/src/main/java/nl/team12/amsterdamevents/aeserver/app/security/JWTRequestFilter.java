package nl.team12.amsterdamevents.aeserver.app.security;

import nl.team12.amsterdamevents.aeserver.app.exceptions.AuthorizedException;
import nl.team12.amsterdamevents.aeserver.app.exceptions.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    // path prefixes that will be protected by the authentication filter
    private static final Set<String> SECURED_PATHS =
            Set.of("/aevents", "/registrations", "/users");

    @Autowired
    private JWToken jwToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        JWToken jwToken = null;
        String encryptedToken;

        try {
            // Get requested path
            String servletPath = request.getServletPath();

            // OPTIONS requests and non-secured area should pass through without check
            if (HttpMethod.OPTIONS.matches(request.getMethod()) ||
                    SECURED_PATHS.stream().noneMatch(servletPath::startsWith)) {

                chain.doFilter(request, response);
                return;
            }

            // get the encrypted token string from the authorization request header
            encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            // block the request if no token was found
            if (encryptedToken != null) {
                // remove the "Bearer" token prefix, if used
                encryptedToken = encryptedToken.replace("Bearer", "");
            }

            // decode the token
            // TODO: password????
//            jwToken = JWToken.decode(encryptedToken,jwToken.passWord);

            // Validate the token
            if (jwToken == null) {
                throw new AuthorizedException("You need to login first.");
            }

            // pass-on the token info as an attribute for the request
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

            // proceed with the chain
            chain.doFilter(request, response);
        } catch (Exception e) {
            // aborting the chain
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
            return;
        }
    }
}
