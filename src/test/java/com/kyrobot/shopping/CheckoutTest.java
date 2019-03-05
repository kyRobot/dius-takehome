package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTest {

  private Checkout checkout;

  private final Product ipad = Product.create("ipd", "549.99");
  private final Product macbook = Product.create("mbp", "1399.99");
  private final Product appletv = Product.create("atv", "109.50");
  private final Product cable = Product.create("vga", "30.00");

  @Before
  public void setUp() {
    checkout = baseCheckout();
  }

  private Checkout baseCheckout() {
    return new Checkout(ipad, macbook, appletv, cable);
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

  @Test
  public void oneItemTotal() {
    var expected = ipad.price();
    checkout.scan(ipad.sku());
    var total = checkout.total();
    assertThat("Single scan price was wrong", total, equalTo(expected));
  }

  @Test
  public void oneKnowItemOneUnknownItemTotal() {
    var expected = ipad.price();
    checkout.scan(ipad.sku());
    checkout.scan("ipod");
    var total = checkout.total();
    assertThat("Unknown item changed total", total, equalTo(expected));
  }

  @Test
  public void scanOrderIsIrrelevant() {
    var expected = ipad.price().add(macbook.price());
    checkout.scan(ipad.sku());
    checkout.scan(macbook.sku());
    var ipadFirst = checkout.total();

    var anotherCashier = baseCheckout();
    anotherCashier.scan(macbook.sku());
    anotherCashier.scan(ipad.sku());
    var macbookFirst = anotherCashier.total();

    assertThat("Scan order changed total", ipadFirst, equalTo(macbookFirst));
  }


}