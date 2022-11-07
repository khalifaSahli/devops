package com.esprit.examen.services;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;

import com.esprit.examen.entities.Produit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.esprit.examen.entities.Stock;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class StockServiceImplTest {
	@Autowired
	IStockService stockService;

	Stock s = new Stock("stock test",110,8,new HashSet<Produit>());
	@Order(0)
	@Test
	public void testListStock() {
		s.getProduits().add(new Produit());
		Stock savedStock= stockService.addStock(s);
		List<Stock> stocks = stockService.retrieveAllStocks();
		assertNotNull(stocks);


	}
    @Order(1)
	@Test
	public void testAddStock() {
		List<Stock> stocks = stockService.retrieveAllStocks();
		int expected=stocks.size();
		s.getProduits().add(new Produit());
		Stock savedStock= stockService.addStock(s);
		assertEquals(expected+1, stockService.retrieveAllStocks().size());
		assertNotNull(savedStock.getLibelleStock());
		assertSame(110, savedStock.getQte());
		assertTrue(savedStock.getQteMin()>0);
        log.info(" stock  :" + savedStock.toString());
		stockService.deleteStock(savedStock.getIdStock());
	}
	@Order(2)
	@Test
	public void testUpdateStock() {
		Stock s = new Stock(Long.valueOf(1));
		s.setLibelleStock("stock update");
		s.setQte(5);
		s.setQteMin(50);
		Stock updateeStock= stockService.updateStock(s);
		log.info(" stock  :" + updateeStock.toString());
	}
    @Order(3)
	@Test
	public void testDeleteStock() {
		Stock savedStock= stockService.addStock(s);
		stockService.deleteStock(savedStock.getIdStock());
		assertNull(stockService.retrieveStock(savedStock.getIdStock()));
	}
	@Order(4)
	@Test
	public void testRetreveStock() {
		Stock savedStock= stockService.addStock(s);
		assertNotNull(stockService.retrieveStock(savedStock.getIdStock()));
	}
	@Order(5)
	@Test
	public void testStatusStock() {
		Stock savedStock= stockService.addStock(s);
		assertNotNull(stockService.retrieveStatusStock());
	}

}
