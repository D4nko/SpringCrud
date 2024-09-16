package pl.kurs.userroleApi.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.kurs.userroleApi.dto.UserDto;
import pl.kurs.userroleApi.model.Role;
import pl.kurs.userroleApi.repository.RoleRepository;

import java.util.Set;

@Service
@Profile("no-liquibase")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init(){
        log.info("Initializing default users and roles...");

    Role userRole = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new Role(null, "USER", null)));
    Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> roleRepository.save(new Role(null, "ADMIN", null)));

    UserDto john = new UserDto();
        john.setName("John");
        john.setSurname("Doe");
        john.setEmail("john.doe@example.com");
        john.setPassword("password1");
        john.setRoles(Set.of("ADMIN"));
    {
        userService.createUser(john);
        log.info("Created user: john.doe@example.com with ADMIN role");
    }

    UserDto jane = new UserDto();
        jane.setName("Jane");
        jane.setSurname("Doe");
        jane.setEmail("jane.doe@example.com");
        jane.setPassword("password2");
        jane.setRoles(Set.of("USER"));


    {
        userService.createUser(jane);
        log.info("Created user: jane.doe@example.com with USER role");
    }

    UserDto alice = new UserDto();
        alice.setName("Alice");
        alice.setSurname("Smith");
        alice.setEmail("alice.smith@example.com");
        alice.setPassword("password3");
        alice.setRoles(Set.of("USER","ADMIN"));


    {
        userService.createUser(alice);
        log.info("Created user: alice.smith@example.com with USER and ADMIN roles");
    }

    UserDto bob = new UserDto();
        bob.setName("Bob");
        bob.setSurname("Brown");
        bob.setEmail("bob.brown@example.com");
        bob.setPassword("password4");
        bob.setRoles(Set.of("USER"));


    {
        userService.createUser(bob);
        log.info("Created user: bob.brown@example.com with USER role");
    }

        log.info("Default users and roles initialized");
}
}

