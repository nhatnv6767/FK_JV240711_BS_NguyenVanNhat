package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Book;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookBusiness implements DAOInterface<Book> {

    private Connection conn = null;
    private CallableStatement callSt = null;

    private void openConnection() {
        conn = new JDBCUtil().openConnection();
    }

    private void closeConnection() {
        new JDBCUtil().closeConnection(conn, callSt);
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setBookTitle(rs.getString("book_title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationDate(rs.getDate("publication_date"));
        book.setPrice(rs.getFloat("price"));
        book.setCategoryId(rs.getInt("category_id"));
        book.setCategoryName(rs.getString("category_name"));
        return book;
    }


    @Override
    public void insert(Book book) {
        if (book == null || !isValidBook(book)) {
            System.out.println("Invalid book");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call create_book(?,?,?,?,?)}");
            callSt.setString(1, book.getBookTitle());
            callSt.setString(2, book.getAuthor());
            callSt.setDate(3, new java.sql.Date(book.getPublicationDate().getTime()));
            callSt.setFloat(4, book.getPrice());
            callSt.setInt(5, book.getCategoryId());
            callSt.executeUpdate();
            System.out.println("Book created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Book book) {
        if (book == null || !isValidBook(book)) {
            System.out.println("Invalid book");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call update_book(?,?,?,?,?, ?)}");
            callSt.setInt(1, book.getBookId());
            callSt.setString(2, book.getBookTitle());
            callSt.setString(3, book.getAuthor());
            callSt.setDate(4, new java.sql.Date(book.getPublicationDate().getTime()));
            callSt.setFloat(5, book.getPrice());
            callSt.setInt(6, book.getCategoryId());
            callSt.executeUpdate();
            System.out.println("Book updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Book book) {
        if (book == null || book.getBookId() <= 0) {
            System.out.println("Invalid book");
            return;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call delete_book(?)}");
            callSt.setInt(1, book.getBookId());
            callSt.executeUpdate();
            System.out.println("Book deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Book get(int id) {
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_book_by_id(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return mapBook(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Book get(String name) {
        return null;
    }

    @Override
    public Book get(Book book) {
        return null;
    }

    @Override
    public Book[] getAll() {
        List<Book> books = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_all_books()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                books.add(mapBook(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return books.toArray(new Book[0]);
    }

    public List<Book> getBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_books_by_category(?)}");
            callSt.setInt(1, categoryId);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                books.add(mapBook(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return books;
    }

    public List<Book> getBooksSorted(String column, String direction) {
        List<Book> books = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_books_sorted(?, ?)}");
            callSt.setString(1, column);
            callSt.setString(2, direction);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                books.add(mapBook(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return books;
    }

    public List<Book> searchBooksAdvanced(String title, String author, Float minPrice, Float maxPrice, Date startDate, Date endDate) {
        List<Book> books = new ArrayList<>();
        if (minPrice != null && minPrice < 0) {
            throw new IllegalArgumentException("Minimum price cannot be negative");
        }
        if (maxPrice != null && maxPrice < 0) {
            throw new IllegalArgumentException("Maximum price cannot be negative");
        }
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call search_books_advanced(?, ?, ?, ?, ?, ?)}");
            callSt.setString(1, title);
            callSt.setString(2, author);
            callSt.setFloat(3, minPrice != null ? minPrice : 0);
            callSt.setFloat(4, maxPrice != null ? maxPrice : 999999999);
            callSt.setDate(5, startDate != null ? new java.sql.Date(startDate.getTime()) : null);
            callSt.setDate(6, endDate != null ? new java.sql.Date(endDate.getTime()) : null);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                books.add(mapBook(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return books;
    }

    private boolean isValidBook(Book book) {
        return book.getBookTitle() != null && !book.getBookTitle().trim().isEmpty() &&
                book.getAuthor() != null && !book.getAuthor().trim().isEmpty() &&
                book.getPublicationDate() != null &&
                book.getPrice() > 0 &&
                book.getCategoryId() > 0;
    }
}
