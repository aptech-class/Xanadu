package Xanadu.Services;

import Xanadu.Entities.Image;
import Xanadu.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private ImageRepository     imageRepository;

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public Image findBySrc(String src) {
        return imageRepository.findBySrc(src);
    }
}
