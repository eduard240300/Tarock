package com.Tarock.Client.Service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.Tarock.Common.Service.DataManipulationService;

public class TestCommunicationService {
    @Test
    public void testBoolToString()
    {
        assertEquals("1", DataManipulationService.boolToString(true));
        assertEquals("0", DataManipulationService.boolToString(false));
    }
}
