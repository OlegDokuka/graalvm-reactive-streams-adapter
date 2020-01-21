package com.example.demo;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

public class DemoApplication {
    public Publisher<String> doSimpleWork() {
        return Flux.range(0, 100)
                .map(i -> i + "Hello world from JDK")
                .log();
    }

    public Publisher<Integer> doWork() {
        return Flux.range(0, 10000000)
                .log();
    }
}
