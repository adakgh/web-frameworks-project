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

    // TODO: uncomment when repository has enough data
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody ObjectNode userInfo,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws UnAuthorizedException {
        // the input
        String email = userInfo.get("eMail").asText();
        String password = userInfo.get("passWord").asText();

        // Authenticate the user using the credentials provided
//        User user = new User();
//        List<User> users = userRepository.findAll();
//        boolean admin = false;
//
//        for (User newUser : users) {
//            if (newUser.geteMail().equals(email)) {
//                user = newUser;
//            }
//
//            if (newUser.isAdmin()) {
//                admin = true;
//            }
//        }

        // Authenticate the user by finding by id
//        User newUser = userRepository.findById(user.getId());

        // If password incorrect throw an exception
        if (!password.equals(email.substring(0, email.indexOf("@")))) {
            throw new UnAuthorizedException("Cannot authenticate user by email=" + email + " and password=" + password);
        }

        // Issue a token for the user valid for some time
        String tokenString = jwToken.encode(email, false);
//        String tokenString = jwToken.encode(email, admin);

//        return ResponseEntity.accepted()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
//                .body(newUser);
        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(new User(2, email.substring(0, email.indexOf("@")), email, false));
    }
}
