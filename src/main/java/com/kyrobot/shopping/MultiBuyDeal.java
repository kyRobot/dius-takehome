package com.kyrobot.shopping;

import java.math.BigDecimal;
import java.util.List;

public class MultiBuyDeal implements Deal {

  private final Product product;
  private final int minimumPurchase;
  private final BigDecimal discount;

  public MultiBuyDeal(Product product, int minimumPurchase, BigDecimal discount) {
    this.product = product;
    this.minimumPurchase = minimumPurchase;
    this.discount = discount;
  }

  @Override
  public BigDecimal applyTo(List<Product> products) {
    var productsPurchased = products.stream().filter(p -> p.sku().equals(product.sku())).count();
    var dealsTriggered = productsPurchased / minimumPurchase;
    return discount.multiply(BigDecimal.valueOf(dealsTriggered));
  }
}
