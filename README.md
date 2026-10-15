# micro-1-java · catalogo-service

Microservizio **Quarkus** (Java 21) che espone il catalogo prodotti. Dati in memoria, nessun database.

| | |
|---|---|
| Porta | `8081` |
| Porta debug (dev mode) | `5006` |
| Health | `GET /q/health` |

## Requisiti

- JDK 21+
- Maven 3.9+ (`mvn -v`)

## Comandi

```powershell
mvn quarkus:dev        # avvio in dev mode con live reload (Dev UI: http://localhost:8081/q/dev-ui)
mvn test               # test
mvn package            # build: target\quarkus-app\quarkus-run.jar
java -jar target\quarkus-app\quarkus-run.jar
```

## API

| Metodo | Path | Descrizione |
|---|---|---|
| GET | `/prodotti?categoria=periferiche` | elenco (filtro opzionale) |
| GET | `/prodotti/{id}` | dettaglio, 404 se assente |
| POST | `/prodotti` | crea: `{"nome":"Webcam","categoria":"periferiche","prezzo":49.99}` |

```powershell
curl.exe http://localhost:8081/prodotti
```
