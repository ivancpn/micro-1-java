package com.example.catalogo;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Catalogo in memoria: nessun database, i dati si azzerano a ogni riavvio.
 */
@ApplicationScoped
public class CatalogoService {

    private final Map<Long, Prodotto> prodotti = new ConcurrentHashMap<>();
    private final AtomicLong sequenza = new AtomicLong();

    public CatalogoService() {
        aggiungi(new Prodotto(null, "Tastiera meccanica", "periferiche", new BigDecimal("89.90")));
        aggiungi(new Prodotto(null, "Mouse wireless", "periferiche", new BigDecimal("34.50")));
        aggiungi(new Prodotto(null, "Monitor 27 pollici", "monitor", new BigDecimal("249.00")));
    }

    public List<Prodotto> tutti(String categoria) {
        return prodotti.values().stream()
                .filter(p -> categoria == null || categoria.equalsIgnoreCase(p.categoria()))
                .sorted(Comparator.comparing(Prodotto::id))
                .toList();
    }

    public Optional<Prodotto> perId(Long id) {
        return Optional.ofNullable(prodotti.get(id));
    }

    public Prodotto aggiungi(Prodotto nuovo) {
        long id = sequenza.incrementAndGet();
        Prodotto salvato = new Prodotto(id, nuovo.nome(), nuovo.categoria(), nuovo.prezzo());
        prodotti.put(id, salvato);
        return salvato;
    }
}
