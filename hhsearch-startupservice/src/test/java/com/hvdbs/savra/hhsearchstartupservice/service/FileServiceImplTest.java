package com.hvdbs.savra.hhsearchstartupservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileServiceImplTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test() {
        Flux<Integer> limit = Flux.range(1, 25);

        limit.limitRate(10);
        limit.subscribe(
                value -> System.out.println(value),
                err -> err.printStackTrace(),
                () -> System.out.println("Finished!!"),
                subscription -> subscription.request(15)
        );

        StepVerifier.create(limit)
                .expectSubscription()
                .thenRequest(15)
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .expectNext(11, 12, 13, 14, 15)
                .thenRequest(10)
                .expectNext(16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
                .verifyComplete();

     /*   webTestClient.post()
                .uri("/api/v1/search/vacancies/{keyword}")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();*/

        Flux<Object> objectFlux = Flux.fromStream(Stream.iterate(1, (i) -> i + 1).limit(9))
                .flatMap(keyword ->
                        {
                            webTestClient.post()
                                    .uri("/api/v1/search/vacancies/{keyword}")
                                    .accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody().isEmpty();
                            return Mono.empty();
                        }
                )
                .doFinally(signalType -> System.out.println("Файл прочитан, данные отправлены в searchservice"))
                .limitRate(3);

        StepVerifier.create(objectFlux)
                .expectSubscription()
                .thenRequest(20)
                .expectNext(1, 2, 3)
                .expectNext(11, 12, 13, 14, 15)
                .thenRequest(10)
                .expectNext(16, 17, 18, 19, 20, 21, 22, 23, 24, 25);
     /*
        StepVerifier
                .create(objectFlux)
                .expectNext("JOHN")
                .expectNextMatches(name -> name.startsWith("MA"))
                .expectNext("CLOE", "CATE")
                .expectComplete()
                .verify();*/
    }
}