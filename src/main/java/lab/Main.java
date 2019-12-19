
package lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Collections;

@ComponentScan
@EnableAutoConfiguration
@EnableWebMvc
@EnableWebSecurity
public class Main implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8000"));
        app.run(args);
    }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedMethods("*")
        //.allowedOrigins("http://localhost:3000");
        .allowedOrigins("*");
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
    ds.setUrl("jdbc:mysql://localhost:3306/lab");
    ds.setUsername("root");
    ds.setPassword("12345");
    return ds;
  }
}
