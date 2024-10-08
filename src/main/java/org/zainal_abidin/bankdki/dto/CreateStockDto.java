/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStockDto {
    private String namaBarang;
    private Integer jumlahStokBarang;
    private String nomorSeriBarang;
    private String additionalInfo;
    private String gambarBarang;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
}