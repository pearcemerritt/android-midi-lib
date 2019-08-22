package com.leff.midi.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestMidiUtil
{
    @Test
    public void testMidiNoteToPitch()
    {
        assertEquals("C", MidiUtil.midiNoteToPitch(0));
        assertEquals("C", MidiUtil.midiNoteToPitch(108));
        assertEquals("Ab/G#", MidiUtil.midiNoteToPitch(56));
    }

    @Test
    public void testMidiNoteToPitchError()
    {
        try
        {
            MidiUtil.midiNoteToPitch(-1);
            fail("Expected an exception");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(e.getMessage().contains("-1"));
        }

        try
        {
            MidiUtil.midiNoteToPitch(128);
            fail("Expected an exception");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(e.getMessage().contains("128"));
        }
    }

    @Test
    public void testMidiNoteOctave()
    {
        assertEquals(-1, MidiUtil.midiNoteOctave(0));
        assertEquals(4, MidiUtil.midiNoteOctave(60));
        assertEquals(3, MidiUtil.midiNoteOctave(59));
        assertEquals(8, MidiUtil.midiNoteOctave(108));
    }

    @Test
    public void testMidiNoteOctaveError()
    {
        try
        {
            MidiUtil.midiNoteOctave(-1);
            fail("Expected an exception");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(e.getMessage().contains("-1"));
        }

        try
        {
            MidiUtil.midiNoteOctave(128);
            fail("Expected an exception");
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(e.getMessage().contains("128"));
        }
    }
}
