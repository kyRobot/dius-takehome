package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTest {

  private Checkout checkout;

  @Before
  public void setUp() {
    checkout = new Checkout();
  }

  @Test
  public void noItemTotalIsZero() {
    var total = checkout.total();
    assertThat("No items scanned should be 0 total", total, equalTo(BigDecimal.ZERO));
  }

  @Test
  public void scanningNothingDoesNotAffectTotal() {
    var preScan = checkout.total();
    checkout.scan("");
    var postScan = checkout.total();
    assertThat("No items scanned should be same total", preScan, equalTo(postScan));
  }

  @Test
  public void totalDoesNotChangeIfCheckedMultiple() {
    var total = checkout.total();
    var secondTotal = checkout.total();
    assertThat("Multiple totals should be equal", total, equalTo(secondTotal));
  }
}