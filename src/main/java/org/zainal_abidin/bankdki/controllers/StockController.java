/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.controllers;

import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/stock")
public class StockController {
    private static final Logger log = LogManager.getLogger(StockController.class);
    
    @PostMapping("/create-stock")
    public String createStock(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.info("accessing /stock/create-stock");
        return String.format("Hello %s!", name);
    }
    
    @GetMapping("/list-stock")
    public String listStock(@RequestParam(value = "page", defaultValue = "") String page) {
        log.info("accessing /stock/list-stock");
        return String.format("Hello %s!", page);
    }
    
    @GetMapping("/detail-stock")
    public String detailStock(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.info("accessing /stock/detail-stock");
        return String.format("Hello %s!", name);
    }
    
    @PutMapping("/update-stock")
    @PatchMapping("/update-stock")
    public String updateStock(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.info("accessing /stock/update-stock");
        return String.format("Hello %s!", name);
    }
    
    @PostMapping("/delete-stock")
    public String deleteStock(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.info("accessing /stock/delete-stock");
        return String.format("Hello %s!", name);
    }
}
