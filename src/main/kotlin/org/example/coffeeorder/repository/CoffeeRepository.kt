package org.example.coffeeorder.repository

import org.example.coffeeorder.model.Coffee
import org.springframework.data.jpa.repository.JpaRepository

interface CoffeeRepository: JpaRepository<Coffee, Long> {
}
