package www.exam.janvier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.repository.UtilisateurRepository;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepo;
    @Override
    public UserDetails loadUserByUsername(String nomSociete) throws UsernameNotFoundException {
        UtilisateurEntity user = utilisateurRepo.findByNomSociete(nomSociete);
        return new User(user.getNomSociete(),user.getPassword(),  user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));
    }
}
