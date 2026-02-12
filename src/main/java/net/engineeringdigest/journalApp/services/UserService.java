package net.engineeringdigest.journalApp.services;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.UserException;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createUser(User entry)  {
        if(userRepository.existsByUsername(entry.getUsername())){
            logger.error("User already exists with username:{} ",entry.getUsername());
            throw new UserException("User already exists with username: "+entry.getUsername());

        }
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Collections.singletonList("USER"));

        return userRepository.save(entry);
    }

    public User createAdmin(User entry)  {
        User existingUser =
                userRepository.findByUsername(entry.getUsername());

        if (existingUser!=null) {

            if (existingUser.getRoles().contains("ADMIN")) {
                throw new UserException(
                        "Admin already exists with username: " + entry.getUsername());
            }

            existingUser.getRoles().add("ADMIN");
            return userRepository.save(existingUser); // UPDATE (has _id)
        }

        // New user
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Arrays.asList("USER", "ADMIN"));
        return userRepository.save(entry);
    }

    public void saveUser(User entry)  {
        userRepository.save(entry);
    }

    public void deleteUserById(ObjectId id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getEntryById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateUser(User entry)  {
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        userRepository.save(entry);
    }

}
