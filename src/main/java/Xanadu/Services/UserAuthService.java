package Xanadu.Services;

import Xanadu.Entities.Role;
import Xanadu.Entities.User;
import Xanadu.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user ==null){
            throw new UsernameNotFoundException(username);
        }
        return org.springframework.security.core.userdetails.User
                .builder()
                .password(user.getPassword())
                .username(user.getUsername())
                .authorities(Role.ADMIN.name())
                .build();
    }
}
