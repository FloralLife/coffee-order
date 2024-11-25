package org.example.coffeeorder.repository

import org.example.coffeeorder.model.Member
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : CrudRepository<Member, Long> {
}
