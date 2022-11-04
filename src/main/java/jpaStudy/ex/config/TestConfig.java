package jpaStudy.ex.config;

import jpaStudy.ex.entity.Station;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestConfig {
    @Bean
    @Profile("test")
    public Station testStation(){
        return new Station("testStation");
    }
    @Bean
    @Profile("local")
    public Station localStation(){
        return new Station("localStation");
    }
}
