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

package com.leff.midi.event.meta;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.leff.midi.util.VariableLengthInt;

public class KeySignature extends MetaEvent
{
    public static final int SCALE_MAJOR = 0;
    public static final int SCALE_MINOR = 1;

    private int mKey;
    private int mScale;

    public KeySignature(long tick, long delta, int key, int scale)
    {
        super(tick, delta, MetaEvent.KEY_SIGNATURE, new VariableLengthInt(2));

        this.setKey(key);
        mScale = scale;
    }

    public void setKey(int key)
    {
        mKey = (byte) key;

        if(mKey < -7)
            mKey = -7;
        else if(mKey > 7)
            mKey = 7;
    }

    public int getKey()
    {
        return mKey;
    }

    public void setScale(int scale)
    {
        mScale = scale;
    }

    public int getScale()
    {
        return mScale;
    }

    @Override
    protected int getEventSize()
    {
        return 5;
    }

    @Override
    public void writeToFile(OutputStream out) throws IOException
    {
        super.writeToFile(out);

        out.write(2);
        out.write(mKey);
        out.write(mScale);
    }

    @Override
    public String toString() {
        String key;
        if (mScale == 0)
        {
            key = MAJOR_CIRCLE_OF_FIFTHS.get(mKey) + " major";
        }
        else
        {
            key = MINOR_CIRCLE_OF_FIFTHS.get(mKey) + " minor";
        }
        return String.format("%s %s", super.toString(), key);
    }

    public static final Map<Integer, String> MAJOR_CIRCLE_OF_FIFTHS = new HashMap<Integer, String>();
    public static final Map<Integer, String> MINOR_CIRCLE_OF_FIFTHS = new HashMap<Integer, String>();

    static {
        MAJOR_CIRCLE_OF_FIFTHS.put(-7, "Cb");
        MAJOR_CIRCLE_OF_FIFTHS.put(-6, "Gb");
        MAJOR_CIRCLE_OF_FIFTHS.put(-5, "Db");
        MAJOR_CIRCLE_OF_FIFTHS.put(-4, "Ab");
        MAJOR_CIRCLE_OF_FIFTHS.put(-3, "Eb");
        MAJOR_CIRCLE_OF_FIFTHS.put(-2, "Bb");
        MAJOR_CIRCLE_OF_FIFTHS.put(-1, "F");
        MAJOR_CIRCLE_OF_FIFTHS.put(0, "C");
        MAJOR_CIRCLE_OF_FIFTHS.put(1, "G");
        MAJOR_CIRCLE_OF_FIFTHS.put(2, "D");
        MAJOR_CIRCLE_OF_FIFTHS.put(3, "A");
        MAJOR_CIRCLE_OF_FIFTHS.put(4, "E");
        MAJOR_CIRCLE_OF_FIFTHS.put(5, "B");
        MAJOR_CIRCLE_OF_FIFTHS.put(6, "F#");
        MAJOR_CIRCLE_OF_FIFTHS.put(7, "C#");

        MINOR_CIRCLE_OF_FIFTHS.put(-7, "Ab");
        MINOR_CIRCLE_OF_FIFTHS.put(-6, "Eb");
        MINOR_CIRCLE_OF_FIFTHS.put(-5, "Bb");
        MINOR_CIRCLE_OF_FIFTHS.put(-4, "F");
        MINOR_CIRCLE_OF_FIFTHS.put(-3, "C");
        MINOR_CIRCLE_OF_FIFTHS.put(-2, "G");
        MINOR_CIRCLE_OF_FIFTHS.put(-1, "D");
        MINOR_CIRCLE_OF_FIFTHS.put(0, "A");
        MINOR_CIRCLE_OF_FIFTHS.put(1, "E");
        MINOR_CIRCLE_OF_FIFTHS.put(2, "B");
        MINOR_CIRCLE_OF_FIFTHS.put(3, "F#");
        MINOR_CIRCLE_OF_FIFTHS.put(4, "C#");
        MINOR_CIRCLE_OF_FIFTHS.put(5, "G#");
        MINOR_CIRCLE_OF_FIFTHS.put(6, "D#");
        MINOR_CIRCLE_OF_FIFTHS.put(7, "A#");
    }

    public static MetaEvent parseKeySignature(long tick, long delta, MetaEventData info)
    {
        if(info.length.getValue() != 2)
        {
            return new GenericMetaEvent(tick, delta, info);
        }

        int key = info.data[0];
        int scale = info.data[1];

        return new KeySignature(tick, delta, key, scale);
    }
}
