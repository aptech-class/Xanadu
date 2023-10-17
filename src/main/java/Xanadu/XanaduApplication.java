package Xanadu;

import Xanadu.Entities.Product;
import Xanadu.Services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(
        basePackages = "Xanadu",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "Xanadu.CrawlData..*"
        )
)
@Slf4j
public class XanaduApplication implements ApplicationRunner {
    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(XanaduApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String handle  = "reactive-water-bottle-sling";
//        Product product = productService.findByHandleWithVariantsAndImages(handle);
//        log.info(product.getVariants().get(0).getTitle());

    }
}
