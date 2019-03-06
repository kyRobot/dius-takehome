package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class BulkBuyDealTest {

  private final Product product = Product.create("p", "5");
  private final Deal bulkBuy3 = new BulkBuyDeal(product, 3, BigDecimal.ONE);

  @Test
  public void noMinimumPurchaseNoDeal() {
    var expected = BigDecimal.ZERO;
    var discount = bulkBuy3.apply(List.of(product));
    assertThat("Discount without minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void noPurchaseNoDeal() {
    var expected = BigDecimal.ZERO;
    var discount = bulkBuy3.apply(List.of());
    assertThat("Discount given without minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void minimumPurchaseGivesDeal() {
    var expected = new BigDecimal("3");
    var discount = bulkBuy3.apply(List.of(product, product, product));
    assertThat("no Discount with minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void minimumPurchaseGivesDealPerItem() {
    var expected = new BigDecimal("4");
    var discount = bulkBuy3.apply(Collections.nCopies(4, product));
    assertThat("no Discount per item with minimum purchase", discount, equalTo(expected));
  }
}