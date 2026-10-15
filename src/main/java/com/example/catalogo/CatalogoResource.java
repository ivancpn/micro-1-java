package com.example.catalogo;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Path("/prodotti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogoResource {

    @Inject
    CatalogoService service;

    @GET
    public List<Prodotto> elenco(@QueryParam("categoria") String categoria) {
        return service.tutti(categoria);
    }

    @GET
    @Path("/{id}")
    public Response dettaglio(@PathParam("id") Long id) {
        return service.perId(id)
                .map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response crea(Prodotto prodotto) {
        if (prodotto == null || prodotto.nome() == null || prodotto.nome().isBlank()
                || prodotto.prezzo() == null || prodotto.prezzo().compareTo(BigDecimal.ZERO) < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Errore("nome e prezzo (>= 0) sono obbligatori"))
                    .build();
        }
        Prodotto salvato = service.aggiungi(prodotto);
        return Response.created(URI.create("/prodotti/" + salvato.id())).entity(salvato).build();
    }

    public record Errore(String messaggio) {
    }
}
