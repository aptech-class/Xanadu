package Xanadu.Services;

import Xanadu.Entities.User;
import Xanadu.Repositories.UserRepository;
import Xanadu.Utils.FilesProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {
    private final String uploadImageDir = "/files/images/users";

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            Optional<User> userOptionalExists = userRepository.findById(user.getId());
            userOptionalExists.ifPresent(value -> user.setPassword(value.getPassword()));
        }

        MultipartFile imageFile = user.getImageFile();
        if (!imageFile.isEmpty()) {
            try {
                String image = user.getImage();
                String newImage = FilesProcessor.saveFileByMultiPart(imageFile, uploadImageDir);
                user.setImage(newImage);
                if (image != null) {
                    FilesProcessor.deleteFile(image);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
