package com.Tarock.Client.GUI.TalonSelectionForm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.Tarock.Common.Service.DataManipulationService;

public class TestControllerTalonSelectionForm {
    @Test
    public void testBoolToString()
    {
        assertEquals("1", DataManipulationService.boolToString(true));
        assertEquals("0", DataManipulationService.boolToString(false));
    }
}
