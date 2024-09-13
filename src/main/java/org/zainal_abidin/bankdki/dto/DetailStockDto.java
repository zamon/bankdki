/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailStockDto {
    private Long idBarang;
    private String namaBarang;
    private Integer jumlahStokBarang;
    private String nomorSeriBarang;
    private String additionalInfo;
    private String gambarBarang;
    private String createdAt;
    private Integer createdBy;
    private String updatedAt;
    private Integer updatedBy;
}
