package br.ifes.leds.sincap.webservice

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

import javax.servlet.Filter

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = Application)
@WebAppConfiguration
class BaseMocksTests {
    MockMvc mockMvc

    @Autowired
    WebApplicationContext context
    @Autowired
    Filter springSecurityFilterChain

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = webAppContextSetup(context)
            .addFilter(springSecurityFilterChain)
            .build()
    }
}
