package com.project.ece651.webapp.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;

// reference: https://mrbird.cc/Spring-Boot%20JSON.html
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper getJsonObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // (official) https://github.com/FasterXML/jackson-databind/wiki/Serialization-Features
        // https://stackoverflow.com/questions/18031125/what-is-the-difference-between-enum-name-and-enum-tostring

        // mapper.configure(WRITE_ENUMS_USING_TO_STRING, true);

        // need to include this, or annotate password and encryptedPassword with a new type of @JsonView
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

        return mapper;
    }
}
