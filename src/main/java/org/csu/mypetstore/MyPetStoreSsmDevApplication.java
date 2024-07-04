package org.csu.mypetstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "org.csu.mypetstore")
@MapperScan("org.csu.mypetstore.persistence")
@ServletComponentScan
public class MyPetStoreSsmDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPetStoreSsmDevApplication.class, args);
    }

}
