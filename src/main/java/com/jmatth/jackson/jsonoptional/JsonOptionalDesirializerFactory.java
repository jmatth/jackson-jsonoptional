package com.jmatth.jackson.jsonoptional;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;

public class JsonOptionalDesirializerFactory extends DeserializerFactory {
    @Override
    public DeserializerFactory withAdditionalDeserializers(final Deserializers additional) {
        return null;
    }

    @Override
    public DeserializerFactory withAdditionalKeyDeserializers(final KeyDeserializers additional) {
        return null;
    }

    @Override
    public DeserializerFactory withDeserializerModifier(final BeanDeserializerModifier modifier) {
        return null;
    }

    @Override
    public DeserializerFactory withAbstractTypeResolver(final AbstractTypeResolver resolver) {
        return null;
    }

    @Override
    public DeserializerFactory withValueInstantiators(final ValueInstantiators instantiators) {
        return null;
    }

    @Override
    public JavaType mapAbstractType(
        final DeserializationConfig config, final JavaType type
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public ValueInstantiator findValueInstantiator(
        final DeserializationContext ctxt, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<Object> createBeanDeserializer(
        final DeserializationContext ctxt, final JavaType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<Object> createBuilderBasedDeserializer(
        final DeserializationContext ctxt,
        final JavaType type,
        final BeanDescription beanDesc,
        final Class<?> builderClass
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createEnumDeserializer(
        final DeserializationContext ctxt, final JavaType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createReferenceDeserializer(
        final DeserializationContext ctxt, final ReferenceType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createTreeDeserializer(
        final DeserializationConfig config, final JavaType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createArrayDeserializer(
        final DeserializationContext ctxt, final ArrayType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createCollectionDeserializer(
        final DeserializationContext ctxt, final CollectionType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createCollectionLikeDeserializer(
        final DeserializationContext ctxt, final CollectionLikeType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createMapDeserializer(
        final DeserializationContext ctxt, final MapType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonDeserializer<?> createMapLikeDeserializer(
        final DeserializationContext ctxt, final MapLikeType type, final BeanDescription beanDesc
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public KeyDeserializer createKeyDeserializer(
        final DeserializationContext ctxt, final JavaType type
    ) throws JsonMappingException {
        return null;
    }

    @Override
    public TypeDeserializer findTypeDeserializer(
        final DeserializationConfig config, final JavaType baseType
    ) throws JsonMappingException {
        return null;
    }
}
