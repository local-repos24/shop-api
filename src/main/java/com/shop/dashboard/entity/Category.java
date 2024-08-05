package com.shop.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="is_enable")
    private boolean isEnable;

    @Column(name="category_code")
    private String categoryCode;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
