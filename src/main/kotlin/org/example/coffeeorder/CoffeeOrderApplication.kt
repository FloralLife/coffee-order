package org.example.coffeeorder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoffeeOrderApplication

fun main(args: Array<String>) {
  runApplication<CoffeeOrderApplication>(*args)
}
