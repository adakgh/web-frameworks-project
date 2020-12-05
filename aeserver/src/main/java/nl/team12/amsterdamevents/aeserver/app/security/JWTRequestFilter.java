package nl.team12.amsterdamevents.aeserver.app.security;

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
    private JWToken jwTokenClass;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        JWToken jwToken = null;
        String encryptedToken;

        // get requested path
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
            encryptedToken = encryptedToken.replace("Bearer ", "");

            // decode the token
            jwToken = jwTokenClass.decode(encryptedToken);

            // get a representation of the token for future usage
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

            // proceed with the chain
            chain.doFilter(request, response);
        }

        // validate the token
        if (jwToken == null) {
            throw new UnAuthorizedException("You need to login first");
        }
    }

}
