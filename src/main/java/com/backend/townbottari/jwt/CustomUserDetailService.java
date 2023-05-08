package com.backend.townbottari.jwt;

import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User findUser = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new NotFoundException());
        return UserPrincipal.create(findUser);
    }
}