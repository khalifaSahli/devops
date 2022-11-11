package com.esprit.examen.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

	@Autowired
	StockRepository stockRepository;


	@Override
	public List<Stock> retrieveAllStocks() {
		// récuperer la date à l'instant t1
		log.info("In method retrieveAllStocks");
		List<Stock> stocks =  stockRepository.findAll();
		for (Stock stock : stocks) {
			log.info(" Stock : " + stock);
		}
		log.info("out of method retrieveAllStocks");
		// récuperer la date à l'instant t2
		// temps execution = t2 - t1
		return stocks;
	}

	@Override
	public Stock addStock(Stock s) {
		// récuperer la date à l'instant t1
		log.info("In method addStock");
	        stockRepository.save(s);
		return s ;
		
	}

	@Override
	public void deleteStock(Long stockId) {
		log.info("In method deleteStock");
		stockRepository.deleteById(stockId);

	}

	@Override
	public Stock updateStock(Stock s) {
		log.info("In method updateStock");
		 stockRepository.save(s);
		return s ;
	}

	@Override
	public Stock retrieveStock(Long stockId) {
		long start = System.currentTimeMillis();
		log.info("In method retrieveStock");
		Stock stock = stockRepository.findById(stockId).orElse(null);
		log.info("stock:"+stock);
		log.info("out of method retrieveStock");
		 long elapsedTime = System.currentTimeMillis() - start;
		log.info("Method execution time: " + elapsedTime + " milliseconds.");

		return stock;
	}

	@Override
	public String retrieveStatusStock() {

		String sentence = "le stock:                                                                                                               " +
				"                                ";
		StringBuilder builder = new StringBuilder(sentence);
		List<Stock> stocksEnRouge = stockRepository.retrieveStatusStock();
		for (int i = 0; i < stocksEnRouge.size(); i++) {
			builder.insert(10, stocksEnRouge.get(i).getLibelleStock());
			builder.insert(10+stocksEnRouge.get(i).getLibelleStock().length(), " a une quantité de " + stocksEnRouge.get(i).getQte());
			builder.insert(49, " inférieur à la quantité minimale a ne pas dépasser de " + stocksEnRouge.get(i).getQte());

		}
		String str = builder.toString();

		log.info(str);
		return str;
	}

}
