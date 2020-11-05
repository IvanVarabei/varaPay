package com.epam.varapay.model.dao.builder;

import com.epam.varapay.model.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IdentifiableBuilder <T extends Identifiable> {
    T build(ResultSet resultSet) throws SQLException;
}
