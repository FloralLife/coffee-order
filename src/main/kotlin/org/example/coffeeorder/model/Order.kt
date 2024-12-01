package org.example.coffeeorder.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import lombok.Builder

@Entity
@Builder
@Table(name = "orders")
class Order(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long?,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  var member: Member,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coffee_id", nullable = false)
  var coffee: Coffee

) {
  constructor(member: Member, coffee: Coffee) : this(null, member, coffee)
}
