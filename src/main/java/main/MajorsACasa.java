package main;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.logging.Logger;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MajorsACasa {

    private static final Logger log = Logger.getLogger(MajorsACasa.class.getName());

    public static void main(String[] args) {
        // Auto-configura l'aplicació
        new SpringApplicationBuilder(MajorsACasa.class).run(args);
    }

}
