package jpaStudy.ex;

import jpaStudy.ex.performance.MyParentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExApplication {


	public static void main(String[] args) {
		SpringApplication.run(ExApplication.class, args);

	}

}
