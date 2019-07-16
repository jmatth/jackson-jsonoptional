package com.jmatth.jackson.jsonoptional;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;

@SuppressWarnings("rawtypes")
public class JsonOptionalSerializer extends ReferenceTypeSerializer<JsonOptional<?>> {

  public JsonOptionalSerializer(
      final ReferenceTypeSerializer<?> base,
      final BeanProperty property,
      final TypeSerializer vts,
      final JsonSerializer<?> valueSer,
      final NameTransformer unwrapper,
      final Object suppressableValue,
      final boolean suppressNulls) {
    super(base, property, vts, valueSer, unwrapper, suppressableValue, suppressNulls);
  }

  @Override
  protected ReferenceTypeSerializer<JsonOptional<?>> withResolved(
      final BeanProperty prop,
      final TypeSerializer vts,
      final JsonSerializer<?> valueSer,
      final NameTransformer unwrapper) {
    return new JsonOptionalSerializer(
        this, prop, vts, valueSer, unwrapper, _suppressableValue, _suppressNulls);
  }

  @Override
  public ReferenceTypeSerializer<JsonOptional<?>> withContentInclusion(
      final Object suppressableValue, final boolean suppressNulls) {
    return new JsonOptionalSerializer(
        this,
        _property,
        _valueTypeSerializer,
        _valueSerializer,
        _unwrapper,
        suppressableValue,
        suppressNulls);
  }

  @Override
  protected boolean _isValuePresent(final JsonOptional<?> value) {
    return value.isPresent();
  }

  @Override
  protected Object _getReferenced(final JsonOptional<?> value) {
    return value.get();
  }

  @Override
  protected Object _getReferencedIfPresent(final JsonOptional<?> value) {
    return value.orElse(null);
  }

  @Override
  public boolean isEmpty(final SerializerProvider provider, final JsonOptional value) {
    return !value.isPresent();
  }
}
