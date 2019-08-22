package com.leff.midi.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestController
{
    @Test
    public void testToString()
    {
        // 64 is the damper pedal
        assertEquals("0 (0): Controller ch0 64 0",
                new Controller(0, 0, 64, 0).toString());
    }
}
