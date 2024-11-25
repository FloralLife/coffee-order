package org.example.coffeeorder.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "members")
class Member (
  @Id
  var id: Long,
  var balance: Int
)
