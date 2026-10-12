package com.example.lab12.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The title cant be blank")
    @NotEmpty(message = "The title cant be empty")
    @Size(max = 25, message = "Title must not exceed 25 characters")
    @Column(columnDefinition = "VARCHAR(25) not null")
    private String title;

    @NotBlank(message = "The body cant be blank")
    @NotEmpty(message = "The body cant be empty")
    @Size(max = 100, message = "Body must not exceed 100 characters")
    @Column(columnDefinition = "VARCHAR(100) not null")
    private String body;


    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

}

