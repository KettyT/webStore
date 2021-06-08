package ru.tiutikova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tiutikova.UserException;
import ru.tiutikova.dao.entity.auth.SessionsEntity;
import ru.tiutikova.dao.entity.auth.UsersEntity;
import ru.tiutikova.dao.entity.cart.CartEntity;
import ru.tiutikova.dao.repositories.SessionRepository;
import ru.tiutikova.dao.repositories.UsersRepository;
import ru.tiutikova.dao.repositories.cart.CartDetailsRepository;
import ru.tiutikova.dao.repositories.cart.CartNativeDao;
import ru.tiutikova.dao.repositories.cart.CartRepository;
import ru.tiutikova.dto.ResultDto;
import ru.tiutikova.dto.UserDto;
import ru.tiutikova.dto.auth.AuthDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
//    @Autowired
    UsersRepository userRepository;

//    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private SessionRepository sessionRepository;

    private CartRepository cartRepository;

    private CartDetailsRepository cartDetailsRepository;

    private CartNativeDao cartNativeDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UsersRepository userRepository, SessionRepository sessionRepository, CartDetailsRepository cartDetailsRepository,
                       CartRepository cartRepository, CartNativeDao cartNativeDao) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.cartNativeDao = cartNativeDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersEntity user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


    public UserDto getUserBySessionCode (String sessionCode) {
        UsersEntity usersEntity = userRepository.getBySessionCode(sessionCode);

        if (usersEntity == null) {
            return null;
        }

        return new UserDto(usersEntity);
    }

    private void handleAllUserCarts (SessionsEntity session) {

        CartEntity mainCart = cartRepository.getByUserId(session.getUserId());
        CartEntity secondCart = cartRepository.getBySessionId(session.getId());

        if (mainCart == null && secondCart == null) {
            return;
        }

        if (mainCart == null && secondCart != null || (mainCart != null && secondCart != null && Objects.equals(mainCart.getId(), secondCart.getId()))) {
            mainCart = secondCart;
            mainCart.setUserId(session.getUserId());

            cartRepository.save(mainCart);
            return;
        }

        if (mainCart != null && secondCart == null) {
            mainCart.setUserId(session.getUserId());

            cartRepository.save(mainCart);
            return;
        }

        if (mainCart != null && secondCart != null) {
            cartNativeDao.moveStoredDetails(secondCart.getId(), mainCart.getId());
            cartRepository.deleteById(secondCart.getId());
        }
    }

    public UserDto getProfile() {
        return (UserDto)SecurityContextHolder.getContext().getAuthentication();
    }

    public ResultDto saveProfile(UserDto dto) {

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new UserException("Поле \"Имя\" не может быть пустым");
        }

        if (dto.getSurname() == null || dto.getSurname().isEmpty()) {
            throw new UserException("Поле \"Фамилия\" не может быть пустым");
        }

        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
            throw new UserException("Поле \"Телефон\" не может быть пустым");
        }

        UserDto existingUserDto = (UserDto)SecurityContextHolder.getContext().getAuthentication();

        UsersEntity usersEntity = new UsersEntity();

        usersEntity.setId(existingUserDto.getId());
        usersEntity.setName(dto.getName());
        usersEntity.setSurname(dto.getSurname());
        usersEntity.setEmail(existingUserDto.getEmail());
        usersEntity.setPassword(existingUserDto.getPassword());
        usersEntity.setPhone(dto.getPhone());

        userRepository.save(usersEntity);

        return new ResultDto(true, "Информация успешно сохранена");
    }

    @Transactional
    public ResultDto login(AuthDto dto) {

        UsersEntity userDetails;
        try {
            userDetails = (UsersEntity)loadUserByUsername(dto.getEmail());
        } catch (UsernameNotFoundException ex) {
            throw new UserException("Не верный логин или пароль");
        }

//        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
            throw new UserException("Не верный логин или пароль");
        }

        UserDto storedUserDetails = (UserDto)SecurityContextHolder.getContext().getAuthentication();

//        sessionRepository.updateUserId(userDetails.getId(), storedUserDetails.getSessionId());

        SessionsEntity session = sessionRepository.getBySessionCode(storedUserDetails.getSessionId());

        if (session == null) {
            session = new SessionsEntity();
            session.setSessionCode(storedUserDetails.getSessionId());
        }
        session.setUserId(userDetails.getId());

        sessionRepository.save(session);

        handleAllUserCarts (session);

        return new ResultDto(true);
    }

    public ResultDto logout() {
        UserDto userDto = getProfile();
        String sessionId = userDto.getSessionId();

        SessionsEntity sessionsEntity = sessionRepository.getBySessionCode(sessionId);
        sessionsEntity.setUserId(null);
        sessionRepository.save(sessionsEntity);

        return new ResultDto(true);
    }

}