package com.philvigus.fixtures;

import com.philvigus.AbstractBaseEntityFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public class TestFactory extends AbstractBaseEntityFactory<TestEntity> {
    public TestFactory(final JpaRepository<TestEntity, Long> jpaRepository) {
        super(TestEntity.class, jpaRepository);
    }

    @Override
    protected Map<String, Object> defaultAttributes() {
        return null;
    }
}
