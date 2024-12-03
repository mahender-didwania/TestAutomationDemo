// package com.example.stepdefinitions;

// import java.time.Duration;

// import org.testcontainers.containers.GenericContainer;
// import org.testcontainers.containers.wait.strategy.Wait;

// import io.cucumber.java.AfterAll;
// import io.cucumber.java.BeforeAll;
// import io.cucumber.java.en.Given;

// public class CypressTestDefinition {

//     private static GenericContainer<?> cypressContainer;

//     @BeforeAll
//     public static void setUp() {
//         // Start the container before all tests
//         cypressContainer = new GenericContainer<>("cypress/included:13.14.2")
//                 .withExposedPorts(3000)
//                 .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(10)));

//         cypressContainer.start();
//     }

//     @Given("cypress tests have been invoked")
//     public void test(){
//     }

//     @AfterAll
//     public static void tearDown() {
//         if (cypressContainer != null) {
//             cypressContainer.stop(); // Stop the container after the tests are complete
//         }
//     }
// }
