package com.leff.midi.event.meta;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestTimeSignature
{
    @Test
    public void testToString()
    {
        assertEquals("0 (0): TimeSignature 4/4", new TimeSignature(
                0, 0, 4, 4, TimeSignature.METER_QUARTER, TimeSignature.DEFAULT_DIVISION).toString());
        assertEquals("0 (0): TimeSignature 6/8", new TimeSignature(
                0, 0, 6, 8, TimeSignature.METER_EIGHTH, TimeSignature.DEFAULT_DIVISION).toString());
    }
}
