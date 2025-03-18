package com.example.sap1701_team1.fptmentorlink.models.entity_models;

import com.example.sap1701_team1.fptmentorlink.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`account`")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String email;
    private String userCode;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullname;
    private String phone;
    private boolean is_active;

    @OneToMany(mappedBy = "account")
    private List<Report> reportList;

    @OneToMany(mappedBy = "account")
    private List<Wallet> walletList;

    @OneToMany(mappedBy = "account")
    private List<Chat> chatList;

    @OneToMany(mappedBy = "account")
    private List<Notification> notificationList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
