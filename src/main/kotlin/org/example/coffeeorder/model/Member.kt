package org.example.coffeeorder.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "members")
class Member(
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long?,
  var balance: Int,

  @OneToMany(mappedBy = "member", cascade = [(CascadeType.ALL)])
  var orders: MutableList<Order> = mutableListOf()
)
