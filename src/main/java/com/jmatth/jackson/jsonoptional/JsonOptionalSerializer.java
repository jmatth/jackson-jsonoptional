package com.jmatth.jackson.jsonoptional;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;

@SuppressWarnings("rawtypes")
public class JsonOptionalSerializer extends ReferenceTypeSerializer<JsonOptional<?>> {

  public JsonOptionalSerializer(
      ReferenceType fullType,
      boolean staticTyping,
      TypeSerializer vts,
      JsonSerializer<Object> ser) {
    super(fullType, staticTyping, vts, ser);
  }

  public JsonOptionalSerializer(
      JsonOptionalSerializer base,
      BeanProperty property,
      TypeSerializer vts,
      JsonSerializer<?> valueSer,
      NameTransformer unwrapper,
      Object suppressableValue,
      boolean suppressNulls) {
    super(base, property, vts, valueSer, unwrapper, suppressableValue, suppressNulls);
  }

  @Override
  protected ReferenceTypeSerializer<JsonOptional<?>> withResolved(
      BeanProperty prop,
      TypeSerializer vts,
      JsonSerializer<?> valueSer,
      NameTransformer unwrapper) {
    if ((_property == prop)
        && (_valueTypeSerializer == vts)
        && (_valueSerializer == valueSer)
        && (_unwrapper == unwrapper)) {
      return this;
    }
    return new JsonOptionalSerializer(
        this, prop, vts, valueSer, unwrapper, _suppressableValue, _suppressNulls);
  }

  @Override
  public ReferenceTypeSerializer<JsonOptional<?>> withContentInclusion(
      Object suppressableValue, boolean suppressNulls) {
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
  protected boolean _isValuePresent(JsonOptional<?> value) {
    return value.isPresent();
  }

  @Override
  protected Object _getReferenced(JsonOptional<?> value) {
    return value.get();
  }

  @Override
  protected Object _getReferencedIfPresent(JsonOptional<?> value) {
    return value.isPresent() ? value.get() : null;
  }
}
