package com.leff.midi.event.meta;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestKeySignature
{
    @Test
    public void testToString()
    {
        assertEquals("0 (0): KeySignature C major", 
                new KeySignature(0, 0, 0, 0).toString());
        assertEquals("0 (0): KeySignature F# minor", 
                new KeySignature(0, 0, 3, 1).toString());
        assertEquals("0 (0): KeySignature Db major", 
                new KeySignature(0, 0, -5, 0).toString());
    }
}
