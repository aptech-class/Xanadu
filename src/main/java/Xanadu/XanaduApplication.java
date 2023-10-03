package Xanadu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(
        basePackages = "Xanadu",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "Xanadu.CrawlData..*"
        )
)
public class XanaduApplication {

    public static void main(String[] args) {
        SpringApplication.run(XanaduApplication.class, args);
    }

}
