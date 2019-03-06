package com.kyrobot.shopping;

import java.math.BigDecimal;
import java.util.List;

public class BulkBuyDeal implements Deal {

  private final Product product;
  private final int minimumBuy;
  private final BigDecimal discountPerItem;

  public BulkBuyDeal(Product product, int minimumBuy, BigDecimal discountPerItem) {
    this.product = product;
    this.minimumBuy = minimumBuy;
    this.discountPerItem = discountPerItem;
  }

  @Override
  public BigDecimal applyTo(List<Product> products) {
    var productsPurchased = products.stream().filter(p -> p.sku().equals(product.sku())).count();
    if (productsPurchased >= minimumBuy) {
      return discountPerItem.multiply(BigDecimal.valueOf(productsPurchased));
    } else {
      return BigDecimal.ZERO;
    }
  }
}
