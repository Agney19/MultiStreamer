package com.moneytransfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MoneyTransferApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    public void setContext(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void createUser_Success() {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user")
                .content(TestUtils.toJsonBytes(dto))
                .contentType(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

}
