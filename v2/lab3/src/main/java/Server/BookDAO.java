package Server;

import DTO.BookDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public static BookDTO findById(long id) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM books "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            BookDTO book = null;
            if (resultSet.next()) {
                book = new BookDTO();
                book.setId(resultSet.getLong(1));
                book.setAuthorId(resultSet.getLong(2));
                book.setName(resultSet.getString(3));
                book.setPages(resultSet.getLong(4));
            }
            preparedStatement.close();
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BookDTO findByName(String name) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM books "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            BookDTO book = null;
            if (resultSet.next()) {
                book = new BookDTO();
                book.setId(resultSet.getLong(1));
                book.setAuthorId(resultSet.getLong(2));
                book.setName(resultSet.getString(3));
                book.setPages(resultSet.getLong(4));
            }
            preparedStatement.close();
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(BookDTO book) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "UPDATE books "
                            + "SET name = ?, author_id = ?, pages = ? "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, book.getAuthorId( ) );
            preparedStatement.setLong(3, book.getPages());
            preparedStatement.setLong(4, book.getId());
            var result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(BookDTO book) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO books (name,author_id,pages) "
                            + "VALUES (?,?,?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, book.getAuthorId( ) );
            preparedStatement.setLong(3, book.getPages());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book.setId(resultSet.getLong(1));
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(BookDTO book) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM books "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, book.getId());
            var result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<BookDTO> findAll() {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM books";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                BookDTO book = new BookDTO();
                book.setId(resultSet.getLong(1));
                book.setAuthorId(resultSet.getLong(2));
                book.setName(resultSet.getString(3));
                book.setPages(resultSet.getLong(4));
                list.add(book);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<BookDTO> findByAuthorId(Long id) {
        try (Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM books "
                            + "WHERE author_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                BookDTO book = new BookDTO();
                book.setId(resultSet.getLong(1));
                book.setAuthorId(resultSet.getLong(2));
                book.setName(resultSet.getString(3));
                book.setPages(resultSet.getLong(4));
                list.add(book);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
