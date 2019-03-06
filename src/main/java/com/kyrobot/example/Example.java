package com.kyrobot.example;

import com.kyrobot.shopping.Checkout;
import com.kyrobot.shopping.Deal;
import com.kyrobot.shopping.Product;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public class Example {

  public void setupCheckout(Properties pricingRules) {
    // Properties would be a file with format similar to:
    // ipd=599.99, BULKBUY, 4, 100
    // atv=109.50, MULTIBUY, 3, 109.50

    var pricing = new PricingRules(pricingRules);
    var checkout = new Checkout(pricing.products(), pricing.deals());
    checkout.scan("sku");
    checkout.total();

  }

  class PricingRules {

    public PricingRules(Properties externallyDefined) {
    }

    private Set<Product> products() {
      return Collections.emptySet();
    }

    private Set<Deal> deals() {
      return Collections.emptySet();
    }
  }
}
