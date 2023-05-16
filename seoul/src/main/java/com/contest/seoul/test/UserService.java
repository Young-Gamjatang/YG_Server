//package com.contest.seoul.test;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public User updateUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public void deleteUser(String userId) {
//        userRepository.deleteById(userId);
//    }
//
//    public User getUser(String userId) {
//        return userRepository.findById(userId).orElse(null);
//    }
//}
