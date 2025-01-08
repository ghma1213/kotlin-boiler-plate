package com.example.kotlinboilerplate.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {
    @GetMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping("/token-test")
    fun tokenTest(): String {

        return "success"
    }
}