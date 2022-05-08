package com.philvigus;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractBaseEntityFactory<T> {
  protected final Class<T> entityClass;

  public AbstractBaseEntityFactory(final Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  public T make()
      throws NoSuchMethodException, InvocationTargetException, InstantiationException,
          IllegalAccessException {
    return entityClass.getDeclaredConstructor().newInstance();
  }
}
