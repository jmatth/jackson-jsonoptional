import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

public class JsonOptionalModule extends Module {
  @Override
  public String getModuleName() {
    return getClass().getName();
  }

  @Override
  public Version version() {
    return Version.unknownVersion();
  }

  @Override
  public void setupModule(final SetupContext context) {
    final SimpleDeserializers deserializers = new SimpleDeserializers();
    deserializers.addDeserializer(JsonOptional.class, new JsonOptionalDeserializer());

    final SimpleSerializers serializers = new SimpleSerializers();
    serializers.addSerializer(JsonOptional.class, new JsonOptionalSerializer());

    context.addDeserializers(deserializers);
    context.addSerializers(serializers);
  }
}
