package org.example.coffeeorder.service

import org.example.coffeeorder.model.Order
import org.example.coffeeorder.repository.CoffeeRepository
import org.example.coffeeorder.repository.MemberRepository
import org.example.coffeeorder.repository.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service

class OrderService(
  private val memberRepository: MemberRepository,
  private val coffeeRepository: CoffeeRepository,
  private val orderRepository: OrderRepository
) {
  fun order(memberId: Long, coffeeId: Long): Order {
    val member =
      memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("No member with id $memberId")

    val coffee =
      coffeeRepository.findByIdOrNull(coffeeId) ?: throw IllegalArgumentException("No coffee with id $coffeeId")

    check(member.balance >= coffee.price) {
      "Balance of member is not enough. member=${member.id}"
    }

    member.balance -= coffee.price

    return orderRepository.save(Order(member = member, coffee = coffee))
  }
}
