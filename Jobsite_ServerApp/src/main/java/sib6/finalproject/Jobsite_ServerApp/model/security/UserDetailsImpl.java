package sib6.finalproject.Jobsite_ServerApp.model.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sib6.finalproject.Jobsite_ServerApp.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    @Autowired
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles()
                .forEach(role -> {
                    String roles = "ROLE_" + role.getName().name().toUpperCase();
                    grantedAuthorities.add(new SimpleGrantedAuthority(roles));
                    role.getPrivileges()
                            .forEach(privilege -> {
                                String privileges = privilege.getName().name().toUpperCase();
                                grantedAuthorities.add(new SimpleGrantedAuthority(privileges));
                            });
                });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
