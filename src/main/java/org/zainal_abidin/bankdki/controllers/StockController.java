/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.zainal_abidin.bankdki.dto.ApiResponse;
import org.zainal_abidin.bankdki.dto.CreateStockDto;
import org.zainal_abidin.bankdki.dto.DetailStockDto;
import org.zainal_abidin.bankdki.dto.ListStockDto;
import org.zainal_abidin.bankdki.dto.UpdateStockDto;
import org.zainal_abidin.bankdki.entities.Stock;
import org.zainal_abidin.bankdki.services.StockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private static final Logger log = LogManager.getLogger(StockController.class);
    private final String uploadDir = "uploads/";
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    
    @PostMapping("/create-stock")
    public ResponseEntity<ApiResponse> createStock(
            @RequestParam("nama_barang") String namaBarang,
            @RequestParam("jumlah_stok_barang") int jumlahStokBarang,
            @RequestParam("nomor_seri_barang") String nomorSeriBarang,
            @RequestParam("additional_info") String additionalInfo,
            @RequestPart("gambar_barang") MultipartFile gambarBarang,
            @RequestPart("created_by") int createdBy,
            @RequestPart("updated_by") int updatedBy,
            HttpServletRequest request) throws IOException, NoSuchAlgorithmException {

        log.info("%s accessing /api/stock/create-stock".formatted(request.getRemoteAddr()));
        ApiResponse apiResponse = new ApiResponse(200, "data berhasil disimpan");
        
        CreateStockDto createDto = new CreateStockDto();
        createDto.setNamaBarang(namaBarang);
        createDto.setJumlahStokBarang(jumlahStokBarang);
        createDto.setNomorSeriBarang(nomorSeriBarang);
        createDto.setAdditionalInfo(additionalInfo);
        
        String mimeType = gambarBarang.getContentType();
        if (Objects.equals(mimeType, "image/jpeg") || Objects.equals(mimeType, "image/png")) {
            String originalFilename = gambarBarang.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = generateFileName() + fileExtension;
            
            java.nio.file.Path destinationDir = Paths.get(uploadDir);
            java.nio.file.Path filePath = destinationDir.resolve(newFilename);
            Files.copy(gambarBarang.getInputStream(), filePath);

            createDto.setGambarBarang(newFilename);
        } else {
            throw new IOException("File harus dalam format JPG atau PNG.");
        }
        
        createDto.setCreatedAt(LocalDateTime.now());
        createDto.setCreatedBy(createdBy);
        createDto.setUpdatedAt(LocalDateTime.now());
        createDto.setUpdatedBy(updatedBy);
        Stock stock = stockService.saveStock(createDto);
        
        return ResponseEntity.ok(apiResponse);
    }
    
    @GetMapping("/list-stock")
    public ResponseEntity<Page<ListStockDto>> listStock(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "15") int size,
            HttpServletRequest request) {
        log.info("%s accessing /api/stock/list-stock".formatted(request.getRemoteAddr()));
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ListStockDto> stockPage = stockService.fetchAllStocks(pageable);
    
        return ResponseEntity.ok(stockPage);
    }
    
    @GetMapping("/detail-stock/{stockId}")
    public ResponseEntity<DetailStockDto> detailStock(@PathVariable Long stockId, HttpServletRequest request) {
        log.info(String.format("%s accessing /api/stock/detail-stock/%d",request.getRemoteAddr(), stockId));
        Optional<Stock> stockOptional = stockService.fetchStockById(stockId);

        return stockOptional.map(stock -> {
            DetailStockDto dto = new DetailStockDto();
            dto.setIdBarang(stock.getIdBarang());
            dto.setNamaBarang(stock.getNamaBarang());
            dto.setJumlahStokBarang(stock.getJumlahStokBarang());
            dto.setNomorSeriBarang(stock.getNomorSeriBarang());
            dto.setAdditionalInfo(stock.getAdditionalInfo());
            dto.setGambarBarang(stock.getGambarBarang());
            dto.setCreatedAt(stock.getCreatedAt().toString());
            dto.setCreatedBy(stock.getCreatedBy());
            dto.setUpdatedAt(stock.getUpdatedAt().toString());
            dto.setUpdatedBy(stock.getUpdatedBy());
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/update-stock")
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestParam("id_barang") Long idBarang,
            @RequestParam("nama_barang") String namaBarang,
            @RequestParam("jumlah_stok_barang") int jumlahStokBarang,
            @RequestParam("nomor_seri_barang") String nomorSeriBarang,
            @RequestParam("additional_info") String additionalInfo,
            @RequestPart("gambar_barang") MultipartFile gambarBarang,
            @RequestPart("updated_by") int updatedBy,
            HttpServletRequest request) {
        log.info(String.format("%s accessing /api/stock/update-stock with id %d",request.getRemoteAddr(), idBarang));
        ApiResponse apiResponse = new ApiResponse(200, "data berhasil diupdate");
        Optional<Stock> stockOptional = stockService.fetchStockById(idBarang);
        UpdateStockDto updateStockDto = new UpdateStockDto();

        if (stockOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Stock stock = stockOptional.get();

        if (gambarBarang != null && !gambarBarang.isEmpty()) {
            String mimeType = gambarBarang.getContentType();
            if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
                apiResponse.setStatus(500);
                apiResponse.setMessage("File harus berformat JPG atau PNG");
                return ResponseEntity.status(500).body(apiResponse);
            }

            String originalFilename = gambarBarang.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = generateFileName() + fileExtension;

            if (stock.getGambarBarang() != null) {
                Path existingFilePath = Paths.get(uploadDir, stock.getGambarBarang());
                try {
                    Files.deleteIfExists(existingFilePath);
                } catch (IOException e) {
                    log.error("Gagal menghapus file existing", e);
                }
            }

            try {
                Path filePath = Paths.get(uploadDir, newFilename);
                Files.write(filePath, gambarBarang.getBytes());
                updateStockDto.setGambarBarang(newFilename);
            } catch (IOException e) {
                log.error("Gagal menyimpan file", e);
                apiResponse.setStatus(500);
                apiResponse.setMessage("Gagal menyimpan file");
                return ResponseEntity.status(500).body(apiResponse);
            }
        }
        
        updateStockDto.setIdBarang(idBarang);
        updateStockDto.setNamaBarang(namaBarang);
        updateStockDto.setJumlahStokBarang(jumlahStokBarang);
        updateStockDto.setNomorSeriBarang(nomorSeriBarang);
        updateStockDto.setAdditionalInfo(additionalInfo);
        updateStockDto.setUpdatedAt(LocalDateTime.now());
        updateStockDto.setUpdatedBy(updatedBy);
        
        stockService.updateStock(updateStockDto);

        return ResponseEntity.ok(apiResponse);
    }
    
    @DeleteMapping("/delete-stock")
    public ResponseEntity<ApiResponse> deleteStock(
            @RequestParam("id_barang") Long idBarang, 
            HttpServletRequest request) {
        log.info(String.format("%s accessing /api/stock/delete-stock with id %d",request.getRemoteAddr(), idBarang));
        ApiResponse apiResponse = new ApiResponse(200, "data berhasil dihapus");
        
        Optional<Stock> stockOptional = stockService.fetchStockById(idBarang);

        if (stockOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Stock stock = stockOptional.get();

        if (stock.getGambarBarang() != null) {
            Path filePath = Paths.get(uploadDir, stock.getGambarBarang());
            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                log.error("Gagal menghapus file gambarBarang", e);
                apiResponse.setStatus(500);
                apiResponse.setMessage("Gagal menghapus file");
                
                return ResponseEntity.status(500).body(apiResponse);
            }
        }

        stockService.deleteStockById(idBarang);
        
        return ResponseEntity.ok(apiResponse);
    }
    
    private String generateFileName() {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(timestamp.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating MD5", e);
        }
    }
}
