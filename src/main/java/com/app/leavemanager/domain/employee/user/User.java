package com.app.leavemanager.domain.employee.user;

import com.app.leavemanager.configurations.security.model.token.Token;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_email")
    private String email;
    @Column(name = "c_password")
    private String password;
    @Column(name = "c_role")
    @Enumerated(EnumType.STRING)
    private Scope role;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    @Builder.Default
    @Column(name = "c_enabled")
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isEnabled;
    }

    public boolean hasRole(Scope scope) {
        return Objects.equals(role, scope);
    }
}
