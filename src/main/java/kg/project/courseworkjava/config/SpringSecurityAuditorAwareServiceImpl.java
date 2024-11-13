package kg.project.courseworkjava.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public class SpringSecurityAuditorAwareServiceImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String by = "system";

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                by = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                by = (String) principal;
            }
        }

        return Optional.ofNullable(by);
    }
}