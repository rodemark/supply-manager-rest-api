package team.rode.supplymanagerrestapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupplyManagerRestApi {

    public static void main(String[] args) {

        // Загружаем переменные окружения из .env
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });

        SpringApplication.run(SupplyManagerRestApi.class, args);
    }

}
