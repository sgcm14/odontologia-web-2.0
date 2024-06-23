package clinica.sistemaReservaTurno.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationSucessHandler implements AuthenticationSuccessHandler {

    private final Map<String, String> roleTargetUrlMap = new HashMap<>();

    public AuthenticationSucessHandler() {
        roleTargetUrlMap.put("ROLE_USER", "/home_user.html");
        roleTargetUrlMap.put("ROLE_ADMIN", "/home_admin.html");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        response.sendRedirect(targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(grantedAuthority -> roleTargetUrlMap.get(grantedAuthority.getAuthority()))
                .filter(url -> url != null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró ninguna URL de destino para el rol dado"));
    }
}

/*public class AuthenticationSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin){
            setDefaultTargetUrl("/admin/home");
        } else{
            setDefaultTargetUrl("/user/home");
        }
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}*/


