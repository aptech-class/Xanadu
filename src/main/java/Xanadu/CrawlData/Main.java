package Xanadu.CrawlData;

import Xanadu.Entities.*;
import Xanadu.Entities.Collection;
import Xanadu.Services.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URISyntaxException;
import java.util.*;


@Slf4j
@SpringBootApplication
@ComponentScan(
        basePackages = {"Xanadu"}
)
@EnableTransactionManagement
public class Main implements ApplicationRunner {
    @Autowired
    private CollectionCrawler collectionCrawler;
    @Autowired
    private ProductCrawler productCrawler;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductTagService productTagService;

    @Autowired
    private OptionService optionService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OptionValueService optionValueService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private VariantService variantService;
    private final Object lockProcessCategory = new Object();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.exit(0);
    }

    @Override
    public void run(ApplicationArguments args) throws URISyntaxException, InterruptedException {
        crawling();
        log.info("crawled.");
    }

    private void crawling() throws InterruptedException, URISyntaxException {
        List<Thread> threads = new ArrayList<>();
        List<Collection> collections = collectionCrawler.gets(collectionCrawler.getCollectionsEndpoint(), "limit=50");

        for (Collection collection : collections) {
            Collection collectionExited = collectionService.findByHandle(collection.getHandle());
            if (collectionExited != null) {
                continue;
            }
            Runnable task = () -> {
                try {
                    String productsEndpoint = collectionCrawler.getCollectionsEndpoint() + "/" + collection.getHandle() + "/products";
                    ProductCrawler.ResProducts resProducts = productCrawler.gets(productsEndpoint, "limit=250");
                    if (resProducts.getProducts().isEmpty()) {
                        return;
                    }
                    Collection collectionSaved = collectionService.save(collection);

                    HashSet<Category> categories = resProducts.getCategories();
                    List<Category> categoriesOnCollection = new ArrayList<>();

                    synchronized (lockProcessCategory) {
                        for (Category category : categories) {
                            Category categoryExisted = categoryService.findByTitle(category.getTitle());
                            if (categoryExisted != null) {
                                categoriesOnCollection.add(categoryExisted);
                            } else {
                                Category categorySaved = categoryService.save(category);
                                categoriesOnCollection.add(categorySaved);
                            }
                        }

                        collectionSaved.setCategories(categoriesOnCollection);

                        List<Product> products = resProducts.getProducts();
                        for (Product product : products) {
                            processProduct(product, collectionSaved);
                        }
                    }

                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            };
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }


    private void processProduct(Product product, Collection collectionSaved) {

        Product productExisted = productService.findByHandle(product.getHandle());
        if (productExisted != null) {
            List<Collection> collectionsExisted = collectionService.findByProduct(productExisted);
            collectionsExisted.add(collectionSaved);
            productExisted.setCollections(collectionsExisted);
            productService.save(productExisted);
        } else {

            List<Collection> collectionsOnProduct = new ArrayList<>();
            collectionsOnProduct.add(collectionSaved);
            product.setCollections(collectionsOnProduct);

            ProductType productType = product.getProductType();
            ProductType productTypeExisted = productTypeService.findByTitle(productType.getTitle());
            if (productTypeExisted != null) {
                product.setProductType(productTypeExisted);
            } else {
                ProductType productTypeSaved = productTypeService.save(productType);
                product.setProductType(productTypeSaved);
            }

            Vendor vendor = product.getVendor();
            Vendor vendorExisted = vendorService.findByName(vendor.getName());
            if (vendorExisted != null) {
                product.setVendor(vendorExisted);
            } else {
                Vendor vendorSaved = vendorService.save(vendor);
                product.setVendor(vendorSaved);
            }


            List<ProductTag> productTags = new ArrayList<>();
            for (ProductTag productTag : product.getProductTags()) {
                ProductTag productTagExisted = productTagService.findByValue(productTag.getValue());
                if (productTagExisted != null) {
                    productTags.add(productTagExisted);
                } else {
                    ProductTag productTagSaved = productTagService.save(productTag);
                    productTags.add(productTagSaved);
                }
            }
            product.setProductTags(productTags);

            Product productSaved = productService.save(product);

            List<Option> options = product.getOptions();
            options.forEach(option -> {
                option.setProduct(productSaved);
                Option optionSaved = optionService.save(option);
                for (OptionValue optionValue : option.getOptionValues()) {
                    optionValue.setOption(optionSaved);
                    optionValueService.save(optionValue);
                }

            });

            for (Image image : product.getImages()) {
                image.setProduct(productSaved);
                imageService.save(image);
            }

            List<Variant> variants = product.getVariants();
            for (Variant variant : variants) {
                variant.setProduct(productSaved);
                if (variant.getImage() != null) {
                    Image imageSaved = imageService.findBySrc(variant.getImage().getSrc());
                    variant.setImage(imageSaved);
                }
                List<OptionValue> optionValuesSaved = new ArrayList<>();
                for (OptionValue optionValue : variant.getOptionValues()) {

                    OptionValue  optionValueSaved = optionValueService.findByValueAndProduct(optionValue.getValue(),productSaved);
                    optionValuesSaved.add(optionValueSaved);
                }
                variant.setOptionValues(optionValuesSaved);
                Variant variantSaved = variantService.save(variant);
            }
        }
    }
}
