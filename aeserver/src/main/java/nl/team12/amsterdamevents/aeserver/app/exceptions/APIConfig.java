package nl.team12.amsterdamevents.aeserver.app.exceptions;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
public class APIConfig implements WebMvcConfigurer {

    // JWT configurations
    @Value("${jwt.issuer:private company}")
    public String issuer;

    @Value("${jwt.pass-phrase}")
    public String passWord;

    @Value("${jwt.expiration-seconds}")
    public int expiration;

    @Override
    public void addCorsMapping(CrosRegistry registry){
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE)
                .exposedHeaders(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE)
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedOrigins("http://localhost:4805");
    }
}
