package com.estateguru.banktransactions.services.user;

import com.estateguru.banktransactions.exceptions.EntityNotFoundException;
import com.estateguru.banktransactions.exceptions.MessageException;
import com.estateguru.banktransactions.exceptions.UnauthorizedException;
import com.estateguru.banktransactions.models.dtos.auth.RegisterUserDto;
import com.estateguru.banktransactions.models.dtos.auth.UserSendCodeDto;
import com.estateguru.banktransactions.models.dtos.user.UserCreatingDto;
import com.estateguru.banktransactions.models.dtos.user.UserDetailPreviewDto;
import com.estateguru.banktransactions.models.dtos.user.UserListPreviewDto;
import com.estateguru.banktransactions.models.entities.user.User;
import com.estateguru.banktransactions.services.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends CommonService<User> {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Long createUser(UserCreatingDto dto) throws EntityNotFoundException;

    User getByUsernameInLogin(String username) throws UnauthorizedException;

    Optional<User> findByUsername(String username);

    User signUp(RegisterUserDto userCreatingDto) throws MessageException, EntityNotFoundException;

    UserDetailPreviewDto getUserDetails();

    Page<UserListPreviewDto> getUsers(int page, int size);

}