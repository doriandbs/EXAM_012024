package www.exam.janvier;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.repository.RoleRepository;


@SpringBootApplication
@EnableAutoConfiguration
public class Main {

    public static void main(String[] args) {
          SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository) {
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
        };
    }
}

