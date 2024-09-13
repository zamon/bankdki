/**
 *
 * @author zainal
 */
package org.zainal_abidin.bankdki.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateStockDto {
    private String namaBarang;
    private Integer jumlahStokBarang;
    private String nomorSeriBarang;
    private String additionalInfo;
//    private MultipartFile gambarBarang;
    private String gambarBarang;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
}