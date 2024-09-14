/**
 *
 * @author Zainal Abidin
 */
package org.zainal_abidin.bankdki;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankdkiApplication {
    public static void main(String[] args) throws IOException {
        Path uploadDir = (Path) Paths.get("uploads/");
        Path logDir = (Path) Paths.get("logs/");
        Files.createDirectories(uploadDir);
        Files.createDirectories(logDir);
        
        SpringApplication.run(BankdkiApplication.class, args);
    }
}
