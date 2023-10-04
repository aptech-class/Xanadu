package Xanadu.Services;

import Xanadu.Entities.OptionValue;
import Xanadu.Entities.Product;
import Xanadu.Repositories.OptionValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionValueService {
    @Autowired
    private OptionValueRepository optionValueRepository;

    public OptionValue save(OptionValue optionValue) {
        return optionValueRepository.save(optionValue);
    }


    public OptionValue findByValueAndProduct(String value, Product productSaved) {
        return optionValueRepository.findByValueAndProductId(value,productSaved.getId());
    }
}
