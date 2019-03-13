package com.daniel.seckill.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author DanielLin07
 * @date 2019/3/13 12:00
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }
}
