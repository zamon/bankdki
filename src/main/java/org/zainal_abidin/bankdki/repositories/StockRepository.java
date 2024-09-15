/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zainal_abidin.bankdki.entities.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Page<Stock> findAll(Pageable pageable);
}