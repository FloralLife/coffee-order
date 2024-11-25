package org.example.coffeeorder.service

import org.example.coffeeorder.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService (val repository: OrderRepository) {
}
