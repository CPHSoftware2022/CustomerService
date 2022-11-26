package com.example.demo.integration;

import com.example.demo.integration.testCRUDfunctionality.SpringIntegrationTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com.example.demo")
public class CucumberIntegrationTest extends SpringIntegrationTest {
}
