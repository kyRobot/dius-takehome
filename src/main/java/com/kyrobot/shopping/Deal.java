package com.kyrobot.shopping;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * A Deal is a function that takes a Product List and calculates a Discount value Discount should be
 * Zero or more, never negative;
 */
public interface Deal extends Function<List<Product>, BigDecimal> {

  default BigDecimal applyTo(List<Product> products) {
    return BigDecimal.ZERO;
  }

  /*
   redirect apply calls to applyTo. API Naming choice, allows Deal.applyTo(products)
  */
  @Override
  default BigDecimal apply(List<Product> products) {
    return applyTo(products);
  }
}
