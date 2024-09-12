/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.entity;

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
    private Long idBarang;  // Id Barang (sequence)

    @Column(nullable = false)
    private String namaBarang;  // Nama Barang (string)

    @Column(nullable = false)
    private int jumlahStokBarang;  // Jumlah Stok Barang (integer)

    @Column(nullable = false, unique = true)
    private String nomorSeriBarang;  // Nomor Seri Barang (string)

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> additionalInfo;  // Additional Info (type JSONB)

    private String gambarBarang;  // Gambar Barang (string)

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();  // Created At (timestamp)

    private Integer createdBy;  // Created By (integer)

    private LocalDateTime updatedAt;  // Updated At (timestamp)

    private Integer updatedBy;  // Updated By (integer)
}