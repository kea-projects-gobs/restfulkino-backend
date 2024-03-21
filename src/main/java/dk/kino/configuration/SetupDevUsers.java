package dk.kino.configuration;

import dk.security.entity.Role;
import dk.security.entity.UserWithRoles;
import dk.security.repository.RoleRepository;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class SetupDevUsers implements ApplicationRunner {

    UserWithRolesRepository userWithRolesRepository;
    RoleRepository roleRepository;
    PasswordEncoder pwEncoder;
    String passwordUsedByAll;

    public SetupDevUsers(UserWithRolesRepository userWithRolesRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.roleRepository = roleRepository;
        this.pwEncoder = passwordEncoder;

        passwordUsedByAll = "test12";
    }

    public void run(ApplicationArguments args) {
        setupAllowedRoles();
        setupUserWithRoleUsers();
        setupEmployee();
    }

    private void setupAllowedRoles(){
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("EMPLOYEE"));
    }

     /*****************************************************************************************
     IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL
     iT'S ONE OF THE TOP SECURITY FLAWS YOU CAN DO
     If you see the lines below in log-outputs on Azure, forget whatever had your attention on, AND FIX THIS PROBLEM
     *****************************************************************************************/
    private void setupUserWithRoleUsers() {
        Role roleUser = roleRepository.findById("USER").orElseThrow(()-> new NoSuchElementException("Role 'user' not found"));
        Role roleAdmin = roleRepository.findById("ADMIN").orElseThrow(()-> new NoSuchElementException("Role 'admin' not found"));
        System.out.println("******************************************************************************");
        System.out.println("********** IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ************");
        System.out.println();
        System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
        System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
        System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
        System.out.println();
        System.out.println("******************************************************************************");
        UserWithRoles admin = new UserWithRoles("admin", pwEncoder.encode(passwordUsedByAll), "admin@a.dk");
        UserWithRoles user = new UserWithRoles("user", pwEncoder.encode(passwordUsedByAll), "user@a.dk");
        UserWithRoles useradmin = new UserWithRoles("useradmin", pwEncoder.encode(passwordUsedByAll), "useradmin@a.dk");

        admin.addRole(roleAdmin);
        user.addRole(roleUser);
        useradmin.addRole(roleAdmin);
        useradmin.addRole(roleUser);
        userWithRolesRepository.save(admin);
        userWithRolesRepository.save(user);
        userWithRolesRepository.save(useradmin);

    }

    private void setupEmployee(){
        Role roleEmployee = roleRepository.findById("EMPLOYEE").orElseThrow(()-> new NoSuchElementException("Role 'employee' not found"));
        UserWithRoles employee1 = new UserWithRoles("employee1", pwEncoder.encode(passwordUsedByAll), "employee1@a.dk");
        UserWithRoles employee2 = new UserWithRoles("employee2", pwEncoder.encode(passwordUsedByAll), "employee2@a.dk");
        employee1.addRole(roleEmployee);
        employee2.addRole(roleEmployee);
        userWithRolesRepository.save(employee1);
        userWithRolesRepository.save(employee2);
    }
}
