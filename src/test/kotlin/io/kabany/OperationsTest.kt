package io.kabany

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.kabany.operations.OperationsService
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import jakarta.inject.Inject

@QuarkusTest
class OperationsTest {

  @Inject
  lateinit var operationsService: OperationsService

  @Test
  fun shouldCreateListOfElements() {
    val list = operationsService.createList(1000)
    Assertions.assertEquals(1000, list.size)
    // First item
    Assertions.assertEquals(1, list.get(0).id)
    Assertions.assertEquals("This is the message number 1", list.get(0).message)
    // 100th item
    Assertions.assertEquals(100, list.get(99).id)
    Assertions.assertEquals("This is the message number 100", list.get(99).message)
    // 1000th item
    Assertions.assertEquals(1000, list.get(999).id)
    Assertions.assertEquals("This is the message number 1000", list.get(999).message)
  }

  @Test
  fun shouldAggregateItemsFromFibonacciSequence() {
    Assertions.assertEquals(0, operationsService.fibonacciSum(-1));
    Assertions.assertEquals(0, operationsService.fibonacciSum(0));
    Assertions.assertEquals(1, operationsService.fibonacciSum(1));
    Assertions.assertEquals(2, operationsService.fibonacciSum(2));
    Assertions.assertEquals(4, operationsService.fibonacciSum(3));
    Assertions.assertEquals(7, operationsService.fibonacciSum(4));
    Assertions.assertEquals(12, operationsService.fibonacciSum(5));
    Assertions.assertEquals(143, operationsService.fibonacciSum(10));
    Assertions.assertEquals(32951280098L, operationsService.fibonacciSum(50));
    //Assertions.assertEquals(927372692193078999175L, operationsService.fibonacciSum(100));
  }

  @Test
  fun shouldGenerateFibonacciList() {
    Assertions.assertEquals(0, operationsService.fibonacciList(-1).size);
    val list0 = operationsService.fibonacciList(0);
    Assertions.assertEquals(1, list0.size);
    Assertions.assertEquals(0, list0.get(0));
    val list1 = operationsService.fibonacciList(1);
    Assertions.assertEquals(2, list1.size);
    Assertions.assertEquals(1, list1.get(1));
    val list2 = operationsService.fibonacciList(2);
    Assertions.assertEquals(3, list2.size);
    Assertions.assertEquals(1, list2.get(2));
    val list3 = operationsService.fibonacciList(3);
    Assertions.assertEquals(4, list3.size);
    Assertions.assertEquals(2, list3.get(3));
    val list4 = operationsService.fibonacciList(4);
    Assertions.assertEquals(5, list4.size);
    Assertions.assertEquals(3, list4.get(4));
    val list5 = operationsService.fibonacciList(5);
    Assertions.assertEquals(6, list5.size);
    Assertions.assertEquals(5, list5.get(5));
    val list6 = operationsService.fibonacciList(6);
    Assertions.assertEquals(7, list6.size);
    Assertions.assertEquals(8, list6.get(6));
    val list7 = operationsService.fibonacciList(10);
    Assertions.assertEquals(11, list7.size);
    Assertions.assertEquals(55, list7.get(10));
    val list8 = operationsService.fibonacciList(50);
    Assertions.assertEquals(51, list8.size);
    Assertions.assertEquals(12586269025L, list8.get(50));
    val list9 = operationsService.fibonacciList(78);
    Assertions.assertEquals(79, list9.size);
    Assertions.assertEquals(8944394323791464L, list9.get(78));
  }
}