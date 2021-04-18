package ru.tutikova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tutikova.dao.entity.auth.UsersEntity;
import ru.tutikova.dao.repositories.UsersRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UsersRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
/*    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersEntity user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    //TODO: здесь можно добавит методы получения всех пользователей, удаления, добавления

}