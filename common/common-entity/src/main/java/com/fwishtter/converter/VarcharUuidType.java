package com.fwishtter.converter;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;

import java.sql.Types;
import java.util.UUID;

@Slf4j
public class VarcharUuidType implements EnhancedUserType<UUID> {

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<UUID> returnedClass() {
        return UUID.class;
    }

    @Override
    public UUID deepCopy(UUID uuid) {
        return uuid; // necessary
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public String toSqlLiteral(UUID uuid) {
        return "";
    }

    @Override
    public String toString(UUID uuid) throws HibernateException {
        return "";
    }

    @Override
    public UUID fromStringValue(CharSequence charSequence) throws HibernateException {
        return null;
    }
}
