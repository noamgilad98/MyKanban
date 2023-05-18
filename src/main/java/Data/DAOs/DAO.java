package Data.DAOs;


import Data.DTOs.DTO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T extends DTO> {

    protected final Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }
    public boolean add(T dto) throws SQLException {
        String tableName = dto.getTableName();
        List<String> primaryKeyFields = dto.getPrimaryKeyFields();

        String sql = "INSERT INTO " + tableName + " (";
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!primaryKeyFields.contains(fieldName)) {
                sql += fieldName + ",";
            }
        }
        sql = sql.substring(0, sql.length() - 1); // Remove trailing comma

        sql += ") VALUES (";
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (!primaryKeyFields.contains(field.getName())) {
                sql += "?,";
            }
        }
        sql = sql.substring(0, sql.length() - 1); // Remove trailing comma
        sql += ")";

        PreparedStatement statement = connection.prepareStatement(sql);
        int index = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(dto);
            } catch (IllegalAccessException e) {
                throw new SQLException("Error accessing field " + field.getName() + " of DTO " + dto.getClass().getSimpleName(), e);
            }
            if (!primaryKeyFields.contains(field.getName())) {
                statement.setObject(index, value);
                index++;
            }
        }
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    }




    public boolean update(T dto) throws SQLException {
        String tableName = dto.getTableName();
        List<String> primaryKeyFields = dto.getPrimaryKeyFields();
        String sql = "UPDATE " + tableName + " SET ";
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!primaryKeyFields.contains(field.getName())) {
                sql += field.getName() + " = ?,";
            }
        }
        sql = sql.substring(0, sql.length() - 1); // Remove trailing comma
        sql += " WHERE ";
        for (String pk : primaryKeyFields) {
            sql += pk + " = ? AND ";
        }
        sql = sql.substring(0, sql.length() - 5); // Remove trailing AND
        PreparedStatement statement = connection.prepareStatement(sql);
        int index = 1;
        for (Field field : fields) {
            if (!primaryKeyFields.contains(field.getName())) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(dto);
                } catch (IllegalAccessException e) {
                    throw new SQLException("Error accessing field " + field.getName() + " of DTO " + dto.getClass().getSimpleName(), e);
                }
                statement.setObject(index, value);
                index++;
            }
        }
        for (String pk : primaryKeyFields) {
            Field pkField;
            try {
                pkField = dto.getClass().getDeclaredField(pk);
                pkField.setAccessible(true);
                Object pkValue = pkField.get(dto);
                statement.setObject(index, pkValue);
                index++;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new SQLException("Error accessing primary key field " + pk + " of DTO " + dto.getClass().getSimpleName(), e);
            }
        }
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    }



    public boolean remove(T dto) throws SQLException, NoSuchFieldException {
        String tableName = dto.getTableName();
        List<String> pkFields = dto.getPrimaryKeyFields();
        if (pkFields.isEmpty()) {
            throw new SQLException("No primary key fields defined for DTO " + dto.getClass().getSimpleName());
        }
        String sql = "DELETE FROM " + tableName + " WHERE ";
        for (String field : pkFields) {
            sql += field + " = ? AND ";
        }
        sql = sql.substring(0, sql.length() - 5); // Remove trailing " AND "
        PreparedStatement statement = connection.prepareStatement(sql);
        int index = 1;
        for (String field : pkFields) {
            Field declaredField = dto.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            Object value;
            try {
                value = declaredField.get(dto);
            } catch (IllegalAccessException e) {
                throw new SQLException("Error accessing field " + declaredField.getName() + " of DTO " + dto.getClass().getSimpleName(), e);
            }
            statement.setObject(index, value);
            index++;
        }
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    }

    public T get(Class<T> dtoClass) throws Exception {
        String tableName = dtoClass.getDeclaredConstructor().newInstance().getTableName();
        List<String> pkValues = dtoClass.getDeclaredConstructor().newInstance().getPrimaryKeyFields();



        String sql = "SELECT * FROM " + tableName + " WHERE ";
        for (int i = 0; i < pkValues.size(); i++) {
            sql += pkValues.get(i) + " = ?";
            if (i < pkValues.size() - 1) {
                sql += " AND ";
            }
        }

        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < pkValues.size(); i++) {
            statement.setObject(i + 1, pkValues.get(i));
        }

        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }

        T dto = dtoClass.getDeclaredConstructor().newInstance();
        for (Field field : dtoClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = resultSet.getObject(field.getName());
            field.set(dto, value);
        }

        return dto;
    }


    public List<T> getAll(Class<T> dtoClass) throws Exception {
        T dtoInstance = dtoClass.getDeclaredConstructor().newInstance();
        String tableName = dtoInstance.getTableName();
        List<String> primaryKeyFields = dtoClass.getDeclaredConstructor().newInstance().getPrimaryKeyFields();

        String sql = "SELECT * FROM " + tableName;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            T dto = dtoClass.getDeclaredConstructor().newInstance();

            for (Field field : dtoClass.getDeclaredFields()) {
                if (!primaryKeyFields.contains(field.getName())) {
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());
                    field.set(dto, value);
                }
            }

            for (String pk : primaryKeyFields) {
                Field pkField = dtoClass.getDeclaredField(pk);
                pkField.setAccessible(true);
                Object value = resultSet.getObject(pk);
                pkField.set(dto, value);
            }

            result.add(dto);
        }

        return result;
    }

    public T getByUnique(Class<T> dtoClass,String uniqueColumn , Object uniqueValue) throws Exception {
        String tableName = dtoClass.getDeclaredConstructor().newInstance().getTableName();

        String sql = "SELECT * FROM " + tableName + " WHERE " + uniqueColumn + " = " + uniqueValue;



        PreparedStatement statement = connection.prepareStatement(sql);




        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }

        T dto = dtoClass.getDeclaredConstructor().newInstance();
        for (Field field : dtoClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = resultSet.getObject(field.getName());
            field.set(dto, value);
        }

        return dto;
    }



}
