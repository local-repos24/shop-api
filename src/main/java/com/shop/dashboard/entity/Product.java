package com.shop.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code_string;
    private String description;
    private String name;
    private String price;
    private int store;
    private int level;
    private String article_type;
    private String img;
    private int model_number;
    private String brand;
    private boolean enable;
    /*agregar timestamp a entity category
     * cuando existen datos ya creados en la tabla puede dar error zero date
     * al agregar el timestamp, una forma de solucionar seria borrar manualmente
     * de la tabla los campos con fecha 0000-0000-00:00
     * o resolver zero date https://www.programmerall.com/article/9892145127/
     * */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date last_update;

    @PrePersist
    private void onCreate() {
        last_update=new Date();
    }

    private int stock;
    private String availability;
    private String season_code;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
