package yun.edu.business.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan("yun.edu.business.mapper")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        // 配置mapper文件位置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/**/*.xml"));
        
        // 创建SqlSessionFactory
        SqlSessionFactory factory = sessionFactory.getObject();
        
        // 注册TypeHandler（只用于显式指定了typeHandler的字段）
        if (factory != null) {
            TypeHandlerRegistry typeHandlerRegistry = factory.getConfiguration().getTypeHandlerRegistry();
            typeHandlerRegistry.register(String.class, JdbcType.BLOB, BlobToBase64TypeHandler.class);
        }
        
        return factory;
    }
} 