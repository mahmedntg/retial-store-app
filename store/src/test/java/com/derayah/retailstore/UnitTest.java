package com.derayah.retailstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * UnitTest.
 *
 * @author : Mo Sayed
 * @since : 5/25/2022
 */
public abstract class UnitTest extends Test<UnitTest> {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws Exception {
        setMvc(mvc);
    }

    protected String readResourceFile(final String resourceFileName) throws IOException {
        ClassPathResource cpr = new ClassPathResource(resourceFileName);
        byte[] data = FileCopyUtils.copyToByteArray(cpr.getInputStream());
        return new String(data, StandardCharsets.UTF_8);
    }

}
