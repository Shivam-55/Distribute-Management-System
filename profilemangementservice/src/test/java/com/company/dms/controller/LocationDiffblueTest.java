package com.company.dms.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.dms.controller.Location;
import com.company.dms.otherservicecall.LocationService;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@WebFluxTest(controllers = {Location.class})
@ContextConfiguration(classes = {Location.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LocationDiffblueTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private Location location;

    @MockBean
    private LocationService locationService;

    /**
     * Method under test: {@link Location#getLocation(String)}
     */
    @Test
    void testGetLocation() throws Exception {
        // Arrange
        Mono<String> justResult = Mono.just("Data");
        when(locationService.getCityByPincode(Mockito.<String>any())).thenReturn(justResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/location/pincode/{pinCode}",
                "Pin Code");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(location)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Data"));
    }

    /**
     * Method under test: {@link Location#getLocation(String)}
     */
    @Test
    void testGetLocation2() throws Exception {
        // Arrange
        when(locationService.getCityByPincode(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/location/pincode/{pinCode}",
                "Pin Code");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(location).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Enter valid PinCode"));
    }

    /**
     * Method under test: {@link Location#getLocation(String)}
     */
    @Test
    void testGetLocation3() throws Exception {
        // Arrange
        when(locationService.getCityByPincode(Mockito.<String>any()))
                .thenThrow(new RuntimeException("Enter valid PinCode"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/location/pincode/{pinCode}",
                "Pin Code");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(location).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Enter valid PinCode"));
    }

    /**
     * Method under test: {@link Location#getPinCodesByCity(String)}
     */
    @Test
    void testGetPinCodesByCity() {
        // Arrange
        when(locationService.getPincodesByCity(Mockito.anyString()))
                .thenReturn(Mono.just(Collections.singletonList("12345")));

        // Act & Assert
        webTestClient.get().uri("/location/city/{city}", "TestCity")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .isEqualTo((Collections.singletonList("12345")));
    }
}
