package com.kyrobot.shopping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Checkout {

  private final Map<String, Product> products;
  private final List<Product> basket;

  public Checkout(Product... products) {
    this.products =
        Arrays.stream(products).collect(Collectors.toUnmodifiableMap(Product::sku, p -> p));
    this.basket = new ArrayList<>();
  }

  public void scan(String sku) {
    var knownProduct = products.get(sku);
    if (knownProduct != null) {
      basket.add(knownProduct);
    }
  }

  public BigDecimal total() {
    return basket.stream()
        .map(Product::price)
        .reduce(BigDecimal.ZERO, (itemPrice, total) -> total.add(itemPrice));
  }
}
