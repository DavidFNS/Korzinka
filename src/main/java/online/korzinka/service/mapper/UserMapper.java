package online.korzinka.service.mapper;
import online.korzinka.dto.UserInfoDto;
import online.korzinka.entity.Authorities;
import online.korzinka.entity.User;
import org.mapstruct.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
@Mapper(componentModel = "spring")
public interface UserMapper{
    User toEntity(UserInfoDto userInfoDto);
    UserInfoDto toDto(User user);

    default SimpleGrantedAuthority convert(Authorities auth){
        return new SimpleGrantedAuthority(auth.getName());
    }
}
