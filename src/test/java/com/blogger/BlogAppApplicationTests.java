package com.blogger;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
@Slf4j
class BlogAppApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void applicationCanConnectToDatabaseTest(){

        assertThat(dataSource).isNotNull();
        log.info("Data object is created");

        try(Connection connection = dataSource.getConnection()){

            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("blogdb");
            log.info("Connection --> {}", connection.getCatalog());

        }catch (SQLException throwable){
           log.info("Exception occurred while connecting to the database --> {}", throwable.getMessage());
        }
    }

}
