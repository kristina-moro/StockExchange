package org.stockexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.stockexchange")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
