package com.gsafety.iot.test.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Date 2021/12/10 9:56
 * @Author wangqiang
 * @Description InfluxDbConfig
 **/
@Configuration
public class InfluxDbConfig {

    @Value("${spring.influx.url}")
    private String influxDBUrl;

    @Value("${spring.influx.user}")
    private String userName;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.database}")
    private String database;

    @Bean
    public InfluxDB influxdb() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxDBUrl, userName, password);
        try {

            /**
             * 异步插入：
             * enableBatch这里第一个是point的个数，第二个是时间，单位毫秒
             * point的个数和时间是联合使用的，如果满100条或者60 * 1000毫秒
             * 满足任何一个条件就会发送一次写的请求。
             */
            influxDB.setDatabase(database).enableBatch(100, 1000 * 60, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //设置默认策略
            influxDB.setRetentionPolicy("autogen");
        }
        //设置日志输出级别
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxDB;
    }
}
