package com.varabei.ivan.model.dao.builder;

import com.varabei.ivan.model.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IdentifiableBuilder <T extends Identifiable> {
    T build(ResultSet resultSet) throws SQLException;
}
