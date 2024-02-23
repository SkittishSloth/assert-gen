package io.github.skittishsloth.assertgen.parsing;

import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.PropertySource;

import lombok.Value;

public class Parser {
    public void parse(final String source) {
        final JavaClassSource classSource = Roaster.parse(JavaClassSource.class, source);
        final List<PropertySource<JavaClassSource>> properties = classSource.getProperties();

        properties.stream().map(this::buildProperties).toList();
    }

    private PropertyData buildProperties(final PropertySource<JavaClassSource> propertySource) {
        final String name = propertySource.getName();
        final Type<JavaClassSource> type = propertySource.getType();

        final TypeData typeData = new TypeData(type.getName(), type.isPrimitive());

        return new PropertyData(name, typeData);
    }

    @Value
    static class PropertyData {
        @NonNull String name;
        @NonNull TypeData type;
    }

    @Value
    static class TypeData {
        @NonNull String name;
        boolean primitive;
    }
}
