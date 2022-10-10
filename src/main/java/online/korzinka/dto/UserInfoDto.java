package online.korzinka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password"}, allowGetters = false, allowSetters = true)
public class UserInfoDto implements UserDetails {
    private Integer id;
    private String username;
    private String lastname;
    private String email;
    private String password;
    private String phonenumber;
    private Double account;
    private Date created_at;
    private Set<SimpleGrantedAuthority> authorities;

//    @Override
//    public Set<SimpleGrantedAuthority> getAuthorities() {
//        return null;
//    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
