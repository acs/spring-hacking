package me.acs;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class AllTrueLogic {

  public static void main(String args[]) {
    Flux<Flux<Integer>> inputsEvents = Flux.just(
      Flux.just(1, 2, 3),
      Flux.just(3),
      Flux.just()
    );

    Flux<Flux<Integer>> inputsEventsWithData =
      inputsEvents.filterWhen(input -> input.any(event -> true));

    System.out.println(inputsEvents.collectList().block());
    System.out.println(inputsEventsWithData.collectList().block());

    Flux<Integer> numbersNoZeros = Flux.just(1, 5, 6, 1, 3);
    Flux<Integer> numbersSomeZeros = Flux.just(1, 0, 6, 1, 3);

    Mono<Boolean> allTrue = numbersNoZeros.all(number -> number > 0);
    Mono<Boolean> allTrue1 = numbersSomeZeros.all(number -> number > 0);

    System.out.println(allTrue.block());
    System.out.println(allTrue1.block());

  }

}
