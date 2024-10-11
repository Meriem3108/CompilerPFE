package com.example.testpfe.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Entity
//@Table(name = "users")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @NotEmpty(message = "Le nom d'utilisateur ne doit pas être vide")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit comporter entre 3 et 50 caractères")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Le mot de passe ne doit pas être vide")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Le mot de passe doit comporter au moins une majuscule, un chiffre et être d'au moins 8 caractères")
    private String password;


}