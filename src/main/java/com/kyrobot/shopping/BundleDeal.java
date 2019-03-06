package com.kyrobot.shopping;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.util.List;

public class BundleDeal implements Deal {

  private final Product purchaseProduct;
  private final Product freeProduct;

  public BundleDeal(Product purchaseProduct, Product freeProduct) {

    this.purchaseProduct = purchaseProduct;
    this.freeProduct = freeProduct;
  }

  @Override
  public BigDecimal applyTo(List<Product> products) {
    var bySku = products.stream().collect(groupingBy(Product::sku, counting()));
    var purchased = bySku.get(purchaseProduct.sku());
    var free = bySku.get(freeProduct.sku());

    if (purchased != null) {
      if (free != null) {
        // one free item per purchased item
        if (free <= purchased) {
          return freeProduct.price().multiply(BigDecimal.valueOf(free));
        } else if (purchased < free) {
          return freeProduct.price().multiply(BigDecimal.valueOf(purchased));
        }
      }
    }

    return BigDecimal.ZERO;
  }
}
