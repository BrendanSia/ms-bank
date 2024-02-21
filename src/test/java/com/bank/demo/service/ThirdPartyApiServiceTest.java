package com.bank.demo.service;

import com.bank.demo.service.ThirdPartyApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ThirdPartyApiServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ThirdPartyApiService thirdPartyApiService;

    private final String apiUrl = "https://testing.com/thirdPartyApi";

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(thirdPartyApiService, "apiUrl", apiUrl);
    }

    @Test
    public void testCallThirdPartyApi_Success() {
        String expectedResponse = "Test";

        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(expectedResponse);

        String response = thirdPartyApiService.callThirdPartyApi();

        assertEquals(expectedResponse, response);

        verify(restTemplate, times(1)).getForObject(apiUrl, String.class);
    }
}
