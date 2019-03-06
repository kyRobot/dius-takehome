# dius-takehome
A takehome challenge from Dius

It is a Java 11 App with no cli or GUI so an example is provided for expected usage. There are also tests which serve the dual purpose of example API use & verification.

This is a simple checkout for a shop selling computer goods.
It has the concept of Products which are a tuple of SKU and Price.
It has the concept of Deals which provide discounts for various buying choices:
  * MultiBuy - e.g Buy 3 for the price of 2
  * BulkBuy - buy 4 get a discount on price for every item
  * Bundle - buy x get y free

## Caveats and Future extension possibility
The Checkout logic is very simple, all complex operations are passed into it. This allows easier testing and dependency changes in future.<br>
There is no concept of currency or money formatting however that could be injected in future very simply.<br>
There is no human readable output however a ReceiptPrinter of such could be added later.<br>
Product & Deals are created programatically however the Example project shows how external configuration is simple to produce and the Checkout would need no changes to allow for such configuration in future.<br>
