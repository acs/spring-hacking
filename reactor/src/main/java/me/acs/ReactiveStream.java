package me.acs;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import static org.assertj.core.api.Assertions.assertThat;


public class ReactiveStream {

  private static void simpleSubscribe() {
    List<Integer> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4).log().subscribe(elements::add);

    assertThat(elements).containsExactly(1, 2, 3, 4);
  }

  private static void doubleStreamNumbersSubscribe() {
    List<Integer> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4).log().map(i -> i * 2).subscribe(elements::add);

    assertThat(elements).containsExactly(1 * 2, 2 * 2, 3 * 2, 4 * 2);
  }

  private static void zipStreams() {
    List<String> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
      .log()
      .map(i -> i * 2)
      .zipWith(Flux.range(0, Integer.MAX_VALUE),
        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
      .subscribe(elements::add);

    assertThat(elements).containsExactly(
      "First Flux: 2, Second Flux: 0",
      "First Flux: 4, Second Flux: 1",
      "First Flux: 6, Second Flux: 2",
      "First Flux: 8, Second Flux: 3");
  }

  private static void streamConcurrency() {
    List<Integer> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
      .log()
      .map(i -> i * 2)
      .subscribeOn(Schedulers.parallel())
      .subscribe(elements::add);

    assertThat(elements).containsExactly(1, 2, 3, 4);
  }

  private static void customSubscribe() {
    List<Integer> elements = new ArrayList<>();

    // long numberOfItems = Long.MAX_VALUE;
    long numberOfItems = 2;

    Flux.just(1, 2, 3, 4)
      .log()
      .subscribe(new Subscriber<Integer>() {

        private Subscription s;
        int onNextAmount = 0;

        @Override
        public void onSubscribe(Subscription s) {
          this.s = s;
          s.request(numberOfItems);
        }

        @Override
        public void onNext(Integer integer) {
          elements.add(integer);
          onNextAmount++;
          if (onNextAmount % 2 == 0) {
            s.request(2);
          }

        }

        @Override
        public void onError(Throwable t) {
        }

        @Override
        public void onComplete() {
        }
      });

    assertThat(elements).containsExactly(1, 2, 3, 4);
  }

  private static void hotStream() {
    // always running and can be subscribed to at any point in time, missing the start of the data
    ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
      while(true) {
        fluxSink.next(System.currentTimeMillis());
      }
    })
      .sample(Duration.ofSeconds(2))
      .publish();

    publish.subscribe(System.out::println);
    publish.subscribe(System.out::println);

    publish.connect();

  }

  public static void main(String args[]) {
    System.out.println("Working with reactive streams in Java with Reactor");

//    simpleSubscribe();
//    customSubscribe();
//
//    doubleStreamNumbersSubscribe();
//
//    zipStreams();
//
//    streamConcurrency();

    hotStream();

  }

}
