package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Book;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    }

    @Override
    public void delete(Book book) {

    }

    @Override
    public Book get(int id) {
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
        return new Book[0];
    }

    private boolean isValidBook(Book book) {
        return book.getBookTitle() != null && !book.getBookTitle().trim().isEmpty() &&
                book.getAuthor() != null && !book.getAuthor().trim().isEmpty() &&
                book.getPublicationDate() != null &&
                book.getPrice() > 0 &&
                book.getCategoryId() > 0;
    }
}
