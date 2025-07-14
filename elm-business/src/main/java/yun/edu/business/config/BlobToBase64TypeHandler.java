package yun.edu.business.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

@MappedTypes(String.class)
public class BlobToBase64TypeHandler extends BaseTypeHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(BlobToBase64TypeHandler.class);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        // 设置参数，从String到Blob的转换
        byte[] bytes = Base64.getDecoder().decode(parameter);
        ps.setBytes(i, bytes);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        logger.debug("Getting BLOB data for column: {}", columnName);
        Object columnValue = rs.getObject(columnName);
        if (columnValue == null) {
            logger.warn("BLOB data is null for column: {}", columnName);
            return null;
        }
        
        if (columnValue instanceof byte[]) {
            return convertBlobToBase64((byte[]) columnValue);
        } else if (columnValue instanceof Blob) {
            return convertBlobToBase64((Blob) columnValue);
        } else {
            logger.warn("Unexpected column type for {}: {}", columnName, columnValue.getClass().getName());
            return String.valueOf(columnValue);
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        logger.debug("Getting BLOB data for column index: {}", columnIndex);
        Object columnValue = rs.getObject(columnIndex);
        if (columnValue == null) {
            logger.warn("BLOB data is null for column index: {}", columnIndex);
            return null;
        }
        
        if (columnValue instanceof byte[]) {
            return convertBlobToBase64((byte[]) columnValue);
        } else if (columnValue instanceof Blob) {
            return convertBlobToBase64((Blob) columnValue);
        } else {
            logger.warn("Unexpected column type for index {}: {}", columnIndex, columnValue.getClass().getName());
            return String.valueOf(columnValue);
        }
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        logger.debug("Getting BLOB data from CallableStatement for column index: {}", columnIndex);
        Object columnValue = cs.getObject(columnIndex);
        if (columnValue == null) {
            logger.warn("BLOB data is null from CallableStatement for column index: {}", columnIndex);
            return null;
        }
        
        if (columnValue instanceof byte[]) {
            return convertBlobToBase64((byte[]) columnValue);
        } else if (columnValue instanceof Blob) {
            return convertBlobToBase64((Blob) columnValue);
        } else {
            logger.warn("Unexpected column type for CallableStatement index {}: {}", columnIndex, columnValue.getClass().getName());
            return String.valueOf(columnValue);
        }
    }

    private String convertBlobToBase64(byte[] bytes) {
        try {
            // 将BLOB转换为Base64字符串
            String base64 = Base64.getEncoder().encodeToString(bytes);
            return "data:image/jpeg;base64," + base64; // 添加数据URL前缀
        } catch (Exception e) {
            logger.error("Error converting BLOB to Base64: {}", e.getMessage(), e);
            return null;
        }
    }

    private String convertBlobToBase64(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = outputStream.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            return "data:image/jpeg;base64," + base64; // 添加数据URL前缀
        } catch (Exception e) {
            logger.error("Error converting BLOB to Base64: {}", e.getMessage(), e);
            return null;
        }
    }
} 