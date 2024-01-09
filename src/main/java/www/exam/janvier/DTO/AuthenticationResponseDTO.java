package www.exam.janvier.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String jwt;
    private UserDetails user;
}
