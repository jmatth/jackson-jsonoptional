package com.jmatth.jackson.jsonoptional;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import java.lang.reflect.Type;

public class JsonOptionalModule extends SimpleModule {
  @Override
  public void setupModule(final SetupContext context) {
    final SimpleDeserializers deserializers = new SimpleDeserializers();
    context.addDeserializers(new Deserializers());
    context.addSerializers(new Serializers());

    context.addTypeModifier(
        new TypeModifier() {
          @Override
          public JavaType modifyType(
              final JavaType type,
              final Type jdkType,
              final TypeBindings context,
              final TypeFactory typeFactory) {
            if (!type.getRawClass().equals(JsonOptional.class)) {
              return type;
            }

            return ReferenceType.upgradeFrom(type, type.containedTypeOrUnknown(0));
          }
        });
  }

  private static class Serializers extends com.fasterxml.jackson.databind.ser.Serializers.Base {
    @Override
    public JsonSerializer<?> findReferenceSerializer(
        final SerializationConfig config,
        final ReferenceType type,
        final BeanDescription beanDesc,
        final TypeSerializer contentTypeSerializer,
        final JsonSerializer<Object> contentValueSerializer) {
      final Class<?> raw = type.getRawClass();
      if (JsonOptional.class.isAssignableFrom(raw)) {
        boolean staticTyping =
            (contentTypeSerializer == null) && config.isEnabled(MapperFeature.USE_STATIC_TYPING);
        return new JsonOptionalSerializer(
            type, staticTyping, contentTypeSerializer, contentValueSerializer);
      }
      return null;
    }
  }

  private static class Deserializers
      extends com.fasterxml.jackson.databind.deser.Deserializers.Base {
    @Override
    public JsonDeserializer<?> findReferenceDeserializer(
        final ReferenceType refType,
        final DeserializationConfig config,
        final BeanDescription beanDesc,
        final TypeDeserializer contentTypeDeserializer,
        final JsonDeserializer<?> contentDeserializer)
        throws JsonMappingException {
      return refType.hasRawClass(JsonOptional.class)
          ? new JsonOptionalDeserializer(
              refType, null, contentTypeDeserializer, contentDeserializer)
          : null;
    }
  }
}
