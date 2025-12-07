package com.woofly.courseservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // --- DÜZƏLİŞLƏR BURADAN BAŞLAYIR ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Rolu Spring Security-nin başa düşəcəyi formata çeviririk
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password; // DİQQƏT: Boş "" yox, real password sahəsini qaytarın
    }

    @Override
    public String getUsername() {
        return email; // DİQQƏT: Login üçün email istifadə edirsinizsə, bunu qaytarın
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hesabın vaxtı keçməyib
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Hesab kilidlənməyib
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Şifrənin vaxtı bitməyib
    }

    @Override
    public boolean isEnabled() {
        return true; // Hesab aktivdir
    }
}