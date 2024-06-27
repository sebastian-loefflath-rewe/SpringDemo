package com.example.springDemo.greeting

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.string
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.random.Random

@WebMvcTest(GreetingController::class)
class GreetingControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    
    private val randomName = (1..6).map { "abcdefghijklmnopqrstuvwxyz".random() }.joinToString(separator = "")
    
    @Test
    fun `hello world`() {
        mockMvc.get("/greetings/world")
            .andExpect { content { string("Hello, world!") } }
    }
    
    @Test
    fun `hello $name`() {
        mockMvc.get("/greetings/$randomName")
            .andExpect { content { string("Hello, $randomName!") } }
    }
    
    @Test
    fun `hello $name with age`() {
        val randomAge = Random.nextInt(1, 100)
        
        mockMvc.get("/greetings/$randomName") {
            contentType = MediaType.APPLICATION_JSON
            content = """ { "age": $randomAge } """
        }
            .andExpect { content { string("Hello, $randomName! You are $randomAge years old.") } }
    }
    
    @Test
    fun `too old`() {
        val randomAge = Random.nextInt(101, 200)
        
        mockMvc.get("/greetings/$randomName") {
            contentType = MediaType.APPLICATION_JSON
            content = """ { "age": $randomAge } """
        }
            .andExpect { status { is4xxClientError() } }
    }
}