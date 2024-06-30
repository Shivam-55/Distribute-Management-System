package com.company.dms.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.dms.controller.RetailerConnection;
import com.company.dms.dto.DistributorAndRetailerDto;
import com.company.dms.service.RetailerConnectionService;

@ContextConfiguration(classes = {RetailerConnection.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RetailerConnectionDiffblueTest {
    @Autowired
    private RetailerConnection retailerConnection;

    @MockBean
    private RetailerConnectionService retailerConnectionService;

    /**
     * Method under test: {@link RetailerConnection#getRetailerConnection(Long)}
     */
    @Test
    void testGetRetailerConnection() throws Exception {
        // Arrange
        DistributorAndRetailerDto buildResult = DistributorAndRetailerDto.builder()
                .distributorId(1L)
                .distributorName("Bella")
                .retailerId(1L)
                .retailerName("Bella")
                .build();
        when(retailerConnectionService.getRetailerConnection(Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/connection/{retailerId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(retailerConnection)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"distributorName\":\"Bella\",\"retailerId\":1,\"distributorId\":1,\"retailerName\":\"Bella\"}"));
    }
}
