package com.leff.midi.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestNoteOn
{
    @Test
    public void testToString()
    {
        assertEquals("0 (0): NoteOn ch0 C4 127", new NoteOn(0, 0, 60, 127).toString());
        assertEquals("0 (0): NoteOn ch0 Db/C#4 0 (note-off)", new NoteOn(0, 0, 61, 0).toString());
    }
}
