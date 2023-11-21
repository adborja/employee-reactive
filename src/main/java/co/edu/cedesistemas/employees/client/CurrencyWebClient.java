package co.edu.cedesistemas.employees.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CurrencyWebClient implements CurrencyClient {
    private static final String CURRENCY_URL = "https://cdn.jsdelivr.net";
    private static final String LATEST_USD_RESOURCE = "/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json";
    private static final WebClient currencyWebClient;

    static {
        currencyWebClient = WebClient.create(CURRENCY_URL);
    }

    @Override
    public Double getExchange(String currency) {
        return getUSDCurrency().getUsd().get(currency);
    }

    private Currency getUSDCurrency() {
        return currencyWebClient
                .get()
                .uri(LATEST_USD_RESOURCE)
                .exchangeToMono(CurrencyWebClient::handleResponse)
                .share()
                .block();
    }

    private static Mono<Currency> handleResponse(ClientResponse resp) {
        if (resp.statusCode().equals(HttpStatus.OK)) {
            return resp.bodyToMono(Currency.class);
        } else {
            return resp.createException().flatMap(Mono::error);
        }
    }
}
