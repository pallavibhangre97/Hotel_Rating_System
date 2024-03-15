package com.user_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @Column
    private String name;
    @Column
    private String email;
    @Transient
    List<Rating> list;

}
