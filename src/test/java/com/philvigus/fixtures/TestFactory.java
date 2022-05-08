package com.philvigus.fixtures;

import com.philvigus.AbstractBaseEntityFactory;

public class TestFactory extends AbstractBaseEntityFactory<TestEntity> {
  public TestFactory() {
    super(TestEntity.class);
  }
}
