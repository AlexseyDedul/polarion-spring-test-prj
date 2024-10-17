package com.garantis.polarion.extension.license.analyzer.model;

import org.jetbrains.annotations.NotNull;

public enum ProductType {
    ALM("com.polarion.alm"),
    QA("com.polarion.qa"),
    REQUIREMENTS("com.polarion.requirements");

    @NotNull
    private String id;

    private ProductType(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    @NotNull
    public static ProductType forId(@NotNull String productId) {
        ProductType[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            ProductType product = var4[var2];
            if (productId.equals(product.getId())) {
                return product;
            }
        }

        throw new IllegalArgumentException("productId is unknown: " + productId);
    }
}
