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

package com.leff.midi.util;

public class MidiUtil
{
    /**
     * MIDI Unit Conversions
     */
    public static long ticksToMs(long ticks, int mpqn, int resolution)
    {
        return ((ticks * mpqn) / resolution) / 1000;
    }

    public static long ticksToMs(long ticks, float bpm, int resolution)
    {
        return ticksToMs(ticks, bpmToMpqn(bpm), resolution);
    }

    public static double msToTicks(long ms, int mpqn, int ppq)
    {
        return ((ms * 1000.0) * ppq) / mpqn;
    }

    public static double msToTicks(long ms, float bpm, int ppq)
    {
        return msToTicks(ms, bpmToMpqn(bpm), ppq);
    }

    public static int bpmToMpqn(float bpm)
    {
        // let b be a variable number of beats per minute
        // 60,000,000 µsec/min ÷ b beat/min   =
        // 60,000,000 µsec/min • 1/b min/beat =
        //
        //   the "mins" cancel out
        //
        // 60,000,000 µsec     ÷ b beat       = ? µsec/beat
        return (int) Math.round(60_000_000.0 / bpm);
    }

    public static float mpqnToBpm(int mpqn)
    {
        // let m be a variable number of micro-seconds per beat
        // 60,000,000 µsec/min ÷ m µsec/beat   =
        // 60,000,000 µsec/min • 1/m beat/µsec =
        //
        //   the "µsecs" cancel out
        //
        // 60,000,000 beat      • m min        = ? beat/min
        return 60_000_000.0f / mpqn;
    }

    public static int midiNoteOctave(int note) {
        if (note < 0 || note > 127)
            throw new IllegalArgumentException(String.format("Invalid midi note (%d)", note));

        return (note / 12) - 1;
    }

    public static final String[] CHROMATIC_PITCHES = new String[] {
            "C", "Db/C#", "D", "Eb/D#", "E", "F", "Gb/F#", "G", "Ab/G#", "A", "Bb/A#", "B" };

    public static String midiNoteToPitch(int note)
    {
        if (note < 0 || note > 127)
            throw new IllegalArgumentException(String.format("Invalid midi note (%d)", note));

        return CHROMATIC_PITCHES[note % 12];
    }

    /**
     * Utility methods for working with bytes and byte buffers
     */
    public static int bytesToInt(byte[] buff, int off, int len)
    {
        int num = 0;

        int shift = 0;
        for(int i = off + len - 1; i >= off; i--)
        {

            num += (buff[i] & 0xFF) << shift;
            shift += 8;
        }

        return num;
    }

    public static byte[] intToBytes(int val, int byteCount)
    {
        byte[] buffer = new byte[byteCount];

        int[] ints = new int[byteCount];

        for(int i = 0; i < byteCount; i++)
        {
            ints[i] = val & 0xFF;
            buffer[byteCount - i - 1] = (byte) ints[i];

            val = val >> 8;

            if(val == 0)
            {
                break;
            }
        }

        return buffer;
    }

    public static boolean bytesEqual(byte[] buf1, byte[] buf2, int off, int len)
    {
        for(int i = off; i < off + len; i++)
        {
            if(i >= buf1.length || i >= buf2.length)
            {
                return false;
            }
            if(buf1[i] != buf2[i])
            {
                return false;
            }
        }
        return true;
    }

    public static byte[] extractBytes(byte[] buffer, int off, int len)
    {
        byte[] ret = new byte[len];

        for(int i = 0; i < len; i++)
        {
            ret[i] = buffer[off + i];
        }

        return ret;
    }

    private static final String HEX = "0123456789ABCDEF";

    public static String byteToHex(byte b)
    {
        int high = (b & 0xF0) >> 4;
        int low = (b & 0x0F);

        return "" + HEX.charAt(high) + HEX.charAt(low);
    }

    public static String bytesToHex(byte[] b)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < b.length; i++)
        {
            sb.append(byteToHex(b[i])).append(" ");
        }
        return sb.toString();
    }
}
