package ar.edu.ues21.pricing.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfig {
		
	@Value("${PRICING_DS_USER:pricing}")
    private String datasourceUser;

    @Value("${PRICING_DS_PASSWORD:pricingdesa}")
    private String datasourcePassword;

    @Value("${PRICING_DS_DATABASE:UES21D1}")
    private String databaseName;

    @Value("${PRICING_DS_PORT:1521}")
    private String portNumber;

    @Value("${PRICING_DS_SERVER_NAME:oracle11-desa.uesiglo21.edu.ar}")
    private String serverName;


    @Autowired
    @Bean(name = "oracleJdbcTemplate")
    public JdbcTemplate oracleJdbcTemplate(@Qualifier("oracle") HikariDataSource oracle) {
        return new JdbcTemplate(oracle);
    }

    @Primary
    @Bean(name = "oracle")
    public HikariDataSource oracleDataSource() {
    	    	
    	Properties props = new Properties();
        props.setProperty("dataSourceClassName", "oracle.jdbc.pool.OracleDataSource");
        props.setProperty("dataSource.user", datasourceUser);
        props.setProperty("dataSource.password", datasourcePassword);
        props.setProperty("dataSource.databaseName", databaseName);
        props.setProperty("dataSource.portNumber", portNumber);
        props.setProperty("dataSource.serverName", serverName);
        props.setProperty("maximumPoolSize", "5");       
        props.setProperty("minimumIdle", "1");
        props.setProperty("dataSource.driverType", "thin");
        props.setProperty("connectionTestQuery", "select 1 from dual");
           	    	
        HikariConfig config = new HikariConfig(props);
        return new HikariDataSource(config);
    }
   

}
