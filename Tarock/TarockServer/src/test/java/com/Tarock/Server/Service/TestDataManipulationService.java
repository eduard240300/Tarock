package com.Tarock.Server.Service;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import com.Tarock.Common.Service.*;

public class TestDataManipulationService {
    @Test
    public void testContainsJAR()
    {
        String string = "jar:::";
        assertTrue(DataManipulationService.containsJAR(string));
    }
}
