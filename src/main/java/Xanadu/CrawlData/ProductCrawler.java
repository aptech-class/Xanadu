package Xanadu.CrawlData;


import Xanadu.Entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Slf4j
@Component
@Getter
public class ProductCrawler {
    private final String productsEndpoint = "https://unitedbyblue.com/products";


    public ResProducts gets(String endpoint, String filter) throws URISyntaxException {
        WebClient client = WebClient.create();
        return client
                .mutate()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .build()
                .method(HttpMethod.GET)
                .uri(new URI(endpoint + ".json?" + filter))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResProducts.class)
                .block();
    }

    public ResProduct get(String endpoint) throws URISyntaxException {
        WebClient client = WebClient.create();
        return client
                .mutate()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .build()
                .method(HttpMethod.GET)
                .uri(new URI(endpoint + ".json"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResProduct.class)
                .block();
    }


    @Getter
    @Setter
    public static class ResProduct {
        @JsonProperty
        private Product product;
    }

    @Getter
    @Setter
    public static class ResProducts {
        @JsonProperty("products")
        private List<Product_> products_;

        @JsonIgnore
        public List<Product> getProducts() {
            List<Product> products = new ArrayList<>();
            for (Product_ product_ : products_) {

                Product product = new Product();

                product.setDiscount(product_.getDiscount());
                product.setHandle(product_.getHandle());
                product.setImages(product_.getImages());
                product.setBodyHtml(product_.getBody_html());
                product.setCollections(product_.getCollections());
                product.setReviews(product_.getReviews());
                product.setTitle(product_.getTitle());

                List<ProductTag> productTags = new ArrayList<>();
                product_.getTags().forEach(tag -> {
                    if (!tag.matches(".*:.*")) {
                        ProductTag productTag = new ProductTag();
                        productTag.setValue(tag);
                        productTags.add(productTag);
                    }
                });
                product.setProductTags(productTags);

                List<Option> options = new ArrayList<>();
                HashMap<String, String> optionsMap = new HashMap<>();
                product_.getOptions_().forEach(option_ -> {
                    Option option = new Option();
                    option.setName(option_.getName());
                    List<OptionValue> optionValues = new ArrayList<>();
                    option_.getValues().forEach(value -> {
                        OptionValue optionValue = new OptionValue();
                        optionValue.setValue(value);
                        optionValues.add(optionValue);
                        optionsMap.put(optionValue.getValue(), option_.getName());
                    });
                    option.setOptionValues(optionValues);
                    option.setName(option_.getName());
                    options.add(option);
                });
                product.setOptions(options);

                ProductType productType = new ProductType();
                productType.setTitle(product_.getProduct_type());
                product.setProductType(productType);

                Vendor vendor = new Vendor();
                vendor.setName(product_.getVendorName());
                product.setVendor(vendor);

                List<Variant> variants = new ArrayList<>();
                product_.getVariants_().forEach(variant_ -> {
                    Variant variant = new Variant();
                    variant.setImage(variant_.getFeaturedImage());
                    variant.setTitle(variant_.getTitle());
                    variant.setInventory(100);
                    variant.setPrice(variant_.getPrice());
                    float loi = Math.round(variant_.getPrice() * 0.3f * 100) / 100f;
                    variant.setPrePrice(variant_.getPrice() - loi);
                    String sku = product.getTitle();
                    String option1 = variant_.getOption1();
                    String option2 = variant_.getOption2();
                    String option3 = variant_.getOption3();
                    List<OptionValue> optionValues = new ArrayList<>();
                    if (option1 != null) {
                        OptionValue optionValue = new OptionValue();
                        optionValue.setValue(option1);
                        optionValues.add(optionValue);
                        sku = sku + "_" + optionsMap.get(option1) + ":" + option1;
                    }
                    if (option2 != null) {
                        OptionValue optionValue = new OptionValue();
                        optionValue.setValue(option2);
                        optionValues.add(optionValue);
                        sku = sku + "_" + optionsMap.get(option2) + ":" + option2;
                    }
                    if (option3 != null) {
                        OptionValue optionValue = new OptionValue();
                        optionValue.setValue(option3);
                        optionValues.add(optionValue);
                        sku = sku + "_" + optionsMap.get(option3) + ":" + option3;
                    }
                    variant.setOptionValues(optionValues);
                    variant.setSku(sku);
                    variants.add(variant);
                });
                product.setVariants(variants);

                products.add(product);
            }
            return products;
        }

        public HashSet<Category> getCategories() {
            HashSet<Category> categories = new LinkedHashSet<>();
            for (Product_ product_ : products_) {
                product_.getTags().forEach(tag -> {
                    if (tag.matches("(?i)^category:.*")) {
                        Category category = new Category();
                        String title = tag.replaceAll("(?i)^category:", "").trim();
                        category.setTitle(title);
                        category.setStatus(true);
                        categories.add(category);
                    }
                });
            }
            return categories;
        }
    }

    @Getter
    @Setter
    static class Product_ extends Product {
        @JsonProperty("vendor")
        private String vendorName;
        private String product_type;
        private List<String> tags;
        private String body_html;
        private List<Category> categories;
        @JsonProperty("options")
        private List<Option_> options_;
        @JsonProperty("variants")
        private List<Variant_> variants_;

        @Getter
        @Setter
        private static class Option_ {
            private List<String> values;
            private String name;
        }

        @Getter
        @Setter
        private static class Variant_ extends Variant {

            private String option1;
            private String option2;
            private String option3;
            @JsonProperty("featured_image")
            private Image featuredImage;
        }

    }
}
