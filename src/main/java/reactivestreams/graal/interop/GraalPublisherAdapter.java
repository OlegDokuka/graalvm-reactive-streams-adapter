package reactivestreams.graal.interop;

import java.util.function.Consumer;

import com.oracle.truffle.api.object.DynamicObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class GraalPublisherAdapter<T> implements Publisher<T> {

    private final Consumer<Subscriber<? super T>> delegate;

    public GraalPublisherAdapter(Publisher<T> delegate) {
        this.delegate = delegate::subscribe;
    }

    @SuppressWarnings("unchecked")
    public GraalPublisherAdapter(DynamicObject delegate) {
        this.delegate = (Consumer<Subscriber<? super T>>) delegate.get("subscribe");
    }

    @Override
    public void subscribe(Subscriber<? super T> s) {
        delegate.accept(s);
    }

    public void subscribe(DynamicObject dynamicObject) {
        delegate.accept(new DynamicSubscriber<>(dynamicObject));
    }

    static class DynamicSubscriber<T> implements Subscriber<T> {

        final Consumer<Subscription> onSubscribe;
        final Consumer<T>            onNext;
        final Consumer<Throwable>    onError;
        final Runnable               onComplete;

        @SuppressWarnings("unchecked")
        DynamicSubscriber(DynamicObject subscriber) {
            onSubscribe = (Consumer<Subscription>) subscriber.get("onSubscribe");
            onNext = (Consumer<T>) subscriber.get("onNext");
            onError = (Consumer<Throwable>) subscriber.get("onError");
            onComplete = (Runnable) subscriber.get("onError");
        }

        @Override
        public void onSubscribe(Subscription s) {
            onSubscribe.accept(s);
        }

        @Override
        public void onNext(T t) {
            onNext.accept(t);
        }

        @Override
        public void onError(Throwable t) {
            onError.accept(t);
        }

        @Override
        public void onComplete() {
            onComplete.run();
        }
    }
}
