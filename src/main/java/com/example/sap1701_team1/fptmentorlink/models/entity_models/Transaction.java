package com.example.sap1701_team1.fptmentorlink.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`transaction`")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String payDate;
    private String transactionNo;
    private String tmnCode;
    private String secureHash;
    private String orderInfo;
    private String txnRef;
    private String amount;
    private String cardType;
    private String transactionStatus;
    private String bankTranNo;
    private String responseCode;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
