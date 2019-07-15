import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class JSONOptional<T> {
  private static final JSONOptional<?> EMPTY = new JSONOptional<>(null);
  private static final JSONOptional<?> UNSET = new JSONOptional<>();

  private final Optional<T> value;

  private JSONOptional() {
    this.value = Optional.empty();
  }

  private JSONOptional(final T value) {
    this.value = Optional.ofNullable(value);
  }

  public static <T> JSONOptional<T> empty() {
    @SuppressWarnings("unchecked")
    final JSONOptional<T> t = (JSONOptional<T>) EMPTY;
    return t;
  }

  public static <T> JSONOptional<T> unset() {
    @SuppressWarnings("unchecked")
    final JSONOptional<T> t = (JSONOptional<T>) UNSET;
    return t;
  }

  public static <T> JSONOptional<T> of(T value) {
    return new JSONOptional<>(Objects.requireNonNull(value));
  }

  public static <T> JSONOptional<T> ofNullable(T value) {
    return value == null ? empty() : of(value);
  }

  public T get() {
    if (value == null) {
      throw new NoSuchElementException("No value present");
    }
    return value.get();
  }

  public boolean isPresent() {
    return value != null && value.isPresent();
  }

  public boolean isSet() {
    return value != null;
  }

  public boolean isUnset() {
    return value == null;
  }

  public boolean isEmpty() {
    return value == null || value.isEmpty();
  }

  public void ifPresent(final Consumer<? super T> action) {
    if (value != null && value.isPresent()) {
      action.accept(value.get());
    }
  }

  public void ifPresentOrElse(final Consumer<? super T> action, final Runnable emptyAction) {
    if (value != null && value.isPresent()) {
      action.accept(value.get());
    } else {
      emptyAction.run();
    }
  }

  public JSONOptional<T> filter(final Predicate<? super T> predicate) {
    Objects.requireNonNull(predicate);
    if (!isPresent()) {
      return this;
    } else {
      return predicate.test(value.get()) ? this : empty();
    }
  }

  public <U> JSONOptional<U> map(final Function<? super T, ? extends U> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return empty();
    } else {
      return JSONOptional.ofNullable(mapper.apply(value.get()));
    }
  }

  public <U> JSONOptional<U> flatMap(
      final Function<? super T, ? extends JSONOptional<? extends U>> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return empty();
    } else {
      @SuppressWarnings("unchecked")
      JSONOptional<U> r = (JSONOptional<U>) mapper.apply(value.get());
      return Objects.requireNonNull(r);
    }
  }

  public JSONOptional<T> or(final Supplier<? extends JSONOptional<? extends T>> supplier) {
    Objects.requireNonNull(supplier);
    if (isPresent()) {
      return this;
    } else {
      @SuppressWarnings("unchecked")
      JSONOptional<T> r = (JSONOptional<T>) supplier.get();
      return Objects.requireNonNull(r);
    }
  }

  public Stream<T> stream() {
    if (!isPresent()) {
      return Stream.empty();
    } else {
      return Stream.of(value.get());
    }
  }

  public T orElse(T other) {
    return isPresent() ? value.get() : other;
  }

  public T orElseGet(Supplier<? extends T> supplier) {
    return isPresent() ? value.get() : supplier.get();
  }

  public T orElseThrow() {
    if (isEmpty()) {
      throw new NoSuchElementException("No value present");
    }
    return value.get();
  }

  public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSupplier)
      throws X {
    if (isPresent()) {
      return value.get();
    } else {
      throw exceptionSupplier.get();
    }
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof JSONOptional)) {
      return false;
    }

    JSONOptional<?> other = (JSONOptional<?>) obj;
    return Objects.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    if (isPresent()) {
      return String.format("JSONOptional[%s]", value);
    } else if (isSet()) {
      return "JSONOptional[null]";
    }
    return "JSONOptional.unset";
  }
}
