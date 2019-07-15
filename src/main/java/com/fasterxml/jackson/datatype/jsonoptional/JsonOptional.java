package com.fasterxml.jackson.datatype.jsonoptional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class JsonOptional<T> {
  private static final JsonOptional<?> EMPTY = new JsonOptional<>(null);
  private static final JsonOptional<?> UNSET = new JsonOptional<>();

  private final Optional<T> value;

  private JsonOptional() {
    this.value = Optional.empty();
  }

  private JsonOptional(final T value) {
    this.value = Optional.ofNullable(value);
  }

  public static <T> JsonOptional<T> empty() {
    @SuppressWarnings("unchecked")
    final JsonOptional<T> t = (JsonOptional<T>) EMPTY;
    return t;
  }

  public static <T> JsonOptional<T> unset() {
    @SuppressWarnings("unchecked")
    final JsonOptional<T> t = (JsonOptional<T>) UNSET;
    return t;
  }

  public static <T> JsonOptional<T> of(T value) {
    return new JsonOptional<>(Objects.requireNonNull(value));
  }

  public static <T> JsonOptional<T> ofNullable(T value) {
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

  public JsonOptional<T> filter(final Predicate<? super T> predicate) {
    Objects.requireNonNull(predicate);
    if (!isPresent()) {
      return this;
    } else {
      return predicate.test(value.get()) ? this : empty();
    }
  }

  public <U> JsonOptional<U> map(final Function<? super T, ? extends U> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return empty();
    } else {
      return JsonOptional.ofNullable(mapper.apply(value.get()));
    }
  }

  public <U> JsonOptional<U> flatMap(
      final Function<? super T, ? extends JsonOptional<? extends U>> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return empty();
    } else {
      @SuppressWarnings("unchecked")
      JsonOptional<U> r = (JsonOptional<U>) mapper.apply(value.get());
      return Objects.requireNonNull(r);
    }
  }

  public JsonOptional<T> or(final Supplier<? extends JsonOptional<? extends T>> supplier) {
    Objects.requireNonNull(supplier);
    if (isPresent()) {
      return this;
    } else {
      @SuppressWarnings("unchecked")
      JsonOptional<T> r = (JsonOptional<T>) supplier.get();
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

    if (!(obj instanceof JsonOptional)) {
      return false;
    }

    JsonOptional<?> other = (JsonOptional<?>) obj;
    return Objects.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    if (isPresent()) {
      return String.format("com.fasterxml.jackson.datatype.jsonoptional.JsonOptional[%s]", value);
    } else if (isSet()) {
      return "com.fasterxml.jackson.datatype.jsonoptional.JsonOptional[null]";
    }
    return "com.fasterxml.jackson.datatype.jsonoptional.JsonOptional.unset";
  }
}
