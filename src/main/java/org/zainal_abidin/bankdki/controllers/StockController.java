/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.controllers;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import javax.print.attribute.standard.Media;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    
    @RequestMapping(path = "/create-stock", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock, HttpServletRequest request) {
        log.info("%s accessing /api/stock/create-stock".formatted(request.getRemoteAddr()));
        
        InputStream is;
        try {
            is = new BufferedInputStream(new FileInputStream(stock.getGambarBarang()));
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            log.info(mimeType);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
        
        stock.setCreatedAt(LocalDateTime.MAX);
        stock.setCreatedBy(5);
        stock.setUpdatedAt(LocalDateTime.MAX);
        stock.setUpdatedBy(5);
        Stock saved = stockService.saveStock(stock);
        return ResponseEntity.ok(saved);
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
