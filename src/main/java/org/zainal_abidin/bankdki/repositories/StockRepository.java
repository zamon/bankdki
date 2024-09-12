/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki.repositories;

import org.zainal_abidin.bankdki.entities.Stock;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class StockRepository {
    @PersistenceContext
    private EntityManager entityManager;

    // Menggunakan Query Builder
    public List<Stock> findStocksByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);
        Root<Stock> stock = cq.from(Stock.class);
        cq.select(stock).where(cb.equal(stock.get("namaBarang"), name));
        return entityManager.createQuery(cq).getResultList();
    }
}
