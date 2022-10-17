package com.philvigus;

import com.philvigus.exceptions.EntityFactoryException;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.beanutils.BeanUtils.setProperty;

public abstract class AbstractBaseEntityFactory<T> {
    protected final Class<T> entityClass;

    protected final Faker faker;

    protected final JpaRepository<T, Long> repository;

    protected Map<String, Object> customAttributes;

    protected AbstractBaseEntityFactory(final Class<T> entityClass, final JpaRepository<T, Long> repository) {
        this.entityClass = entityClass;
        this.faker = new Faker();
        this.repository = repository;
        this.customAttributes = new ConcurrentHashMap<>();
    }

    public List<T> create(final int copies) throws EntityFactoryException {
        if (copies < 1) {
            throw new IllegalArgumentException("copies must be greater than 0");
        }

        final List<T> entities = new ArrayList<>(copies);

        for (int i = 0; i < copies; i++) {
            final T entity = getEntityWithAttributesSet(customAttributes);
            entities.add(repository.save(entity));
        }

        return entities;
    }

    public T create() throws EntityFactoryException {
        final T entity = getEntityWithAttributesSet(customAttributes);

        return repository.save(entity);
    }

    public List<T> make(final int copies) throws EntityFactoryException {
        if (copies < 1) {
            throw new IllegalArgumentException("copies must be greater than 0");
        }

        final List<T> entities = new ArrayList<>(copies);

        for (int i = 0; i < copies; i++) {
            final T entity = getEntityWithAttributesSet(customAttributes);
            entities.add(entity);
        }

        return entities;
    }

    public T make() throws EntityFactoryException {
        return getEntityWithAttributesSet(customAttributes);
    }

    public AbstractBaseEntityFactory<T> withAttributes(final Map<String, Object> customAttributes) {
        this.customAttributes = customAttributes;

        return this;
    }

    protected T getEntityWithAttributesSet(final Map<String, Object> customAttributes)
            throws EntityFactoryException {
        final T entity;

        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | IllegalArgumentException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new EntityFactoryException(String.format("Unable to create entity of type %s", entityClass), e);
        }

        getCombinedAttributes(customAttributes)
                .forEach(
                        (name, value) -> {
                            try {
                                setProperty(entity, name, value);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new EntityFactoryException(
                                        String.format(
                                                "Unable to set property %s to %s on entity of type %s", name, value, entityClass),
                                        e);
                            }
                        });

        return entity;
    }

    protected Map<String, Object> getCombinedAttributes(final Map<String, Object> customAttributes) {
        final Map<String, Object> defaultAttributes = defaultAttributes();

        defaultAttributes.putAll(customAttributes);

        return defaultAttributes;
    }

    protected abstract Map<String, Object> defaultAttributes();
}
