package com.kyrobot.shopping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Checkout {

  private final Map<String, Product> products;
  private final Set<Deal> deals;
  private final List<Product> basket;
  private BinaryOperator<BigDecimal> accumulator = (itemPrice, total) -> total.add(itemPrice);

  public Checkout(Set<Product> products, Set<Deal> deals) {
    this.products = products.stream().collect(Collectors.toUnmodifiableMap(Product::sku, p -> p));
    this.deals = Collections.unmodifiableSet(deals);
    this.basket = new ArrayList<>();
  }

  public void scan(String sku) {
    var knownProduct = products.get(sku);
    if (knownProduct != null) {
      basket.add(knownProduct);
    }
  }

  public BigDecimal total() {
    var subTotal = basket.stream().map(Product::price).reduce(BigDecimal.ZERO, accumulator);
    var discount =
        deals.stream().map(deal -> deal.applyTo(basket)).reduce(BigDecimal.ZERO, accumulator);
    return subTotal.subtract(discount);
  }
}
