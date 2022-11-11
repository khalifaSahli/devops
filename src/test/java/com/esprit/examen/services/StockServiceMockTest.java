package com.esprit.examen.services;
import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockServiceMockTest {
    @Mock
    StockRepository StockRepo;

    @InjectMocks
    StockServiceImpl StockSer;

    List<Stock> listStock = new ArrayList<Stock>() {
        {
            add(Stock.builder().idStock(1l).libelleStock("lib").qte(122)
                    .qteMin(100).produits((Set<Produit>) listp)
                    .build());
            add(Stock.builder().idStock(2l).libelleStock("lib2").qte(1333)
                    .qteMin(33).produits((Set<Produit>) listp)
                    .build());
        }
    };

    List<Produit> listp = new ArrayList<Produit>() {
        {
            add(Produit.builder().codeProduit("20P").libelleProduit("libP205").prix(56)
                    .dateCreation(new Date(2022-10-12)).dateDerniereModification(new Date(2022-10-11))
                    .build());
        }
    };
    Stock s =Stock.builder().idStock(1l).libelleStock("lib").qte(122)
            .qteMin(100).build();

    @Test
    @Order(2)
    public void RetreveStock() {
        Mockito.when(StockRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(s));
        Stock s = StockSer.retrieveStock(1l);
        assertNotNull(s);
        log.info("get==>"+s.toString());
        verify(StockRepo).findById(Mockito.anyLong());
    }

    @Test
    @Order(1)
    public void addStock() {
        when(StockRepo.save(s)).thenReturn(s);
        Assert.assertEquals(s,StockSer.addStock(s));
    }




    @Test
    @Order(3)
    public void getAllStock () {
        when(StockRepo.findAll()).thenReturn(listStock);

        assertEquals(2,StockSer.retrieveAllStocks().size());

        List<Stock> list =StockSer.retrieveAllStocks();
        assertNotNull(list);
        for (Stock stock : list) {
            log.info(stock.toString());
        }
    }
}

