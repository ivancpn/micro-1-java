package com.example.catalogo;

import java.math.BigDecimal;

public record Prodotto(Long id, String nome, String categoria, BigDecimal prezzo) {
}
