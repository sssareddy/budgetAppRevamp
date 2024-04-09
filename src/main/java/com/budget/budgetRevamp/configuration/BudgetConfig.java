package com.budget.budgetRevamp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class BudgetConfig {
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // Serialize dates as strings in ISO 8601 format
        module.addSerializer(java.util.Date.class, new ToStringSerializer());

        objectMapper.registerModule(module);
        return objectMapper;
    }
}
