package org.example.coffeeorder.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import lombok.AllArgsConstructor

@Entity
@AllArgsConstructor
class Order(
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coffee_id")
  var coffee: Coffee,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  var member: Member
)
