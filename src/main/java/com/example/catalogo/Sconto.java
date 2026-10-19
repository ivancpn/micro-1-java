package com.example.catalogo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Prezzo scontato di un prodotto, con percentuale tra 0 e 90.
 */
public record Sconto(Long prodottoId, BigDecimal prezzoPieno, int percentuale, BigDecimal prezzoScontato) {

    public static Sconto applica(Prodotto prodotto, int percentuale) {
        if (percentuale < 0 || percentuale > 90) {
            throw new IllegalArgumentException("percentuale deve essere tra 0 e 90");
        }
        BigDecimal fattore = BigDecimal.valueOf(100 - percentuale).divide(BigDecimal.valueOf(100));
        BigDecimal scontato = prodotto.prezzo().multiply(fattore).setScale(2, RoundingMode.HALF_UP);
        return new Sconto(prodotto.id(), prodotto.prezzo(), percentuale, scontato);
    }
}
