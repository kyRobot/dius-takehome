package com.kyrobot.shopping;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTest {

  private final Product ipad = Product.create("ipd", "549.99");
  private final Product macbook = Product.create("mbp", "1399.99");
  private final Product appletv = Product.create("atv", "109.50");
  private final Product cable = Product.create("vga", "30.00");

  private final Deal appletvMulti = new MultiBuyDeal(appletv, 3, appletv.price());
  private final Deal ipadBulkBuy = new BulkBuyDeal(ipad, 4, BigDecimal.valueOf(100));
  private final Deal cableBundle = new BundleDeal(macbook, cable);
  private Checkout checkout;

  @Before
  public void setUp() {
    checkout = baseCheckout();
  }

  private Checkout baseCheckout() {
    return new Checkout(
        Set.of(ipad, macbook, appletv, cable), Set.of(appletvMulti, ipadBulkBuy, cableBundle));
  }

  @Test
  public void noItemTotalIsZero() {
    var total = checkout.total();
    var expected = BigDecimal.ZERO.add(BigDecimal.ZERO);
    assertThat("No items scanned should be 0 total", total.compareTo(expected), equalTo(0));
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
    checkout.scan(ipad.sku());
    checkout.scan(macbook.sku());
    var ipadFirst = checkout.total();

    var anotherCashier = baseCheckout();
    anotherCashier.scan(macbook.sku());
    anotherCashier.scan(ipad.sku());
    var macbookFirst = anotherCashier.total();

    assertThat("Scan order changed total", ipadFirst, equalTo(macbookFirst));
  }

  @Test
  public void discountsApply() {
    checkout.scan(appletv.sku());
    checkout.scan(ipad.sku());
    checkout.scan(macbook.sku());
    checkout.scan(appletv.sku());
    checkout.scan(appletv.sku());
    var expected = appletv.price().add(ipad.price()).add(macbook.price()).add(appletv.price());
    var total = checkout.total();
    assertThat("Deals not applied", total, equalTo(expected));
  }
}
