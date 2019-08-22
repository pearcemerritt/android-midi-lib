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

import java.io.IOException;
import java.io.OutputStream;

import com.leff.midi.util.VariableLengthInt;

/**
 * Full Disclosure, SysEx events may not be properly handled by this library.
 * 
 */
public class SystemExclusiveEvent extends MidiEvent
{
    private int mType;
    private VariableLengthInt mLength;
    private byte[] mData;

    public SystemExclusiveEvent(int type, long tick, byte[] data)
    {
        this(type, tick, 0, data);
    }

    public SystemExclusiveEvent(int type, long tick, long delta, byte[] data)
    {
        super(tick, delta);

        mType = type & 0xFF;
        if(mType != 0xF0 && mType != 0xF7)
        {
            mType = 0xF0;
        }

        mLength = new VariableLengthInt(data.length);
        mData = data;
    }

    public byte[] getData()
    {
        return mData;
    }

    public void setData(byte[] data)
    {
        mLength.setValue(data.length);
        mData = data;
    }

    @Override
    public boolean requiresStatusByte(MidiEvent prevEvent)
    {
        return true;
    }

    @Override
    public void writeToFile(OutputStream out, boolean writeType) throws IOException
    {
        super.writeToFile(out, writeType);

        out.write(mType);
        out.write(mLength.getBytes());
        out.write(mData);
    }

    @Override
    public int compareTo(MidiEvent other)
    {
        if(super.compareTo(other) != 0)
        {
            return super.compareTo(other);
        }

        // Makes organizational sense to put SystemExclusiveEvents before
        // events like ChannelEvents. Doesn't matter if it's before or after
        // MetaEvents.
        if(!(other instanceof SystemExclusiveEvent))
        {
            return -1;
        }
        return 0;
    }

    @Override
    protected int getEventSize()
    {
        return 1 + mLength.getByteCount() + mData.length;
    }

}
