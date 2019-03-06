package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class BundleDealTest {

  private Product paidProduct = Product.create("paid", "5");
  private Product freeProduct = Product.create("free", "1");
  private Deal bundleDeal = new BundleDeal(paidProduct, freeProduct);

  @Test
  public void noProductsPurchaseNoDeal() {
    var expected = BigDecimal.ZERO;
    var discount = bundleDeal.apply(Collections.emptyList());
    assertThat("Discount without minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void noPaidProductPurchaseNoDeal() {
    var expected = BigDecimal.ZERO;
    var discount = bundleDeal.apply(List.of(freeProduct));
    assertThat("Discount without minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void noFreeProductPurchaseNoDeal() {
    var expected = BigDecimal.ZERO;
    var discount = bundleDeal.apply(List.of(paidProduct));
    assertThat("Discount without minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void minimumPurchaseGivesDeal() {
    var expected = freeProduct.price();
    var discount = bundleDeal.apply(List.of(paidProduct, freeProduct));
    assertThat("no Discount with minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void onlyDiscountFreePerPaid() {
    var expected = freeProduct.price();
    var discount = bundleDeal.apply(List.of(paidProduct, freeProduct, freeProduct));
    assertThat("no Discount per item with minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void multiPaidOnlyOneFreeOnlyOneDiscount() {
    var expected = freeProduct.price();
    var discount = bundleDeal.apply(List.of(paidProduct, paidProduct, freeProduct));
    assertThat("no Discount per item with minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void multiDiscounts() {
    var expected = freeProduct.price().multiply(BigDecimal.valueOf(3L));
    var discount =
        bundleDeal.apply(
            List.of(paidProduct, paidProduct, paidProduct, freeProduct, freeProduct, freeProduct));
    assertThat("no Discount per item with multi purchase", discount, equalTo(expected));
  }
}
