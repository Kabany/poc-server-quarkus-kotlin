package io.kabany.operations

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
object OperationsService {

  fun createList(times: Int): List<OperationItem> {
    var list = ArrayList<OperationItem>()
    for (i in 1..times) {
      list.add(OperationItem(i, "This is the message number $i"))
    }
    return list
  }

  fun fibonacciSum(n: Int): Long {
    if (n <= 0) {
      return 0
    } else if (n == 1) {
      return 1
    }
    var fibo = ArrayList<Long>()
    fibo.add(0)
    fibo.add(1)
    var sum: Long = 1
    for (i in 2..n) {
      fibo.add(fibo.get(i-1) + fibo.get(i-2))
      sum += fibo.get(i)
    }
    return sum
  }

  fun fibonacciList(n: Int): List<Long> {
    var fibo = ArrayList<Long>()
    if (n < 0) {
      return fibo
    } else if (n == 0) {
      fibo.add(0)
      return fibo
    }
    fibo.add(0)
    fibo.add(1)
    if (n == 1) {
      return fibo
    }
    for (i in 2..n) {
      fibo.add(fibo.get(i-1) + fibo.get(i-2))
    }
    return fibo
  }
}

data class OperationItem(val id: Int, val message: String)