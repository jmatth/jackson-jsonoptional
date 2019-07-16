package com.jmatth.jackson.jsonoptional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

public class JsonOptionalModule extends SimpleModule {
  @Override
  public void setupModule(final SetupContext context) {
    final SimpleDeserializers deserializers = new SimpleDeserializers();
    deserializers.addDeserializer(JsonOptional.class, new JsonOptionalDeserializer());

    final SimpleSerializers serializers = new SimpleSerializers();
    serializers.addSerializer(JsonOptional.class, new JsonOptionalSerializer());
    new ObjectMapper().setSerializerFactory()

    context.addDeserializers(deserializers);
    context.addSerializers(serializers);
  }
}
