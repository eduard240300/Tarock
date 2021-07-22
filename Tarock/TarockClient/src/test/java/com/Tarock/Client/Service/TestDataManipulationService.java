package com.Tarock.Client.Service;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestDataManipulationService {
    @Test
    public void testBoolToString()
    {
        assertTrue(DataManipulationService.boolToString(true).equals("1"));
        assertTrue(DataManipulationService.boolToString(false).equals("0"));
    }
}
