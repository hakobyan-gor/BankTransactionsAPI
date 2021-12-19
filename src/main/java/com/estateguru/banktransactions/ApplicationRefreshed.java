package com.estateguru.banktransactions;

import com.estateguru.banktransactions.exceptions.EntityNotFoundException;
import com.estateguru.banktransactions.models.entities.lastNumber.LastNumbers;
import com.estateguru.banktransactions.models.entities.user.Admin;
import com.estateguru.banktransactions.models.entities.user.role.Role;
import com.estateguru.banktransactions.repositories.lastNumber.LastNumbersRepository;
import com.estateguru.banktransactions.repositories.user.admin.AdminRepository;
import com.estateguru.banktransactions.repositories.user.role.RoleRepository;
import com.estateguru.banktransactions.utils.RoleConstants;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApplicationRefreshed implements ApplicationListener<ContextRefreshedEvent> {

    private final LastNumbersRepository lastNumbersRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ApplicationRefreshed(
            LastNumbersRepository lastNumbersRepository,
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.lastNumbersRepository = lastNumbersRepository;
        this.adminRepository       = adminRepository;
        this.passwordEncoder       = passwordEncoder;
        this.roleRepository        = roleRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
/*        try {
            seedData();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private void seedData() throws EntityNotFoundException {

        LastNumbers lastNumbers = new LastNumbers();
        lastNumbersRepository.save(lastNumbers);

        Role adminRole = new Role();
        adminRole.setRoleName(RoleConstants.ADMIN_NAME);
        adminRole.setHidden(false);
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName(RoleConstants.USER_NAME);
        userRole.setHidden(false);
        roleRepository.save(userRole);

        Admin admin = new Admin();
        admin.setFirstName("Estate");
        admin.setLastName("Guru");
        admin.setPassword(passwordEncoder.encode("asdasd"));
        admin.setEmail("info@estateguru.co");
        admin.setUsername("info@estateguru.co" + (new Date().getTime()));
        admin.setHidden(false);
        admin.setRole(roleRepository.findById(RoleConstants.ADMIN_ID).orElseThrow(() -> new EntityNotFoundException("Role Admin not found")));
        adminRepository.save(admin);

    }

}