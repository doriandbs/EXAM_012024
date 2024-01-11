package www.exam.janvier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.SocieteEntity;
import www.exam.janvier.repository.RoleRepository;
import www.exam.janvier.repository.SocieteRepository;

import java.util.HashSet;


@SpringBootApplication
@EnableAutoConfiguration
public class Main {

    public static void main(String[] args) {
          SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, SocieteRepository societeRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_CLIENT") == null) {
                RoleEntity roleClient = new RoleEntity();
                roleClient.setName("ROLE_CLIENT");
                roleRepository.save(roleClient);
            }
            if (roleRepository.findByName("ROLE_ADMIN") == null) {
                RoleEntity roleAdmin = new RoleEntity();
                roleAdmin.setName("ROLE_ADMIN");
                roleRepository.save(roleAdmin);
            }
            if(societeRepository.findByNomSociete("admin")==null){
                SocieteEntity admin = new SocieteEntity();
                admin.setNomSociete("admin");
                admin.setMail("admin@admin.com");
                admin.setPassword("$2a$10$Pfj/3gLgpCMC6oaZ3ToafeylN8BE.jFWnlifP745tIiIAK5bCu7AW");
                RoleEntity roleAdmin = roleRepository.findByName("ROLE_ADMIN");
                admin.setRoles(new HashSet<>());
                admin.getRoles().add(roleAdmin);
                admin.setProduits(new HashSet<>());
                societeRepository.save(admin);
            }
        };
    }
}

