package org.example.coffeeorder.controller

import org.example.coffeeorder.model.Order
import org.example.coffeeorder.model.dto.OrderCreateDto
import org.example.coffeeorder.model.dto.OrderDto
import org.example.coffeeorder.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {

  @PostMapping
  fun createOrder(@RequestBody orderCreateDto: OrderCreateDto): OrderDto {
    val order: Order = orderService.order(orderCreateDto.memberId, orderCreateDto.coffeeId)
    return OrderDto(order.id, order.member.id, order.coffee.id)
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> {
    return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  fun handleBadRequest(e: IllegalStateException): ResponseEntity<String> {
    return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
  }
}
