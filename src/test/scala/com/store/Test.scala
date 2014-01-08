package com.store

import org.scalatest.FunSuite

/**
 * @author Pradeep Muralidharan
 */
class CheckoutTest extends FunSuite{

  test("Items with different sku and price should be different"){
    val item1:Item = new Item("A",32.0F)
    val item2:Item = new Item("B",32.0F)
    assert(!item1.equals(item2))
  }

  test("Items with same sku and price should be same"){
    val item1:Item = new Item("A",32.0F)
    val item2:Item = new Item("A",32.0F)
    assert(item1.equals(item2))
  }

  test("Rule with different item should not match"){
    val item1:Item = new Item("A",32.0F)
    val item2:Item = new Item("B",32.0F)
    val rule:Rule = new Rule(item1,30,2)
    assert(!rule.matches(item2,3))
  }

  test("Rule with same item sku should match "){
    val item1:Item = new Item("A",32.0F)
    val rule:Rule = new Rule(item1,30.0F,2)
    assert(rule.matches(item1,3))
  }

  test("Discounted price when the quantity matches should be equal to the discount price"){
    val item1:Item = new Item("A",30.0F)
    val rule:Rule = new Rule(item1,50.0F,2)
    val price:Float = rule.discountPriceForQty(2);
    assert(price == 50.0F)
  }

  test("Discounted price when the quantity is greater by twice the discount price should be twice "){
    val item1:Item = new Item("A",30.0F)
    val rule:Rule = new Rule(item1,50.0F,2)
    val price:Float = rule.discountPriceForQty(4);
    assert(price == 100.0F)
  }

  test("Discounted price when the quantity is greater by twice plus one the discount price should be twice plus actual price "){
    val item1:Item = new Item("A",30.0F)
    val rule:Rule = new Rule(item1,50.0F,2)
    val price:Float = rule.discountPriceForQty(5);
    assert(price == 130.0F)
  }

  test("test checkout when no discount rule matches, total price should be total price of the product into times added "){
    val itemA:Item = new Item("A",30.0F)
    val itemB:Item = new Item("B",50.0F)
    val rule:Rule = new Rule(itemB,50.0F,2)
    val items:List[Item] = itemA :: itemA :: itemA :: Nil
    val rules:List[Rule] = rule :: Nil

    val price:Float = Checkout.total(items,rules)
    assert(price == 90.0F)
  }

  test("test checkout when  discount rule matches, the discount should be correctly applied"){
    val itemA:Item = new Item("A",25.0F)
    val itemB:Item = new Item("B",50.0F)
    val rule:Rule = new Rule(itemB,75.0F,2)
    val items:List[Item] = itemB :: itemB ::itemA :: itemA :: itemA :: Nil
    val rules:List[Rule] = rule :: Nil

    val price:Float = Checkout.total(items,rules)
    assert(price == 150.0F)
  }

}
