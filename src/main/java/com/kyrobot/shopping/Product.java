package com.kyrobot.shopping;

import java.math.BigDecimal;

public class Product {

  private final String sku;
  private final BigDecimal price;

  private Product(String sku, String price) {
    var value = new BigDecimal(price);
    this.sku = sku.trim().toLowerCase(); // normalise
    this.price = (value.compareTo(BigDecimal.ZERO) > 0) ? value : BigDecimal.ZERO;
  }

  public static Product create(String sku, String price) {
    return new Product(sku, price);
  }

  public String sku() {
    return sku;
  }

  public BigDecimal price() {
    return price;
  }
}
