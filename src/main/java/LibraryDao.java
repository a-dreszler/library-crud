import java.sql.*;
import java.util.Optional;

class LibraryDao {

    Connection connection;

    public LibraryDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException("Nie udało się połączyć z bazą danych:\n" + e.getMessage());
        }
    }

    boolean librarySave(Book book) {
        final String sql = """
                INSERT INTO book (isbn, title, author, year)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getYear());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Nie udało się zapisać książki:\n" + e.getMessage());
        }

        return false;
    }

    Optional<Book> libraryRead(String isbn) {
        final String sql = """
                SELECT
                    *
                FROM
                    book
                WHERE
                    isbn = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("year"),
                        resultSet.getString("isbn")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Nie udało się odczytać książki:\n" + e.getMessage());
        }

        return Optional.empty();
    }

    int libraryUpdate(Book book) {
        final String sql = """
                UPDATE book
                SET
                    isbn = ?,
                    title = ?,
                    author = ?,
                    year = ?
                WHERE
                    id = ?;
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getYear());
            statement.setInt(5, book.getId());
            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated;
        } catch (SQLException e) {
            System.out.println("Nie udało się zaktualizować książki:\n" + e.getMessage());
        }

        return 0;
    }

//    Optional<Integer> getIdByIsbn(String isbn) {
//        final String sql = """
//                SELECT
//                    id
//                FROM
//                    book
//                WHERE
//                    isbn = ?
//                """;
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, isbn);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return Optional.of(resultSet.getInt("id"));
//            }
//        } catch (SQLException e) {
//            System.out.println("Nie udało się uzyskać id książki:\n" + e.getMessage());
//        }
//
//        return Optional.empty();
//    }

    int libraryDelete(int id) {
        final String sql = """
                DELETE FROM book WHERE id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted;
        } catch (SQLException e) {
            System.out.println("Nie udało się usunąć książki:\n" + e.getMessage());
        }

        return 0;
    }
}