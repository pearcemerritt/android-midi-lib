package com.leff.midi.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestNoteOff
{
    @Test
    public void testToString()
    {
        assertEquals("0 (0): NoteOff ch0 C4 0", new NoteOff(0, 0, 60, 0).toString());
    }
}
