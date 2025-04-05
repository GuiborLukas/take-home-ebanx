package com.gmail.lgsc92.integration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gmail.lgsc92.TakeHomeEbanxApplication;

@SpringBootTest(classes = TakeHomeEbanxApplication.class)
@AutoConfigureMockMvc
public class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void resetState() throws Exception {
        // Reset do estado da aplicação antes de cada teste
        mockMvc.perform(post("/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    void testIntegrationSequence() throws Exception {
        // -- Reset state before starting tests (já foi feito no @BeforeEach)

        // -- Get balance for non-existing account
        mockMvc.perform(get("/balance?account_id=1234"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("0"));

        // -- Create account with initial balance
        String depositJson = "{\"type\":\"deposit\", \"destination\":\"100\", \"amount\":10}";
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(depositJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination.id", is("100")))
                .andExpect(jsonPath("$.destination.balance", is(10)));

        // -- Deposit into existing account
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(depositJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination.id", is("100")))
                .andExpect(jsonPath("$.destination.balance", is(20)));

        // -- Get balance for existing account
        mockMvc.perform(get("/balance?account_id=100"))
                .andExpect(status().isOk())
                .andExpect(content().string("20"));

        // -- Withdraw from non-existing account
        String withdrawNonExisting = "{\"type\":\"withdraw\", \"origin\":\"200\", \"amount\":10}";
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(withdrawNonExisting))
                .andExpect(status().isNotFound())
                .andExpect(content().string("0"));

        // -- Withdraw from existing account
        String withdrawJson = "{\"type\":\"withdraw\", \"origin\":\"100\", \"amount\":5}";
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(withdrawJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin.id", is("100")))
                .andExpect(jsonPath("$.origin.balance", is(15)));

        // -- Transfer from existing account
        String transferJson = "{\"type\":\"transfer\", \"origin\":\"100\", \"amount\":15, \"destination\":\"300\"}";
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transferJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin.id", is("100")))
                .andExpect(jsonPath("$.origin.balance", is(0)))
                .andExpect(jsonPath("$.destination.id", is("300")))
                .andExpect(jsonPath("$.destination.balance", is(15)));

        // -- Transfer from non-existing account
        String transferNonExisting = "{\"type\":\"transfer\", \"origin\":\"200\", \"amount\":15, \"destination\":\"300\"}";
        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transferNonExisting))
                .andExpect(status().isNotFound())
                .andExpect(content().string("0"));
    }
}
