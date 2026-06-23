package com.fwishtter.converter;

import org.hibernate.usertype.UserType;

import java.sql.Types;
import java.time.LocalDateTime;

public class LocalDateTimeType implements UserType<LocalDateTime> {
    @Override
    public int getSqlType() {
        return Types.TIMESTAMP;
    }

    @Override
    public Class<LocalDateTime> returnedClass() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime deepCopy(LocalDateTime value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }
}
