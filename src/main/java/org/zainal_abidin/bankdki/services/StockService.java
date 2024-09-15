/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.zainal_abidin.bankdki.dto.CreateStockDto;
import org.zainal_abidin.bankdki.dto.UpdateStockDto;
import org.zainal_abidin.bankdki.entities.Stock;
import org.zainal_abidin.bankdki.repositories.StockRepository;
import org.springframework.data.domain.Pageable;
import org.zainal_abidin.bankdki.dto.ListStockDto;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
    public Stock saveStock(CreateStockDto stockDto) {
        try {
            Stock stock = new Stock();
            stock.setNamaBarang(stockDto.getNamaBarang());
            stock.setJumlahStokBarang(stockDto.getJumlahStokBarang());
            stock.setNomorSeriBarang(stockDto.getNomorSeriBarang());
            stock.setAdditionalInfo(stockDto.getAdditionalInfo());
            stock.setGambarBarang(stockDto.getGambarBarang());
            stock.setCreatedBy(stockDto.getCreatedBy());
            stock.setCreatedAt(LocalDateTime.now());
            stock.setUpdatedBy(stockDto.getUpdatedBy());
            stock.setUpdatedAt(LocalDateTime.now());            
            return stockRepository.save(stock);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save stock: " + e.getMessage());
        }
    }
    
    public Page<ListStockDto> fetchAllStocks(Pageable pageable) {
        return stockRepository.findAll(pageable).map(stock -> {
            ListStockDto dto = new ListStockDto();
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
            return dto;
        });
    }
    
    public Optional<Stock> fetchStockById(Long id) {
        try {
            return stockRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch stock by ID: " + e.getMessage());
        }
    }
    
    public Optional<Stock> updateStock(UpdateStockDto updatedStock) {
        try {
            Optional<Stock> existingProductOptional = stockRepository.findById(updatedStock.getIdBarang());
            if (existingProductOptional.isPresent()) {
                Stock stock = existingProductOptional.get();
                stock.setNamaBarang(updatedStock.getNamaBarang());
                stock.setJumlahStokBarang(updatedStock.getJumlahStokBarang());
                stock.setNomorSeriBarang(updatedStock.getNomorSeriBarang());
                stock.setAdditionalInfo(updatedStock.getAdditionalInfo());
                stock.setGambarBarang(updatedStock.getGambarBarang());
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
    
    public void deleteStockById(Long idBarang) {
        stockRepository.deleteById(idBarang);
    }
}
