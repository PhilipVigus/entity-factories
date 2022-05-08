package com.philvigus;

import com.philvigus.fixtures.TestEntity;
import com.philvigus.fixtures.TestFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractBaseEntityFactoryTest {
  @Test
  void makeReturnsAnInstanceOfTheEntity()
      throws InvocationTargetException, NoSuchMethodException, InstantiationException,
          IllegalAccessException {
    TestFactory factory = new TestFactory();

    assertEquals(TestEntity.class, factory.make().getClass());
  }
}
