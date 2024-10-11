//package com.example.testpfe.Service;
//
//import com.example.testpfe.Entity.User;
//import com.example.testpfe.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService implements IUserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
////    @Autowired
////    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
////@Override
////    public User save(User user) {
////        user.setPassword(passwordEncoder.encode(user.getPassword()));
////        return userRepository.save(user);
////    }
//    @Override
//    public User save(User user) {
//        boolean isUpdatedUser = (user.getId() != null);
//        if (isUpdatedUser) {
//            User existingUser = userRepository.getReferenceById(user.getId());
//
//            if (user.getPassword().isEmpty()) {
//                user.setPassword(existingUser.getPassword());
//            } else {
//                encodePassword(user);
//            }
//        } else {
//            encodePassword(user);
//        }
//        return userRepository.save(user);
//    }
//
//
//    @Override
//    public void encodePassword(User user) {
//        String encodePass = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodePass);
//    }
//    @Override
//    public List<User> findAllUsers() {
//        return userRepository.findAll();
//    }
//    @Override
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
//
//}
package com.example.testpfe.Service;

import com.example.testpfe.Entity.User;
import com.example.testpfe.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Méthode pour sauvegarder un utilisateur
    public User save(User user) {
        if (user.getId() != null) {
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + user.getId() + " not found"));

            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    // Méthode pour encoder le mot de passe de l'utilisateur
    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    // Trouver tous les utilisateurs
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Rechercher un utilisateur par nom d'utilisateur
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    // Implémentation de la méthode `loadUserByUsername` de l'interface `UserDetailsService`
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Crée un objet UserDetails nécessaire pour Spring Security
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())  // Utiliser le champ `username` de l'entité
                .password(user.getPassword())  // Mot de passe encodé
                .authorities("USER")           // Autorités pour cet utilisateur
                .build();
    }
    // Méthode pour récupérer le PasswordEncoder utilisé
    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }
}
