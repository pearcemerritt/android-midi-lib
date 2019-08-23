//////////////////////////////////////////////////////////////////////////////
//	Copyright 2011 Alex Leffelman
//	
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//	
//	http://www.apache.org/licenses/LICENSE-2.0
//	
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.
//////////////////////////////////////////////////////////////////////////////

package com.leff.midi.event;

import com.leff.midi.util.MidiUtil;

public class NoteOn extends ChannelEvent
{
    public NoteOn(long tick, int channel, int note, int velocity)
    {
        super(tick, ChannelEvent.NOTE_ON, channel, note, velocity);
    }

    public NoteOn(long tick, long delta, int channel, int note, int velocity)
    {
        super(tick, delta, ChannelEvent.NOTE_ON, channel, note, velocity);
    }

    public int getNoteValue()
    {
        return mValue1;
    }

    public int getVelocity()
    {
        return mValue2;
    }

    public void setNoteValue(int p)
    {
        mValue1 = p;
    }

    public void setVelocity(int v)
    {
        mValue2 = v;
    }

    @Override
    protected int compareTo(ChannelEvent other)
    {
        // Should only happen if coder is confused
        if (other.getClass() != NoteOn.class)
        {
            throw new IllegalStateException("Calling protected method incorrectly. " +
                    "NoteOn.compareTo(ChannelEvent) should only be called when it is " +
                    "established that the other class is also a NoteOn");
        }

        if(mChannel != other.mChannel)
        {
            return Integer.compare(mChannel, other.mChannel);
        }

        // If neither event is acting as a note-off event, then we
        // can determine equality based off pitch. We ignore velocity
        // and don't let two events with the same pitch (and time/channel
        // etc.) get played twice at the same time.
        if (mValue2 != 0 && other.mValue2 != 0)
        {
            return Integer.compare(mValue1, other.mValue1);
        }
        // If both event are acting as a note-off events, then we
        // can determine equality based off pitch.
        else if (mValue2 == 0 && other.mValue2 == 0)
        {
            return Integer.compare(mValue1, other.mValue1);
        }
        // Otherwise, one of the events is acting as a note-off event, so we
        // want it to be first.
        else
        {
            return Integer.compare(mValue2, other.mValue2);
        }
    }

    @Override
    public String toString()
    {
        String pitchString = String.format("%s%d", 
                MidiUtil.midiNoteToPitch(mValue1), MidiUtil.midiNoteOctave(mValue1));
        String velocityString = mValue2 != 0 ? Integer.toString(mValue2) : "0 (note-off)";
        return String.format("%s %s %s", super.toString(), pitchString, velocityString);
    }
}
