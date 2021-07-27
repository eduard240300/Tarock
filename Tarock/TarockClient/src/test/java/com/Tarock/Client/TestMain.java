package com.Tarock.Client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.Tarock.Common.Service.DataManipulationService;

public class TestMain {
    @Test
    public void testBoolToString()
    {
        assertEquals("1", DataManipulationService.boolToString(true));
        assertEquals("0", DataManipulationService.boolToString(false));
    }
}
