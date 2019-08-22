package com.leff.midi.event.meta;

import org.junit.Test;

import com.leff.midi.util.MidiUtil;

import static org.junit.Assert.*;

public class TestTempo
{
    @Test
    public void testToString()
    {
        assertEquals("0 (0): Tempo 120.0 bpm", 
                new Tempo(0, 0, MidiUtil.bpmToMpqn(120.0f)).toString());
    }
}
