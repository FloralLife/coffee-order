package org.example.coffeeorder.service

import org.example.coffeeorder.TestUtils.randomId
import org.example.coffeeorder.model.Coffee
import org.example.coffeeorder.model.Member
import org.example.coffeeorder.model.Order
import org.example.coffeeorder.repository.CoffeeRepository
import org.example.coffeeorder.repository.MemberRepository
import org.example.coffeeorder.repository.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class OrderServiceTest {
  @Mock
  private lateinit var orderRepository: OrderRepository

  @Mock
  private lateinit var memberRepository: MemberRepository

  @Mock
  private lateinit var coffeeRepository: CoffeeRepository

  @InjectMocks
  private lateinit var orderService: OrderService

  @Test
  fun orderNotExistMemberThenThrowException() {
    val invalidMemberId = randomId()
    whenever(memberRepository.findById(invalidMemberId)).thenReturn(Optional.empty())

    assertThrows<IllegalArgumentException> { orderService.order(invalidMemberId, randomId()) }
  }

  @Test
  fun orderNotExistCoffeeThenThrowException() {
    val member = Member(randomId(), 6000)
    val invalidCoffeeId = randomId()
    whenever(memberRepository.findById(member.id!!)).thenReturn(Optional.of(member))
    whenever(coffeeRepository.findById(invalidCoffeeId)).thenReturn(Optional.empty())

    assertThrows<IllegalArgumentException> { orderService.order(member.id!!, invalidCoffeeId) }
  }

  @Test
  fun orderTooExpensiveCoffeeThenThrowException() {
    val member = Member(randomId(), 6000)
    val coffee = Coffee(randomId(), 10000)

    whenever(memberRepository.findById(member.id!!)).thenReturn(Optional.of(member))
    whenever(coffeeRepository.findById(coffee.id!!)).thenReturn(Optional.of(coffee))

    assertThrows<IllegalStateException> { orderService.order(member.id!!, coffee.id!!) }
  }

  @Test
  fun order() {
    val member = Member(randomId(), 6000)
    val coffee = Coffee(randomId(), 5000)

    whenever(memberRepository.findById(member.id!!)).thenReturn(Optional.of(member))
    whenever(coffeeRepository.findById(coffee.id!!)).thenReturn(Optional.of(coffee))
    whenever(orderRepository.save(any<Order>())).thenReturn(Order(randomId(), member, coffee))

    orderService.order(member.id!!, coffee.id!!)

    assertEquals(1000, member.balance)
  }
}
