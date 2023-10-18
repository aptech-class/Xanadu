package Xanadu.Services;

import Xanadu.Entities.Variant;
import Xanadu.Repositories.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {
    @Autowired
    private VariantRepository variantRepository;
    public Variant save(Variant variant) {
        return variantRepository.save(variant);
    }

    public List<Variant> saveAll(List<Variant> variants) {
        return variantRepository.saveAll(variants);
    }
}
