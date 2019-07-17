package com.jmatth.jackson.jsonoptional;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

public class JsonOptionalDeserializer extends ReferenceTypeDeserializer<JsonOptional<?>> {

    public JsonOptionalDeserializer(
        final JavaType fullType,
        final ValueInstantiator inst,
        final TypeDeserializer typeDeser,
        final JsonDeserializer<?> deser
    ) {
        super(fullType, inst, typeDeser, deser);
    }

    @Override
    public JsonOptionalDeserializer withResolved(
        final TypeDeserializer typeDeser, final JsonDeserializer<?> valueDeser
    ) {
        return new JsonOptionalDeserializer(_fullType, _valueInstantiator, typeDeser, valueDeser);
    }

    @Override
    public JsonOptional<?> getNullValue(final DeserializationContext ctxt) {
        return JsonOptional.empty();
    }

    @Override
    public Object getEmptyValue(final DeserializationContext ctxt) {
        return super.getEmptyValue(ctxt);
    }

    @Override
    public JsonOptional<?> referenceValue(final Object contents) {
        return JsonOptional.ofNullable(contents);
    }

    @Override
    public Object getReferenced(final JsonOptional<?> reference) {
        return reference.get();
    }

    @Override
    public JsonOptional<?> updateReference(final JsonOptional<?> reference, final Object contents) {
        return JsonOptional.ofNullable(contents);
    }
}
