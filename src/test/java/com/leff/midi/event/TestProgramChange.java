package com.leff.midi.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestProgramChange
{
    @Test
    public void testToString()
    {
        assertEquals("0 (0): ProgramChange ACOUSTIC_GRAND_PIANO",
                new ProgramChange(0, 0, 0).toString());
    }
}
