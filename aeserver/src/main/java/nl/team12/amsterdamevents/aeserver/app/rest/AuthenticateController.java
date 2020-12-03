package nl.team12.amsterdamevents.aeserver.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import nl.team12.amsterdamevents.aeserver.app.exceptions.UnAuthorizedException;
import nl.team12.amsterdamevents.aeserver.app.models.User;
import nl.team12.amsterdamevents.aeserver.app.repositories.EntityRepository;
import nl.team12.amsterdamevents.aeserver.app.security.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/authenticate")
public class AuthenticateController {

    @Autowired
    private EntityRepository<User> userRepository;

    @Autowired
    private JWToken jwToken;


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody ObjectNode user,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws UnAuthorizedException {

        String email = user.get("eMail").asText();
        String password = user.get("passWord").asText();
//        long id = user.get("id").asLong();

        // TODO: Authenticate the user using the credentials provided (4.3.2)
//        User newUser = userRepository.findById(id);

        // if password incorrect
        if (!password.equals(email.substring(0, email.indexOf("@")))) {
            throw new UnAuthorizedException("Cannot authenticate user by email=" + email + " and password=" + password);
        }

        // Issue a token for the user valid for some time
        String tokenString = jwToken.encode(email, false);

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(new User(1, email.substring(0, email.indexOf("@")), email, false));
//        new User(1, email.substring(0, email.indexOf("@")), email, false)
    }
}
