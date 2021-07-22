package com.Tarock.Common.Service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDataManipulationService {
    @Test
    public void testBoolToString()
    {
        assertEquals("1", DataManipulationService.boolToString(true));
        assertEquals("0", DataManipulationService.boolToString(false));
    }
}
