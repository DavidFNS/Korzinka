package online.korzinka.security;

import lombok.RequiredArgsConstructor;
import online.korzinka.dto.UserInfoDto;
import online.korzinka.service.impl.UserDetailServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            if (jwtService.validateToken(token)) {
                Integer id = (Integer) jwtService.getClaim(token, "sub");
                UserInfoDto userInfoDto = UserDetailServiceImpl.usersMap.get(id);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userInfoDto, null, userInfoDto.getAuthorities());

                // This object has requestAddress and sessionId
                WebAuthenticationDetails authenticationDetails = new WebAuthenticationDetailsSource()
                        .buildDetails(request);

                auth.setDetails(authenticationDetails);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
    }
}
