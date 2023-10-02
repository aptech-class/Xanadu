package Xanadu.Services;

import Xanadu.Entities.Option;
import Xanadu.Repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    @Autowired
    OptionRepository optionRepository;

    public List<Option> saveAll(Iterable<Option> options) {
        return optionRepository.saveAll(options);
    }

    public Option save(Option option) {
        return optionRepository.save(option);
    }
}
