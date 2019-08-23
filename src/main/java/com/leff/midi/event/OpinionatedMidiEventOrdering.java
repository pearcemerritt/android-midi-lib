package com.leff.midi.event;

import java.util.HashMap;
import java.util.Map;

import com.leff.midi.event.meta.CopyrightNotice;
import com.leff.midi.event.meta.CuePoint;
import com.leff.midi.event.meta.EndOfTrack;
import com.leff.midi.event.meta.GenericMetaEvent;
import com.leff.midi.event.meta.InstrumentName;
import com.leff.midi.event.meta.KeySignature;
import com.leff.midi.event.meta.Lyrics;
import com.leff.midi.event.meta.Marker;
import com.leff.midi.event.meta.MidiChannelPrefix;
import com.leff.midi.event.meta.SequenceNumber;
import com.leff.midi.event.meta.SequencerSpecificEvent;
import com.leff.midi.event.meta.SmpteOffset;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.Text;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.event.meta.TrackName;

public class OpinionatedMidiEventOrdering
{
    protected static Map<Class<? extends MidiEvent>, Integer> map = 
            new HashMap<Class<? extends MidiEvent>, Integer>();
    
    static
    {
        // System messages first
        map.put(SystemExclusiveEvent.class, 0);
        
        // Finish notes in progress before doing anything else 
        map.put(NoteOff.class, 1);
        
        // Update events in progress
        map.put(PitchBend.class, 2);
        map.put(ChannelAftertouch.class, 3);
        map.put(NoteAftertouch.class, 4);
        
        // Now that completed notes have been turned off and
        // in-progress notes have been addressed, we can start to
        // make changes to settings and meta things that will
        // affect new notes going forward.
        
        // Text Meta Events
        map.put(TrackName.class, 5);
        map.put(CopyrightNotice.class, 6);
        map.put(InstrumentName.class, 7);
        map.put(CuePoint.class, 8);
        map.put(Marker.class, 9);
        map.put(Lyrics.class, 10);
        map.put(Text.class, 11);
        
        // Other Meta Events
        map.put(SmpteOffset.class, 12);
        map.put(MidiChannelPrefix.class, 13);
        map.put(KeySignature.class, 14);
        map.put(TimeSignature.class, 15);
        map.put(Tempo.class, 16);
        map.put(SequenceNumber.class, 17);
        map.put(SequencerSpecificEvent.class, 18);
        map.put(GenericMetaEvent.class, 19);
        
        // Channel Events
        map.put(ProgramChange.class, 20);
        map.put(Controller.class, 21);
        map.put(ChannelEvent.class, 22);
        map.put(NoteOn.class, 23);
        
        map.put(EndOfTrack.class, Integer.MAX_VALUE);
    }

    public static int get(MidiEvent event)
    {
        return map.get(event.getClass());
    }
}
