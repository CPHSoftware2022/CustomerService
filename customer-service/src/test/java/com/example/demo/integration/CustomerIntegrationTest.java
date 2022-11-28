package com.example.demo.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(features = "src/test/resources/com.example.demo")
public class CustomerIntegrationTest {
}
