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
public class UpdateStockDto {
    private Long idBarang;
    private String namaBarang;
    private Integer jumlahStokBarang;
    private String nomorSeriBarang;
    private String additionalInfo;
    private String gambarBarang;
    private LocalDateTime updatedAt;
    private Integer updatedBy;
}
