package online.korzinka.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ResourceBundle;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    //localization
    //internationalization i18n
//    @Bean
//    public ResourceBundle resourceBundle(){
//        return ResourceBundle.getBundle("mes");
//    }
}
