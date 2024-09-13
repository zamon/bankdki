/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "STOCK")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idBarang;

    @Column(nullable = false)
    private String namaBarang;
    
    @Column(nullable = false)
    private int jumlahStokBarang;
    
    @Column(nullable = false, unique = true)
    private String nomorSeriBarang;
    
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "clob")
    private Map<String, Object> additionalInfo;
    
    private String gambarBarang;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private Integer createdBy;
    
    private LocalDateTime updatedAt;
    
    private Integer updatedBy;
}