package com.bass2000.tlgrm.bot.service;

import com.bass2000.tlgrm.bot.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bass2000.tlgrm.bot.repo.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findChatId(long id) {
        return userRepository.findUserByChatId(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public List<User> findNewUsers() {
        List<User> newUsers = userRepository.findNewUsers();
        newUsers.forEach((user) -> user.setNotified(true));
        userRepository.saveAll(newUsers);
        return newUsers;
    }

    @Transactional
    public void addUser(User user) {
        user.setAdmin(userRepository.count() == 0);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
