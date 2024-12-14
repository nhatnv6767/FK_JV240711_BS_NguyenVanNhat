package ra.DAO;

import ra.database.JDBCUtil;
import ra.entity.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesBusiness implements DAOInterface<Category> {
    private Connection conn = null;
    private CallableStatement callSt = null;

    private void openConnection() {
        conn = new JDBCUtil().openConnection();
    }

    private void closeConnection() {
        new JDBCUtil().closeConnection(conn, callSt);
    }

    private Category mapCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        return category;
    }


    @Override
    public void insert(Category categories) {
        if (categories == null || !isValidCategory(categories)) {
            System.out.println("Invalid category");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call create_category(?)}");
            callSt.setString(1, categories.getCategoryName());
            callSt.executeUpdate();
            System.out.println("Category created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

    }

    @Override
    public void update(Category categories) {
        if (categories == null || !isValidCategory(categories)) {
            System.out.println("Invalid category");
            return;
        }

        try {
            openConnection();
            callSt = conn.prepareCall("{call update_category(?, ?)}");
            callSt.setInt(1, categories.getCategoryId());
            callSt.setString(2, categories.getCategoryName());
            callSt.executeUpdate();
            System.out.println("Category updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Category categories) {
        if (categories == null || categories.getCategoryId() <= 0) {
            System.out.println("Invalid category");
            return;
        }
        try {
            openConnection();
            callSt = conn.prepareCall("{call delete_category(?)}");
            callSt.setInt(1, categories.getCategoryId());
            callSt.executeUpdate();
            System.out.println("Category deleted successfully");
        } catch (SQLException e) {

            if (e.getMessage().contains("Cannot delete category with existing books")) {
                System.err.println("Cannot delete category with existing books");
            } else {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Category get(int id) {
        // get_category_by_id
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_category_by_id(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return mapCategory(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Category get(String name) {
        return null;
    }

    @Override
    public Category get(Category categories) {
        return null;
    }

    @Override
    public Category[] getAll() {
        List<Category> categories = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call get_all_categories()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categories.toArray(new Category[0]);
    }

    public List<Category> searchCategories(String keyword) {
        List<Category> categories = new ArrayList<>();
        try {
            openConnection();
            callSt = conn.prepareCall("{call search_categories(?)}");
            callSt.setString(1, keyword);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categories;
    }

    private boolean isValidCategory(Category category) {
        return category.getCategoryName() != null && !category.getCategoryName().trim().isEmpty();
    }
}
