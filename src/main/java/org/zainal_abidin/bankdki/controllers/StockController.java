/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.controllers;

import jakarta.persistence.criteria.Path;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import javax.print.attribute.standard.Media;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.zainal_abidin.bankdki.dto.CreateStockDto;
import org.zainal_abidin.bankdki.entities.Stock;
import org.zainal_abidin.bankdki.services.StockService;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    private static final Logger log = LogManager.getLogger(StockController.class);
    
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    
    @PostMapping("/create-stock")
    public ResponseEntity<String> createStock(
            @RequestParam("nama_barang") String namaBarang,
            @RequestParam("jumlah_stok_barang") int jumlahStokBarang,
            @RequestParam("nomor_seri_barang") String nomorSeriBarang,
            @RequestParam("additional_info") String additionalInfo,
            @RequestPart("gambar_barang") MultipartFile gambarBarang,
            HttpServletRequest request) throws IOException, NoSuchAlgorithmException {

        log.info("%s accessing /api/stock/create-stock".formatted(request.getRemoteAddr()));
        
        CreateStockDto stockDto = new CreateStockDto();
        stockDto.setNamaBarang(namaBarang);
        stockDto.setJumlahStokBarang(jumlahStokBarang);
        stockDto.setNomorSeriBarang(nomorSeriBarang);
        stockDto.setAdditionalInfo(additionalInfo);
        
        String mimeType = gambarBarang.getContentType();
        if (Objects.equals(mimeType, "image/jpeg") || Objects.equals(mimeType, "image/png")) {
            String extension = mimeType.equals("image/jpeg") ? ".jpg" : ".png";

            LocalDateTime now = LocalDateTime.now();
            String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(timestamp.getBytes());
            StringBuilder md5Hash = new StringBuilder();
            for (byte b : hash) {
                md5Hash.append(String.format("%02x", b));
            }

            String newFileName = md5Hash + extension;

            java.nio.file.Path uploadDir = Paths.get("uploads/");
            java.nio.file.Path filePath = uploadDir.resolve(newFileName);
            Files.copy(gambarBarang.getInputStream(), filePath);

            stockDto.setGambarBarang(filePath.toString());
        } else {
            throw new IOException("File harus dalam format JPG atau PNG.");
        }
        
        stockDto.setCreatedAt(LocalDateTime.MAX);
        stockDto.setCreatedBy(1);
        stockDto.setUpdatedAt(LocalDateTime.MAX);
        stockDto.setUpdatedBy(1);
        Stock stock = stockService.saveStock(stockDto);
        
        return new ResponseEntity<>("Stock created successfully with ID: " + stock.getIdBarang(), HttpStatus.OK);
    }
    
    @GetMapping("/list-stock")
    public ResponseEntity<List<Stock>> listStock(HttpServletRequest request) {
        log.info("%s accessing /api/stock/list-stock".formatted(request.getRemoteAddr()));
        
        List<Stock> stocks = stockService.fetchAllStocks();
        return ResponseEntity.ok(stocks);
    }
    
    @GetMapping("/detail-stock/{stockId}")
    public ResponseEntity<Stock> detailStock(@PathVariable Long id, HttpServletRequest request) {
        log.info("%s accessing /api/stock/detail-stock".formatted(request.getRemoteAddr()));
        
        Optional<Stock> stockOptional = stockService.fetchStockById(id);
        
        return stockOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/update-stock/{stockId}")
    public ResponseEntity<Stock> updateProduct(@PathVariable Long id, @RequestBody Stock stock, HttpServletRequest request) {
        log.info("%s accessing /api/stock/update-stock".formatted(request.getRemoteAddr()));
        
        stock.setUpdatedAt(LocalDateTime.MAX);
        stock.setUpdatedBy(5);
        Optional<Stock> stockOptional = stockService.updateStock(id, stock);
        
        return stockOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/delete-stock/{stockId}")
    public ResponseEntity<String> deleteStock(@PathVariable Long id, HttpServletRequest request) {
        log.info("%s accessing /api/stock/delete-stock".formatted(request.getRemoteAddr()));
        
        boolean status = stockService.deleteStock(id);
        
        if (status) {
            return ResponseEntity.ok("Stock with ID " + id + " has been deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product with ID " + id);
        }
    }
}
