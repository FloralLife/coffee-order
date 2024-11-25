package org.example.coffeeorder.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AllArgsConstructor

@Entity
@AllArgsConstructor
class Order(
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long
)
