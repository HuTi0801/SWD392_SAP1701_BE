package com.example.sap1701_team1.fptmentorlink.models.entity_models;

import com.example.sap1701_team1.fptmentorlink.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`account`")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String email;

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
}
