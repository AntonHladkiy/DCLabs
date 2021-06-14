package Server;

import DTO.AuthorDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    public static AuthorDTO findById(long id) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM authors "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            AuthorDTO author = null;
            if (resultSet.next()) {
                author = new AuthorDTO();
                author.setId(resultSet.getLong(1));
                author.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AuthorDTO findByName(String name) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM authors "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            AuthorDTO author = null;
            if (resultSet.next()) {
                author = new AuthorDTO();
                author.setId(resultSet.getLong(1));
                author.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(AuthorDTO author) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "UPDATE authors "
                            + "SET name = ? "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setLong(2, author.getId());
            var result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(AuthorDTO author) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "INSERT INTO authors (name) "
                            + "VALUES (?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                author.setId(resultSet.getLong(1));
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(AuthorDTO author) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "DELETE FROM authors "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, author.getId());
            var result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<AuthorDTO> findAll() {
        try (Connection connection = DBConnection.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM authors";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AuthorDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                AuthorDTO author = new AuthorDTO();
                author.setId(resultSet.getLong(1));
                author.setName(resultSet.getString(2));
                list.add(author);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}