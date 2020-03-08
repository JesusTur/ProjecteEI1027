package es.projecteEI1027;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.logging.Logger;

@SpringBootApplication
public class MajorsACasa {

    private static final Logger log = Logger.getLogger(MajorsACasa.class.getName());

    public static void main(String[] args) {
        // Auto-configura l'aplicació
        new SpringApplicationBuilder(MajorsACasa.class).run(args);
    }

}
