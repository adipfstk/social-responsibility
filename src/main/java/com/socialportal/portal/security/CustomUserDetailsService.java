package com.socialportal.portal.security;

import com.socialportal.portal.model.user.Roles;
import com.socialportal.portal.model.user.UserEntity;
import com.socialportal.portal.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserEntityRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.repository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Cannot find the user in db"));
        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(mapRolesToGrantedAuthorities(userEntity.getRoles()))
                .build();

    }

    public List<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Set<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

}
