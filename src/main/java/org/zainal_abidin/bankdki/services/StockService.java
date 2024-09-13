/**
 *
 * @author zainal
 */
package org.zainal_abidin.bankdki.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.zainal_abidin.bankdki.entities.Stock;
import org.zainal_abidin.bankdki.repositories.StockRepository;


@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
    public Stock saveStock(Stock stock) {
        try {
            return stockRepository.save(stock);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save stock: " + e.getMessage());
        }
    }
    
    public List<Stock> fetchAllStocks() {
        try {
            return stockRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all stocks: " + e.getMessage());
        }
    }
    
    public Optional<Stock> fetchStockById(Long id) {
        try {
            return stockRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch stock by ID: " + e.getMessage());
        }
    }
    
    public Optional<Stock> updateStock(Long id, Stock updatedStock) {
        try {
            Optional<Stock> existingProductOptional = stockRepository.findById(id);
            if (existingProductOptional.isPresent()) {
                Stock stock = existingProductOptional.get();
                stock.setNamaBarang(updatedStock.getNamaBarang());
                stock.setJumlahStokBarang(updatedStock.getJumlahStokBarang());
                stock.setNomorSeriBarang(updatedStock.getNomorSeriBarang());
                stock.setAdditionalInfo(updatedStock.getAdditionalInfo());
                stock.setGambarBarang(updatedStock.getGambarBarang());
                stock.setCreatedAt(updatedStock.getCreatedAt());
                stock.setCreatedBy(updatedStock.getCreatedBy());
                stock.setUpdatedAt(updatedStock.getUpdatedAt());
                stock.setUpdatedBy(updatedStock.getUpdatedBy());
                Stock savedEntity = stockRepository.save(stock);
                return Optional.of(savedEntity);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update stock: " + e.getMessage());
        }
    }
    
    public boolean deleteStock(Long id) {
        try {
            stockRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete stock: " + e.getMessage());
        }
    }
}
