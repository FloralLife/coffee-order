package org.example.coffeeorder.controller

import org.example.coffeeorder.TestUtils.randomId
import org.example.coffeeorder.model.Coffee
import org.example.coffeeorder.model.Member
import org.example.coffeeorder.repository.CoffeeRepository
import org.example.coffeeorder.repository.MemberRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerIT @Autowired constructor(
  val mockMvc: MockMvc,
  val memberRepository: MemberRepository,
  val coffeeRepository: CoffeeRepository
) {
  companion object {
    @Container
    private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.2.0").apply {
      withDatabaseName("coffee-order-test")
      withReuse(true)
    }

    init {
      mysqlContainer.start()
    }
  }

//
//  @DynamicPropertySource
//  fun overrideProperties(registry: DynamicPropertyRegistry) {
//    registry.add("spring.datasource.url") { mysqlContainer.jdbcUrl }
//    registry.add("spring.datasource.username") { mysqlContainer.username }
//    registry.add("spring.datasource.password") { mysqlContainer.password }
//  }


  private lateinit var member: Member
  private lateinit var coffee: Coffee
  private lateinit var premiumCoffee: Coffee

  @BeforeAll
  fun init() {
    coffee = coffeeRepository.save(Coffee(randomId(), 5000))
    premiumCoffee = coffeeRepository.save(Coffee(randomId(), 10000))
  }

  @BeforeEach
  fun setup() {
    member = memberRepository.save(Member(null, 6000))
  }

  @AfterEach
  fun cleanUp() {
    memberRepository.deleteAll()
  }

  @AfterAll
  fun tearDown() {
    coffeeRepository.deleteAll()
  }

  @Test
  fun orderNotExistMemberThenThrowException() {
    mockMvc.post("/api/orders") {
      contentType = MediaType.APPLICATION_JSON
      content = "{\"memberId\": ${randomId()}, \"coffeeId\": ${coffee.id}}"
      accept = MediaType.APPLICATION_JSON
    }.andExpect { status { isNotFound() } }
  }

  @Test
  fun orderNotExistCoffeeThenThrowException() {
    mockMvc.post("/api/orders") {
      contentType = MediaType.APPLICATION_JSON
      content = "{\"memberId\": ${member.id}, \"coffeeId\": ${randomId()}}"
    }.andExpect { status { isNotFound() } }
  }

  @Test
  fun orderTooExpensiveCoffeeThenThrowException() {
    mockMvc.post("/api/orders") {
      contentType = MediaType.APPLICATION_JSON
      content = "{\"memberId\": ${member.id}, \"coffeeId\": ${premiumCoffee.id}}"
    }.andExpect { status { isBadRequest() } }
  }

  @Test
  fun order() {
    mockMvc.post("/api/orders") {
      contentType = MediaType.APPLICATION_JSON
      content = "{\"memberId\": ${member.id}, \"coffeeId\": ${coffee.id}}"
    }.andExpectAll {
      status { isOk() }
      jsonPath("$.memberId") { value(member.id) }
      jsonPath("$.coffeeId") { value(coffee.id) }
    }.andDo {
      val member = memberRepository.findById(member.id!!).get()
      assertEquals(1000, member.balance)
    }
  }
}
