package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class MultiBuyDealTest {

  private final Product product = Product.create("p", "5");
  private final Deal multiBuy3for2 = new MultiBuyDeal(product, 3, product.price());

  @Test
  public void noMinimumPurchaseNoDeal() {
    var expected = BigDecimal.ZERO;
    var discount = multiBuy3for2.apply(List.of(product));
    assertThat("Discount without minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void minimumPurchaseGivesDeal() {
    var expected = product.price();
    var discount = multiBuy3for2.apply(List.of(product, product, product));
    assertThat("no Discount with minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void dealAppliedOnceForOverbuy() {
    var expected = product.price();
    var discount = multiBuy3for2.apply(List.of(product, product, product, product));
    assertThat("no Discount with minimum purchase", discount, equalTo(expected));
  }

  @Test
  public void multipleDealsApplied() {
    var expected = product.price().add(product.price());
    var discount = multiBuy3for2.apply(Collections.nCopies(6, product));
    assertThat("no Discount with minimum purchase", discount, equalTo(expected));
  }
}