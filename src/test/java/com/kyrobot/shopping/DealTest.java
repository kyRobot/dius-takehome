package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;

public class DealTest {

  @Test
  public void noDiscountDefault() {
    var discount = new Deal() {
    }.applyTo(List.of(Product.create("none", "200")));
    assertThat("default discount should be zero", discount, equalTo(BigDecimal.ZERO));
  }
}