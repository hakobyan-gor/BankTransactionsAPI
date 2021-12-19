package com.estateguru.banktransactions.services.user;

import com.estateguru.banktransactions.exceptions.EntityNotFoundException;
import com.estateguru.banktransactions.exceptions.MessageException;
import com.estateguru.banktransactions.exceptions.UnauthorizedException;
import com.estateguru.banktransactions.models.dtos.auth.RegisterUserDto;
import com.estateguru.banktransactions.models.dtos.user.UserCreatingDto;
import com.estateguru.banktransactions.models.dtos.user.UserDetailPreviewDto;
import com.estateguru.banktransactions.models.dtos.user.UserListPreviewDto;
import com.estateguru.banktransactions.models.entities.user.User;
import com.estateguru.banktransactions.models.entities.user.role.Role;
import com.estateguru.banktransactions.repositories.user.UserRepository;
import com.estateguru.banktransactions.repositories.user.role.RoleRepository;
import com.estateguru.banktransactions.services.AbstractService;
import com.estateguru.banktransactions.specifications.UserSpecification;
import com.estateguru.banktransactions.utils.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService, UserDetailsService {

    @Lazy
    private final BCryptPasswordEncoder mBCryptPasswordEncoder;
    @Lazy
    private final UserSpecification mUserSpecification;
    @Lazy
    private final UserRepository mUserRepository;
    @Lazy
    private final RoleRepository mRoleRepository;
    @Lazy
    private final FindUser mFindUser;

    public UserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserSpecification userSpecification,
            UserRepository userRepository,
            RoleRepository roleRepository,
            FindUser findUser
    ) {
        super(userRepository);
        mBCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mUserSpecification = userSpecification;
        mUserRepository = userRepository;
        mRoleRepository = roleRepository;
        mFindUser = findUser;
    }

    @Override
    public Long createUser(UserCreatingDto dto) throws EntityNotFoundException {
        User model = userBuilder(dto);
        String encode = mBCryptPasswordEncoder.encode(GeneratePassword.generate());
        model.setPassword(encode);
        Long id = save(model).getId();
        SendEmail.sendEmail(dto.email(), String.format("Your Username is : %s \n Your Password is : %s", model.getEmail(), encode), "Account Credentials");
        return id;
    }

    @Override
    public User getByUsernameInLogin(String username) throws UnauthorizedException {
        Optional<User> optionalUser = mUserRepository.findByEmailAndHiddenFalse(username);
        if (optionalUser.isEmpty()) {
            throw new UnauthorizedException("Username is incorrect");
        }
        return optionalUser.get();
    }

    @Override
    public Optional<User> findByUsername(String deviceId) {
        return mUserRepository.findByUsername(deviceId);
    }

    @Transactional
    @Override
    public User signUp(RegisterUserDto dto) throws MessageException, EntityNotFoundException {
        Optional<User> optionalUser = mUserRepository.findByEmailAndHiddenFalse(dto.email());
        if (optionalUser.isPresent())
            throw new MessageException("Email already taken.");
        User user = new User();
        user.setUsername(String.format("%s%s", dto.email(), System.currentTimeMillis()));
        user.setEmail(dto.email());
        user.setPassword(mBCryptPasswordEncoder.encode(dto.password()));
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setRole(mRoleRepository.findById(RoleConstants.USER_ID).orElseThrow(
                () -> new EntityNotFoundException(Role.class, "Role", String.valueOf(RoleConstants.USER_ID))));
        save(user);
        return user;
    }

    private User userBuilder(UserCreatingDto dto) throws EntityNotFoundException {
        return User
                .builder()
                .role(mRoleRepository.findById(RoleConstants.USER_ID).orElseThrow(
                        () -> new EntityNotFoundException(Role.class, "Role", String.valueOf(RoleConstants.USER_ID))))
                .username(String.format("%s%s", dto.email(), System.currentTimeMillis()))
                .password(mBCryptPasswordEncoder.encode(GeneratePassword.generate()))
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .build();
    }

    @Override
    public UserDetailPreviewDto getUserDetails() {
        User user = mFindUser.findUserByToken();
        return new UserDetailPreviewDto(
                user.getFirstName(),
                user.getLastName(),
                user.getRole().getRoleName(),
                user.getId()
        );
    }

    @Override
    public Page<UserListPreviewDto> getUsers(int page, int size) {
        return new PageImpl<>(mUserSpecification.getUsers(page, size), PageRequest.of(page - 1, size), mUserSpecification.countOfUsers());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = mUserRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UnauthorizedException("Username doesn't exist.");
        User user = optionalUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }

}