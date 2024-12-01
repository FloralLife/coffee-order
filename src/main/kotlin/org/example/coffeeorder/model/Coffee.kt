package org.example.coffeeorder.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "coffee")
class Coffee(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long?,
  var price: Int,

  @OneToMany(mappedBy = "coffee", cascade = [(CascadeType.ALL)])
  var orders: MutableList<Order> = mutableListOf()
)
