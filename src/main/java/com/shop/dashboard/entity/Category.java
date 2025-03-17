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
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="is_enable")
    private byte enabled;

    @Column(name="category_code")
    private String categoryCode;

    @Column(name="is_deleted")
    private byte Deleted;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
