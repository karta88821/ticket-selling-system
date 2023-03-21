package com.daniel_liao.ticketsellingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.daniel_liao.ticketsellingsystem.entity.Role;
import com.daniel_liao.ticketsellingsystem.entity.User;
import com.daniel_liao.ticketsellingsystem.repository.RoleRepository;
import com.daniel_liao.ticketsellingsystem.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetRole() {
        Role role = roleRepo.findById(1).get();
        assertEquals(role.getName(), "ADMIN");
    }

    @Test
    public void testCreateUser() {
        
        User user = new User();
        user.setName("admin");
        user.setAccount("admin");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("admin2023"));

        user.setMail("admin@test.com");

        Role normalRole = roleRepo.findByName("ADMIN");
        user.setRole(normalRole);

        User savedUser = userRepo.save(user); 

        //User existUser = entityManager.find(User.class, savedUser.getId());

        assertEquals(savedUser.getAccount(), user.getAccount());
        assertEquals(savedUser.getRole().getName(), normalRole.getName());
    }

    @Test
    public void testFindUserByAccount() {
        String account = "m10304";
        User foundUser = userRepo.findByAccount(account);

        assertEquals(foundUser.getAccount(), account);
    }
}
