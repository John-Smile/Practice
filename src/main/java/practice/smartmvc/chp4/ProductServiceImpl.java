package practice.smartmvc.chp4;

import java.sql.Connection;

public class ProductServiceImpl implements ProductService {
    private static final String UPDATE_PRODUCT_SQL = "";
    private static final String INSERT_LOG_SQL = "";
    @Override
    public void updateProductPrice(long productId, int price) {
        try {
            Connection connection = DBUtil.getConnection();
            connection.setAutoCommit(false);
            updateProduct(connection, UPDATE_PRODUCT_SQL, productId, price);
            insertLog(connection, INSERT_LOG_SQL, "Create product");
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection();
        }
    }

    private void insertLog(Connection connection, String insertLogSql, String create_product) {
    }

    private void updateProduct(Connection connection, String updateProductSql, long productId, int price) {
    }
}
