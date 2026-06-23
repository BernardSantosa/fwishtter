package fwishtter.com.converter;

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
    public LocalDateTime deepCopy(LocalDateTime localDateTime) {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }
}
