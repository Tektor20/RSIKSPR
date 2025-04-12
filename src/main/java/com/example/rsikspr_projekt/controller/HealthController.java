package com.example.rsikspr_projekt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    private final static Logger log = LoggerFactory.getLogger(HealthController.class);

    @Autowired(required = false)
    private DataSource dataSource;

    @GetMapping("/health")
    public Map<String, Object> checkHealth() {
        Map<String, Object> response = new HashMap<>();
        boolean healthy = true;

        if(dataSource != null) {
            try(Connection connection = dataSource.getConnection()){
                if(!connection.isValid(1)){
                    healthy = false;
                    response.put("databaseStatus", "Invalid Connection");
                    log.warn("Database connection is not valid");
                } else {
                    response.put("databaseStatus", "Connected");
                }
            }
            catch (Exception e){
                healthy = false;
                response.put("databaseStatus", "Connection Failed");
                log.error(e.getMessage());
            }
        } else {
            response.put("databaseStatus", "Not Configured");
        }

        response.put("healthy", healthy);
        return response;
    }
}
