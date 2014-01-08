package com.store

import org.scalatest.FunSuite

/**
 * @author Pradeep Muralidharan
 */
class CheckoutTest extends FunSuite{

  test("Items with different sku and price should be different"){
    // Init
    val item1:Item = new Item("A",32.0F)
    val item2:Item = new Item("B",32.0F)
    // Assert
    assert(!item1.equals(item2))
  }

  test("Items with same sku and price should be same"){
    val item1:Item = new Item("A",32.0F)
    val item2:Item = new Item("A",32.0F)
    assert(item1.equals(item2))
  }

  test("Rule with different item sku should not match"){
    // Init
    val item1:Item = new Item("A",32.0F)
    val item2:Item = new Item("B",32.0F)
    val rule:Rule = new Rule(item1,30,2)
    // Assert
    assert(!rule.matches(item2,3))
  }

  test("Rule with same item sku should match "){
    // Init
    val item1:Item = new Item("A",32.0F)
    val rule:Rule = new Rule(item1,30.0F,2)
    // Assert
    assert(rule.matches(item1,3))
  }

  test("Discounted price when the quantity matches should be equal to the discount price"){
    // Init
    val item1:Item = new Item("A",30.0F)
    val rule:Rule = new Rule(item1,50.0F,2)
    // SUT
    val price:Float = rule.discountPriceForQty(2);
    // Assert
    assert(price == 50.0F)
  }

  test("Discounted price when the quantity is greater by twice the discount price should be twice "){
    // Init
    val item1:Item = new Item("A",30.0F)
    val rule:Rule = new Rule(item1,50.0F,2)
    // SUT
    val price:Float = rule.discountPriceForQty(4);
    // Assert
    assert(price == 100.0F)
  }

  test("Discounted price when the quantity is greater by twice plus one the discount price should be twice plus actual price "){
    // Init
    val item1:Item = new Item("A",30.0F)
    val rule:Rule = new Rule(item1,50.0F,2)
    // SUT
    val price:Float = rule.discountPriceForQty(5);
    // Assert
    assert(price == 130.0F)
  }

  test("Total price should be zero when list of items is empty "){
    // SUT
    val totalPrice = Checkout.total(Nil,Nil);
    // Assert
    assert(totalPrice == 0F)
  }

  test("Total price of items should be the total price of products, when discount rules are not present"){
    // Init
    val item = new Item("A",10.0F)
    val items = item :: item :: item :: Nil
    // SUT
    val totalPrice = Checkout.total(items,Nil)
    // Assert
    assert(totalPrice == 30.0F)
  }

  test("test checkout when no discount rule matches, total price should be total price of the product into times added "){
    // Init
    val itemA:Item = new Item("A",30.0F)
    val itemB:Item = new Item("B",50.0F)
    val rule:Rule = new Rule(itemB,50.0F,2)
    val items:List[Item] = itemA :: itemA :: itemA :: Nil
    val rules:List[Rule] = rule :: Nil
    // SUT
    val price:Float = Checkout.total(items,rules)
    // Assert
    assert(price == 90.0F)
  }

  test("test checkout when  discount rule matches, the discount should be correctly applied"){
    // Init
    val itemA:Item = new Item("A",25.0F)
    val itemB:Item = new Item("B",50.0F)
    val rule:Rule = new Rule(itemB,75.0F,2)
    val items:List[Item] = itemB :: itemB ::itemA :: itemA :: itemA :: Nil
    val rules:List[Rule] = rule :: Nil
   // SUT
    val price:Float = Checkout.total(items,rules)
    // Assert
    assert(price == 150.0F)
  }

}
