/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarang;

    @Column(nullable = false)
    private String namaBarang;
    
    @Column(nullable = false)
    private int jumlahStokBarang;
    
    @Column(nullable = false, unique = true)
    private String nomorSeriBarang;
    
    @Lob
    @Column(columnDefinition = "CLOB")
    private String additionalInfo;
    
    private String gambarBarang;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private Integer createdBy;
    
    private LocalDateTime updatedAt;
    
    private Integer updatedBy;
}