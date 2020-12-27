package me.acs;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;


public class SpringReactive {

  private static void checkStream() {
    Flux<String> fruitFlux = Flux
      .just("Apple", "Orange", "Grape", "Banana", "Strawberry");

    StepVerifier.create(fruitFlux)
      .expectNext("Apple")
      .expectNext("Orange")
      .expectNext("Grape")
      .expectNext("Banana")
      .expectNext("Strawberry")
      .verifyComplete();
  }

  private static void mergeStreams() {
    Flux<String> characterFlux = Flux
      .just("Garfield", "Kojak", "Barbossa")
      .delayElements(Duration.ofMillis(500));

    Flux<String> foodFlux = Flux
      .just("Lasagna", "Lollipops", "Apples")
      .delaySubscription(Duration.ofMillis(250))
      .delayElements(Duration.ofMillis(500));

    Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

    mergedFlux.log().subscribe(System.out::println);

    StepVerifier.create(mergedFlux.share())
      .expectNext("Garfield")
      .expectNext("Lasagna")
      .expectNext("Kojak")
      .expectNext("Lollipops")
      .expectNext("Barbossa")
      .expectNext("Apples")
      .verifyComplete();

  }

  private static void flatMaps() {
    Flux.just(
      "apple", "orange", "banana", "kiwi", "strawberry")
      .buffer(3)
      .flatMap(x ->
        Flux.fromIterable(x)
          .map(y -> y.toLowerCase())
          .subscribeOn(Schedulers.parallel())
          .log()
      )
      .blockLast();
  }

  public static void main(String args[]) {
    System.out.println("Samples from Spring in Action Reactive chapters");

    // mergeStreams();

    flatMaps();
  }

}
