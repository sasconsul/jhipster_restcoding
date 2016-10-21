package com.sasconsul.restcoding.service;

import com.sasconsul.restcoding.JhipsterRestcodingApp;
import com.sasconsul.restcoding.service.impl.RegistryServiceImpl;

import com.sasconsul.restcoding.utils.RegistryUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sasconsul on 10/19/16.
 * Copyright sasconsul 2016 -
 */

/**
 * Test class for the UserResource REST controller.
 *
 * @see RegistryServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterRestcodingApp.class)
public class RegistryServiceImplTest {

    private RegistryServiceImpl registryServiceImpl = new RegistryServiceImpl();

    @Test
    public void testGenerateStringId() {

        long ans = registryServiceImpl.generateStringId("abc", 0);
        long expected = RegistryUtils.validateGenerateStringId("abc");

        assert expected == ans;
    }

}
