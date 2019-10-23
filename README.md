# JSONOptional
This package provides a way to differentiate between values set to `null` and
values that were not present in a JSON payload that has been deserialized with
Jackson. It does this by providing a wrapper type, `JsonOptional`, and a Jackson
module for transparently serializing and deserializing that type,
`JsonOptionalModule`.

## Problem Outline
When writing REST APIs, it can be useful to tell the difference between values
that were set to null in a request vs those that were not present in the request
at all. This is particularly true when writing API endpoints that accept `PATCH`
requests: in this case it would be logical to have a value set to null be
cleared or deleted, while a value not specified in the payload is not modified.
Unfortunately, this is not easy to accomplish by default in many JSON to object
mappers, Jackson included.

For example, say we are deserializing to the following class:

```java
public class MyView {
  @JsonProperty("key")
  public String key;
}
```

The JSON payloads `{ "key": null }` and `{ }` would both produce the same result
when deserialized: an instance of `MyView` with `key` set to `null`. Providing a
way to make this distinction with minimal code modification is the goal of this
package.

## JsonOptional
The `JsonOptional` class provides the same interface as Java's native `Optional`
class (though it is not related to it in any way via the type system).
Additionally, it provides several extra methods to check whether a value was
present in the original JSON request. To illustrate, we can compare the native
`isPresent()` method to the `JsonOptional` specific `isSet()` method under
different circumstances. If we were to deserialize to the following class:

```java
public class MyView {
  @JsonProperty("key")
  public JsonOptional<String> key = JsonOptional.unset();
}
```

then here are the outputs for various JSON payloads.

| Payload            | `key.isPresent()` | `key.isSet()` |
| ------------------ | ----------------- | ------------- |
| `{ "key": "set" }` | true              | true          |
| `{ "key": null }`  | false             | true          |
| `{ }`              | false             | false         |

# Known Issues
When wrapping a class field in `JsonOptional`, you _must_ initialize it to
`JsonOptional.unset()` either as part of its declaration (preffered) or in the
constructor(s). Failure to do so will result in the field being `null` and
probably a `NullPointerException` at runtime. Ideally the deserializer would
handle this is not currently possible as Jackson only considers keys that are
present in the payload it is deserializing, so the deserializer is never even
called for keys that are not set. See [this github
issue](https://github.com/FasterXML/jackson-databind/issues/618) on
`jackson-databind` for more details.
