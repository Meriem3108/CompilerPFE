package com.example.testpfe.Service;

import com.example.testpfe.Entity.User;

import java.util.List;

public interface IUserService {
 //   public User findByUsername(String username);
    public List<User> findAllUsers();
    public User save(User user);
    void encodePassword(User user);
    public User findByUsername(String username);
    //  public void deleteById(Long id);
}
