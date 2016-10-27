package com.sasconsul.restcoding.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.googlecode.zohhak.api.TestWith;
import com.sasconsul.restcoding.service.impl.RegistryServiceImpl;
import com.sasconsul.restcoding.utils.RegistryUtils;

/**
 * Created by sasconsul on 10/19/16.
 * Copyright sasconsul 2016 -
 */

/**
 * Test class for the RegistryServiceImpl REST controller.
 *
 * @see RegistryServiceImpl
 */
@RunWith(SpringRunner.class)
public class RegistryServiceImplTest {

    private RegistryServiceImpl registryServiceImpl = new RegistryServiceImpl();

        
    @TestWith({ "abc" , "ABBC", "abbc", "" })
    void testGenerateStringId(String fInput) {
        long ans = registryServiceImpl.generateStringId(fInput, 0);
        long expected = RegistryUtils.validateGenerateStringId(fInput);

        assert expected == ans;
    }

    //TODO more RegistryService tests.
}
