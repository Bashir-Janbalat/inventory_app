package org.inventory.app.repository;

import org.inventory.app.model.Stock;
import org.inventory.app.model.StockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, StockId> {
}