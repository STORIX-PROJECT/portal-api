package com.shop.storix.portalapi.config.mybatis;

import com.shop.storix.portalapi.common.CodeEnum;
import com.shop.storix.portalapi.common.exception.InvalidCodeEnumException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

public class EnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public EnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    // Java → DB: code 값으로 저장
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    // DB → Java: code 값으로 enum 찾기
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toEnum(cs.getString(columnIndex));
    }

    private E toEnum(String code) {
        if (code == null) return null;
        return EnumSet.allOf(type).stream()
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElseThrow(()-> new InvalidCodeEnumException(type,code));
    }
}

