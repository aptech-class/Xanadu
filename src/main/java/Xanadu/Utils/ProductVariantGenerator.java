package Xanadu.Utils;

import java.util.*;

public class ProductVariantGenerator {
    public static void main(String[] args) {
        Map<String, List<String>> product = new HashMap<>();
        product.put("color", Arrays.asList("red", "blue"));
        product.put("size", Arrays.asList("small", "large"));
        product.put("material", Arrays.asList("cotton", "polyester"));

        List<String> options = new ArrayList<>(product.keySet());
        Map<String, String> variant = new HashMap<>();
        generateVariants(product, options, variant, 0);
    }

    public static void generateVariants(Map<String, List<String>> product, List<String> options,
                                        Map<String, String> variant, int index) {
        if (index == options.size()) {
            System.out.println(variant);
            return;
        }

        String option = options.get(index);
        List<String> values = product.get(option);
        for (String value : values) {
            variant.put(option, value);
            generateVariants(product, options, variant, index + 1);
        }
    }
}