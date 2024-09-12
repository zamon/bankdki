/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.controllers;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stock")
public class StockController {
    @PostMapping("/create-stock")
    public String create_stock(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }
    
    @GetMapping("/list-stock")
    public String list_stock(@RequestParam(value = "page", defaultValue = 0) int page, 
            @RequestParam(value = "per-page", defaultValue = 15) int perPage) {
      return String.format("Hello %s!", page.toString());
    }
    
    @GetMapping("/detail-stock")
    public String detail_stock(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }
    
    @PutMapping("/update-stock")
    @PatchMapping("/update-stock")
    public String update_stock(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }
    
    @PostMapping("/delete-stock")
    public String delete_stock(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }
}
