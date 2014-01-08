package com.store

/**
 * @author Pradeep Muralidharan
 */

/**
 * Case class representing the Item
 * @param sku
 * @param price
 */
case class Item(sku:String, price:Float)

/**
 * Class Rule representing the Discount rules
 * Defined as class to cater for extensions, or more sophisticated implementation
 * @param item
 * @param discountedPrice
 * @param qty
 */
class Rule(item:Item, discountedPrice:Float, qty:Int) {

  /**
   * Return the discount price based on the quantity
   * @param qty
   * @return
   */
  def discountPriceForQty(qty:Int):Float = {
   return qty%this.qty match {
      case 0 => this.discountedPrice * (qty/this.qty)
      case x => (this.discountedPrice * (qty/this.qty))+ (x * item.price)
    }
  }

  /**
   * Checks the relevance of Rule
   * @param item
   * @param qty
   * @return
   */
  def matches(item:Item,qty:Int):Boolean = this.item.equals(item)&& (this.qty== qty || qty > this.qty )


}


/**
 * The idea is to implement the checkout functionality in totally functional fashion,
 * all the methods defined do not have any side effects and truly functional which do not modify and data element
 * Idea is to use Map-Reduce technique.
 *    1) Map the correct price to the item based on the discount rules
 *    2) Reduce/ Accumulate the correct prices to derive the total price
 * Checkout is defined as an object, which provides a singleton implementation of Checkout with functions
 * The map and reduce functions which in this case mapCorrectPrice and cumulativePrice uses tail-recursion for efficiency
 */
object Checkout {

  /**
   * Returns the total price of items applying the appropriate rules
   * @param items
   * @param rules
   * @return
   */
  def total(items:List[Item],rules:List[Rule]):Float = {
   val itemsWithQty:Map[Item,Int]= items map (t => (t,items count(_== t))) toMap;
   totalDiscountedPrice(itemsWithQty.toList, rules)
  }

  /**
   * Returns the total un-discounted price
   * @param items
   * @return
   */
  def totalUnDiscountedPrice(items:List[Item]):Float = {
    val itemsWithQty:Map[Item,Int]= items map (t => (t,items count(_== t))) toMap;
    totalDiscountedPrice(itemsWithQty.toList, Nil)
  }

  /**
   * Total price combination of the mapping the correct price and accumulating the total price.
   * Technique similar to Map-Reduce
   * @param items
   * @param rules
   * @return
   */

  def totalDiscountedPrice(items: List[(Item, Int)], rules: List[Rule] ):Float= {
    // for cumulative price the binary function is + / sum
    // the neutral element is Zero 
    return cumulativePrice((_+_),0f) (mapCorrectPrice (items, rules))
  }

  /**
   * A function which sums the price of items, technique is similar to reduce
   * @param function : a binary function used for accumulation
   * @param neutral  : a neutral element used for the base case
   * @param items    : List cf Tuple containing Item and correct price
   * @return
   */
  def cumulativePrice(function:((Float,Float) => Float), neutral:Float) (items:List[(Item,Float)]):Float={
    return items match {
      case Nil       => neutral
      case (x::xs)   => function(x._2,(cumulativePrice(function,neutral)(xs)))
    }
  }

  /**
   * Map the correct price if any discount rule applied else the total price of the products
   * technique similar to map
   * @param items
   * @param rules
   * @return
   */
  def mapCorrectPrice(items: List[(Item,Int)], rules: List[Rule]):List[(Item,Float)] = {
    return items match {
      case Nil      => Nil
      case x ::xs   => matchingRule(x, rules) match {
        case Some(rule) => (x._1,rule.discountPriceForQty(x._2)) +: mapCorrectPrice(xs, rules)
        case None       => (x._1, x._2 * x._1.price) +: mapCorrectPrice(xs, rules)
      }
    }
  }

  /**
   * Finds the matching rule for a given item from the list of rules
   * @param item
   * @param rules
   * @return
   */
  def matchingRule(item:(Item, Int), rules : List[Rule]) : Option[Rule] =  rules.find(x => x.matches(item._1, item._2))

}
