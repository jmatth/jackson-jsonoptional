import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;

@SuppressWarnings("rawtypes")
public class JsonOptionalSerializer extends JsonSerializer<JsonOptional>
    implements ContextualSerializer {

  private JavaType _valueType;

  @Override
  public JsonSerializer<?> createContextual(
      final SerializerProvider prov, final BeanProperty property) throws JsonMappingException {
    final JavaType javaType = property.getType().containedType(0);
    final JsonOptionalSerializer jsonOptionalSerializer = new JsonOptionalSerializer();
    jsonOptionalSerializer._valueType = javaType;
    return jsonOptionalSerializer;
  }

  @Override
  public void serialize(
      final JsonOptional value, final JsonGenerator gen, final SerializerProvider serializers)
      throws IOException {
    if (!value.isPresent()) {
      // If we get to this point Jackson has already decided to write out the key,
      // so we need to write null to avoid producing an error.
      gen.writeNull();
      return;
    }
    final JsonSerializer<Object> valueSerializer = serializers.findValueSerializer(_valueType);
    valueSerializer.serialize(value.get(), gen, serializers);
  }

  @Override
  public boolean isEmpty(final SerializerProvider provider, final JsonOptional value) {
    return !value.isPresent();
  }

  @Override
  public boolean isUnwrappingSerializer() {
    return true;
  }
}
