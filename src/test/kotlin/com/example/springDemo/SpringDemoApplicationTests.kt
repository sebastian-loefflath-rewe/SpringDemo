package com.example.springDemo

import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.test.Ignore

@SpringBootTest
@AutoConfigureMockMvc
@Ignore
class SpringDemoApplicationTests(
	@Autowired private val mockMvc: MockMvc
) {

	@Test
	fun `get all greetings`() {
		// generate greetings
		mockMvc.get("/greetings/One")
		mockMvc.get("/greetings/Two") {
			contentType = MediaType.APPLICATION_JSON
			content = """ { "age": 42 } """
		}
		
		mockMvc.get("/greetings")
			.andExpect { jsonPath("$.size()", IsEqual(2)) }
			.andExpect { jsonPath("$[0].name", IsEqual("One")) }
			.andExpect { jsonPath("$[1].name", IsEqual("Two")) }
			.andExpect { jsonPath("$[1].age", IsEqual(42)) }
	}

}
