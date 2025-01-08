package com.example.kotlinboilerplate.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.util.*

@MappedSuperclass
class BaseEntity (
    @Column(name = "create_date", nullable = false, updatable = false)
    val createDate: Date = Date(),

    @Column(name = "update_date")
    var updateDate: Date = Date(),
) {
    @PrePersist
    fun prePersist() {
        updateDate = createDate // createDate와 updateDate를 동일하게 설정
    }

    @PreUpdate
    fun preUpdate() {
        updateDate = Date() // update 시 updateDate만 최신화
    }
}