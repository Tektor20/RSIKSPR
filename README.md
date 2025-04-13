# RSIKSPR Project - Message API

## Opis

Jednostavna Spring Boot aplikacija koja demonstrira:
- REST API endpoint (`POST /v1/send`) za primanje JSON payload-a (poruke) i spremanje u H2 in-memory bazu podataka koristeći Spring Data JPA.
- REST health check endpoint (`GET /health`).
- Automatski generiranu OpenAPI (Swagger UI) dokumentaciju za API.
- Integracijske testove za API endpoint.

## Preduslovi

- **JDK (Java Development Kit):** Verzija 17 ili novija.
- **Maven** (npr. 3.8+) ili **Gradle** (npr. 7.5+): Ovisno o tome koji build alat koristite.
- **IDE (Opcionalno ali preporučeno):** IntelliJ IDEA, Eclipse, VS Code s Java proširenjima.
- **Alat za testiranje API-ja (Opcionalno):** Postman, Insomnia, `curl` ili IntelliJ HTTP Client.

## Izgradnja Projekta

Otvorite shell terminal ili command prompt u root direktoriju projekta i pokrenite:

- **Maven:**
    ```bash
    mvn clean install
    ```
- **Gradle:**
    ```bash
    gradle build
    ```
Ovo će kompalirati kod, pokrenuti testove i kreirati izvršnu JAR datoteku (obično u `target/` ili `build/libs/` direktoriju).

## Pokretanje Aplikacije

Aplikaciju možete pokrenuti na nekoliko načina:

1.  **Iz IntelliJ IDEA:**
    - Pronađite klasu `RsiksprProjektApplication.java`.
    - Desnom tipkom miša kliknite na nju i odaberite "Run 'RsiksprProjektApplication'".

2.  **Preko Build Alata:**
    - **Maven:**
        ```bash
        mvn spring-boot:run
        ```
    - **Gradle:**
        ```bash
        gradle bootRun
        ```

3.  **Pokretanjem JAR datoteke:**
    - Nakon buildanja (vidi korak "Buildanje Projekta"), pronađite JAR datoteku (npr. `target/rsikspr_projekt-0.0.1-SNAPSHOT.jar`).
    - Pokrenite je iz terminala:
        ```bash
        java -jar target/rsikspr_projekt-0.0.1-SNAPSHOT.jar
        ```
      

Aplikacija će se po defaultu pokrenuti na `http://localhost:8080`.

## Korištenje API Endpoints-a

Nakon pokretanja aplikacije, dostupni su sljedeći endpointi:

1.  **Health Check:**
    - **URL:** `http://localhost:8080/health`
    - **Metoda:** `GET`
    - **Opis:** Vraća osnovni status aplikacije i baze podataka (ako je konfigurirana).

2.  **Slanje Poruke:**
    - **URL:** `http://localhost:8080/v1/send`
    - **Metoda:** `POST`
    - **Content-Type:** `application/json`
    - **Tijelo Zahtjeva (Request Body - Primjer):**
        ```json
        {
          "message": "Vaša poruka ovdje"
        }
        ```
    - **Odgovor (Response - Primjer uspjeha - Status 201):**
        ```json
        {
          "id": 1,
          "content": "Vaša poruka ovdje",
          "receivedAt": "2025-04-13T22:39:00.123456"
        }
        ```
    - **Primjer `curl` naredbe:**
        ```bash
        curl.exe -X POST http://localhost:8080/v1/send -H "Content-Type: application/json" -d '{"message":"Testna poruka"}'
        ```
        (Operzno definirajte varijable ako koristite `curl` u PowerShellu).

## Dodatni Resursi

1.  **Swagger UI (OpenAPI Dokumentacija):**
    - **URL:** `http://localhost:8080/swagger-ui.html`
    - **Opis:** Interaktivna dokumentacija gdje možete vidjeti sve dostupne endpointe, njihove parametre, tijela zahtjeva/odgovora i direktno ih testirati iz preglednika.

2.  **H2 Konzola (Pristup In-Memory Bazi):**
    - **URL:** `http://localhost:8080/h2-console`
    - **Opis:** Web sučelje za pregledavanje sadržaja H2 in-memory baze podataka.
    - **Postavke za spajanje (ako koristite defaultne iz `application.properties`):**
        - **Driver Class:** `org.h2.Driver`
        - **JDBC URL:** `jdbc:h2:mem:messagedb`
        - **User Name:** `sa`
        - **Password:** `password` (ili prazno ako ste tako postavili)
    - Kliknite "Connect" ili "Test Connection" pa "Connect". Moći ćete vidjeti `MESSAGES` tablicu i njen sadržaj.

## Pokretanje Testova

Za pokretanje svih automatiziranih testova (uključujući integracijske):

- **Maven:**
    ```bash
    mvn test
    ```
- **Gradle:**
    ```bash
    gradle test
    ```

Testovi će se izvršiti, a rezultati će biti prikazani u konzoli i/ili u generiranim izvještajima.
