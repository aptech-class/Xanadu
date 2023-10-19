package Xanadu.Services;

import Xanadu.Entities.*;
import Xanadu.Entities.Collection;
import Xanadu.Repositories.*;
import Xanadu.Utils.FilesProcessor;
import Xanadu.Utils.StringProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class ProductService {
    private final String wordErrorForHandle = "[#%{}\\\\^~\\[\\]`]";
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ProductTagRepository productTagRepository;
    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product findByHandle(String handle) {
        return productRepository.findByHandle(handle);
    }

    @Transactional
    public Page<Product> findAllWithImages(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        products.forEach(product -> product.getImages().forEach(Image::getId));
        return products;
    }

    @Transactional(readOnly = true)
    public Product findByHandleFetchEagerAll(String handle) {
        Product product = productRepository.findByHandle(handle);
        product.getImages().forEach(Image::getId);
        product.getCollections().forEach(Collection::getId);
        product.getProductTags().forEach(ProductTag::getId);
        product.getOptions().forEach(option -> option.getOptionValues().forEach(OptionValue::getId));
        product.getVariants().forEach(variant -> variant.getOptionValues().forEach(OptionValue::getId));
        product.getReviews().forEach(Review::getId);
        return product;
    }

    @Transactional
    public Product findByHandleWithVariantsAndImages(String handle) {
        Product product = productRepository.findByHandle(handle);
        product.getVariants().forEach(variant -> variant.getOptionValues().forEach(OptionValue::getId));
        product.getImages().forEach(Image::getId);
        return product;
    }

    @Transactional
    public Product saveSpread(Product product) {
        List<Collection> collectionsSaved = saveCollectionOfProduct(product);
        product.setCollections(collectionsSaved);

        List<ProductTag> productTagsSaved = saveProductTagsOfProduct(product);
        product.setProductTags(productTagsSaved);
        productRepository.save(product);

        List<Image> imagesSaved = saveImagesOfProduct(product);
        product.setImages(imagesSaved);

        List<Option> optionsSaved = saveOptionsOfProduct(product);
        product.setOptions(optionsSaved);

        List<Variant> variantsGenerated = generateVariants(product, new ArrayList<>(), new HashMap<>(), 0);

        product.setVariants(variantsGenerated);

        return product;
    }

    private List<Variant> generateVariants(Product product, List<Variant> variants, Map<String, String> simpleVariant, int index) {
        List<Option> options = product.getOptions();
        if (options.isEmpty()) {
            return variants;
        }
        if (index != options.size()) {

            Option option = options.get(index);
            List<OptionValue> optionValues = option.getOptionValues();
            if (optionValues == null || optionValues.isEmpty()) {
                generateVariants(product, variants, simpleVariant, index + 1);
            } else {
                option.getOptionValues().forEach(optionValue -> {
                    simpleVariant.put(option.getName(), optionValue.getValue());
                    generateVariants(product, variants, simpleVariant, index + 1);
                });
            }
        } else {

            boolean alreadyVariant = false;
            List<Variant> variantsExists = product.getVariants();
            if (variantsExists != null && !variantsExists.isEmpty()) {
                for (Variant variant : variantsExists) {
                    Map<String, String> simpleVariantExists = new HashMap<>();
                    for (OptionValue optionValue : variant.getOptionValues()) {
                        simpleVariantExists.put(optionValue.getOption().getName(), optionValue.getValue());
                    }
                    StringBuilder check = new StringBuilder();
                    simpleVariant.forEach((optionName, value) -> {
                        check.append(Objects.equals(simpleVariantExists.get(optionName), value));
                    });
                    log.info("variant: {}", simpleVariant);
                    log.info("variantExists: {}", simpleVariantExists);
                    log.info(check.toString());
                    if (!check.isEmpty() && !check.toString().contains("false")) {
                        variants.add(variant);
                        alreadyVariant = true;
                        break;
                    }
                }
            }

            if (!alreadyVariant && !simpleVariant.isEmpty()) {
                Variant variant = new Variant();
                StringBuilder sku = new StringBuilder(product.getHandle());
                StringBuilder title = new StringBuilder();
                simpleVariant.forEach((optionName, value) -> {
                    sku.append("_").append(optionName).append(":").append(value);
                    title.append(title.toString().isEmpty() ? "" : " / ").append(value);
                });

                variant.setTitle(title.toString());
                variant.setSku(sku.toString());
                variant.setInventory(100);
                variant.setPrice(0f);
                variant.setPrePrice(0f);
                variant.setProduct(product);

                List<OptionValue> optionValues = new ArrayList<>();
                options.forEach(option -> {
                    option.getOptionValues().forEach(optionValue -> {
                        if (Objects.equals(simpleVariant.get(option.getName()), optionValue.getValue())) {
                            optionValues.add(optionValue);
                        }
                    });
                });
                variant.setOptionValues(optionValues);
                variants.add(variantRepository.save(variant));
            }
        }

        if (index == 0) {
            List<Long> variantsIds = variants.stream().map(Variant::getId).toList();
            if (variantsIds.isEmpty()) {
                variantRepository.deleteByProduct(product);
            } else {
                variantRepository.deleteByProductAndIdNotIn(product, variantsIds);
            }
        }
        return variants;
    }


    private List<Option> saveOptionsOfProduct(Product product) {
        List<Option> optionsSaved = new ArrayList<>();
        List<Option> options = product.getOptions();

        List<Long> optionsIds = (options == null || options.isEmpty()) ? new ArrayList<>() : options.stream().map(Option::getId).toList();

        List<Option> optionsNeedDelete = optionsIds.isEmpty() ? optionRepository.findByProduct(product) : optionRepository.findByProductAndIdInNotIn(product, optionsIds);
        List<OptionValue> optionValuesNeedDelete = new ArrayList<>(optionValueRepository.findByOptionIn(optionsNeedDelete));

        if (options == null || options.isEmpty()) {
            optionValueRepository.deleteAllInBatch(optionValuesNeedDelete);
            optionRepository.deleteAllInBatch(optionsNeedDelete);
            return optionsSaved;
        }
        options.forEach(option -> {
            option.setProduct(product);
            Option optionSaved = optionRepository.save(option);
            List<OptionValue> optionValuesSaved = new ArrayList<>();
            List<Long> optionValuesIds = new ArrayList<>();

            List<OptionValue> optionValues = option.getOptionValues();
            if (optionValues != null) optionValues.forEach(optionValue -> {
                optionValue.setOption(optionSaved);
                OptionValue optionValueSaved = optionValueRepository.save(optionValue);
                optionValuesSaved.add(optionValueSaved);
                optionValuesIds.add(optionValueSaved.getId());
            });
            optionValuesNeedDelete.addAll(optionValuesIds.isEmpty() ? optionValueRepository.findByOption(optionSaved) : optionValueRepository.findByOptionAndIdNotIn(optionSaved, optionValuesIds));
            optionSaved.setOptionValues(optionValuesSaved);
            optionsSaved.add(optionSaved);

        });


        optionValueRepository.deleteAllInBatch(optionValuesNeedDelete);
        optionRepository.deleteAllInBatch(optionsNeedDelete);

        return optionsSaved;
    }

    private List<Collection> saveCollectionOfProduct(Product product) {
        List<Collection> collectionsSaved = new ArrayList<>();
        List<Collection> collections = product.getCollections();
        if (collections == null || collections.isEmpty()) {
            return collectionsSaved;
        }
        collections.forEach(collection -> {
            if (collection.getId() == null) {
                Collection collectionExists = collectionRepository.findByTitle(collection.getTitle());
                collectionsSaved.add(Objects.requireNonNullElseGet(collectionExists, () -> {
                    String handle = collection.getTitle()
                            .trim()
                            .replaceAll(" ", "-")
                            .replaceAll(wordErrorForHandle, "")
                            + "-" + StringProcessor.generateRandomCharacters(10, wordErrorForHandle);

                    collection.setHandle(handle);
                    return collectionRepository.save(collection);
                }));
            } else {
                collectionsSaved.add(collection);
            }
        });
        return collectionsSaved;
    }

    private List<ProductTag> saveProductTagsOfProduct(Product product) {
        List<ProductTag> productTagsSaved = new ArrayList<>();
        List<ProductTag> productTags = product.getProductTags();
        if (productTags == null || productTags.isEmpty()) {
            return productTagsSaved;
        }
        productTags.forEach(productTag -> {
            if (productTag.getId() == null) {
                ProductTag productTagExists = productTagRepository.findByValue(productTag.getValue());
                productTagsSaved.add(Objects.requireNonNullElseGet(productTagExists, () -> productTagRepository.save(productTag)));
            } else {
                productTagsSaved.add(productTag);
            }
        });
        return productTagsSaved;
    }

    private List<Image> saveImagesOfProduct(Product product) {
        List<Image> imagesSaved = new ArrayList<>();
        List<Long> imagesIds = new ArrayList<>();
        List<Image> images = product.getImages();
        if (images != null) images.forEach(image -> {
            image.setProduct(product);
            if (image.getId() == null) {
                boolean srcIsBase64 = image.getSrc().contains("base64");
                if (srcIsBase64) {
                    try {
                        String src = FilesProcessor.saveFileByDataUrl(image.getSrc());
                        image.setSrc(src);
                        Image imageSaved = imageRepository.save(image);
                        imagesIds.add(imageSaved.getId());
                        imagesSaved.add(imageSaved);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                Image imageExists = imageRepository.findBySrc(image.getSrc());
                imagesSaved.add(Objects.requireNonNullElseGet(imageExists, () -> {
                    Image imageSaved = imageRepository.save(image);
                    imagesIds.add(imageSaved.getId());
                    return imageSaved;
                }));
                return;
            }
            imagesIds.add(image.getId());
            imagesSaved.add(image);

        });
        List<Image> imagesNeedDelete = images == null || images.isEmpty() ? imageRepository.findByProduct(product) : imageRepository.findByProductAndIdNotIn(product, imagesIds);
        List<Variant> variantsRelate = variantRepository.findByImageIn(imagesNeedDelete);

        variantsRelate.forEach(variant -> {
            variant.setImage(null);
            variantRepository.saveAndFlush(variant);
        });
        imageRepository.deleteAllInBatch(imagesNeedDelete);

        imagesNeedDelete.forEach(image -> {
            String imageSrc = image.getSrc();
            if (!imageSrc.matches("^http.*")) {
                try {
                    FilesProcessor.deleteFile(imageSrc);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return imagesSaved;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
