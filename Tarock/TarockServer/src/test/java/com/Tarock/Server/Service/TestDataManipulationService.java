package com.Tarock.Server.Service;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestDataManipulationService {
    @Test
    public void testContainsJAR()
    {
        String string = "jar:::";
        assertTrue(DataManipulationService.containsJAR(string));
    }
}
