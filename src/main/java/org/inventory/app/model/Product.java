package org.inventory.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity( name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String sku;

    @Column(columnDefinition = "TEXT")
    private String description;


    private BigDecimal costPrice;

    private BigDecimal sellingPrice;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    private Supplier supplier;

    /* mappedBy = "product" – verweist auf das Feld product in der Image-Klasse */
    /* cascade = CascadeType.ALL – Änderungen am Produkt wirken sich auch auf Bilder aus */
    /* orphanRemoval = true – wenn ein Bild aus der Liste entfernt wird, wird es auch aus der DB gelöscht */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttribute> productAttributes = new ArrayList<>();

    /* cascade = CascadeType.PERSIST StockMovements nicht gelöscht werden, wenn ein Product gelöscht wird */
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<StockMovement> stockMovements;
}