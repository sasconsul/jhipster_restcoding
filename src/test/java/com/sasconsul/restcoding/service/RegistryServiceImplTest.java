package com.sasconsul.restcoding.service;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

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
@RunWith(Parameterized.class)
public class RegistryServiceImplTest {

    private RegistryServiceImpl registryServiceImpl = new RegistryServiceImpl();

    @Parameters(name = "{index}: registry string=\"{0}\"")
    public static Iterable<? extends Object> data() {
    		return Arrays.asList( "abc" , "ABBC", "abbc", null, "" );
    }
    
    @Parameter
    public String fInput;
  
    @Test
    public void testGenerateStringId() {
	    	assertEquals(RegistryUtils.validateGenerateStringId(fInput),
	    			registryServiceImpl.generateStringId(fInput, 0) );
    }

    //TODO more RegistryService tests.
}
