package com.example.springDemo.greeting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    
    @GetMapping("/greetings/world")
    fun greeting() = "Hello, world!"
    
    @GetMapping("/greetings")
    fun allGreetings(): List<Greeting> = TODO("not implemented yet")
}

data class Person(
    val age: Int
)