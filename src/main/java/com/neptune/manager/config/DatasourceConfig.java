package com.neptune.manager.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author xiongwu
 **/
@Configuration
@MapperScan(basePackages = "com.neptune.manager.mapper.sys",sqlSessionTemplateRef  = "defaultSqlSessionTemplate")
public class DatasourceConfig {


    @Bean(name = "defaultDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.default")
    @Primary
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mybaitsConfiguration")
    @ConfigurationProperties(prefix = "mybatis.configuration")
    @Primary
    public org.apache.ibatis.session.Configuration mybaitsConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean(name = "defaultSqlSessionFactory")
    @Primary
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDataSource") DataSource dataSource,@Qualifier("mybaitsConfiguration") org.apache.ibatis.session.Configuration config) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setConfiguration(config);
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com.neptune.manager.mapper.sys/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "defaultTransactionManager")
    @Primary
    public DataSourceTransactionManager defaultTransactionManager(@Qualifier("defaultDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "defaultSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate defaultSqlSessionTemplate(@Qualifier("defaultSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
