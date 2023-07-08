package tacos.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.User;

@Data
public class RegistrationForm {
    private  String name;
    private  String phoneNumber;
    private  String username;
    private  String password;
    private  String street;
    private  String city;
    private  String state;
    private  String zip;

    public User toUser(PasswordEncoder passwordEncoder){
        return new User(name,phoneNumber,username,passwordEncoder.encode(password),street,city,state,zip);
    }
}
