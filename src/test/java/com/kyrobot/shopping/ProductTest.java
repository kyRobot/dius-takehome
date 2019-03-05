package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import org.junit.Test;

public class ProductTest {

  @Test
  public void create() {
    var product = Product.create("sku", "123.45");
    assertThat("Created product is null", product, is(notNullValue()));
  }

  @Test
  public void skuIsNormalised() {
    var imperfectSKU = "    aBc ";
    var expected = "abc";
    var product = Product.create(imperfectSKU, "999");
    assertThat("Created sku is not normalised", product.sku(), equalTo(expected));
  }

  @Test
  public void normalSKUIsNotChanged() {
    var perfectSKU = "aaa";
    var expected = "aaa";
    var product = Product.create(perfectSKU, "999");
    assertThat("Created sku is altered", product.sku(), equalTo(expected));
  }

  @Test
  public void negativePriceIsZeroed() {
    var negative = "-1";
    var expected = BigDecimal.ZERO;
    var product = Product.create("prod", negative);
    assertThat("Product price should not be negative", product.price(), equalTo(expected));
  }

  @Test
  public void nonNegativePriceIsAsExpected() {
    var negative = "10";
    var expected = BigDecimal.TEN;
    var product = Product.create("prod", negative);
    assertThat("Product price should not be big decimal", product.price(), equalTo(expected));
  }
}
