package com.codetest.app.myretail.service;

import com.codetest.app.myretail.repository.UsersRepository;
import com.codetest.app.myretail.response.Role;
import com.codetest.app.myretail.response.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MongoUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        //List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
    }

    /**
     * Assign Roles to the user.
     *
     * @param user
     * @return
     */
    private Collection<GrantedAuthority> getGrantedAuthorities(Users user) {
        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
        List<Role> roles = user.getRoles();

        grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
        for (Role role : roles) {
            if (role.getName().equalsIgnoreCase("admin")) {
                grantedAuthority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }

        return grantedAuthority;
    }

}