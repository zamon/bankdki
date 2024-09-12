/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

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
    
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> additionalInfo;
    
    private String gambarBarang;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private Integer createdBy;
    
    private LocalDateTime updatedAt;
    
    private Integer updatedBy;
}