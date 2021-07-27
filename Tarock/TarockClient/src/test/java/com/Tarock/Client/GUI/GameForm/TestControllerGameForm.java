package com.Tarock.Client.GUI.GameForm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.Tarock.Common.Service.DataManipulationService;

public class TestControllerGameForm {
    @Test
    public void testBoolToString()
    {
        assertEquals("1", DataManipulationService.boolToString(true));
        assertEquals("0", DataManipulationService.boolToString(false));
    }
}
