package fwishtter.com.converter;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.dialect.Dialect;

@Slf4j
public class VarcharUuidType implements UserType<UUID> {

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

}
