package org.example.coffeeorder.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Coffee(
  @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long
)