package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import www.exam.janvier.dto.AuthenticationResponseDTO;
import www.exam.janvier.dto.LoginDTO;
import www.exam.janvier.utils.JwtUtil;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;
    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public AuthenticationResponseDTO authentication(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getNomsociete(),
                           loginDTO.getPassword()
             )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return new AuthenticationResponseDTO(jwt, userDetails);
    }



}
