/*******************************************************************************
 *
 *                          Messenger Android Frontend
 *                           (C) 2017 Björn Petersen
 *                    Contact: r10s@b44t.com, http://b44t.com
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see http://www.gnu.org/licenses/ .
 *
 *******************************************************************************
 *
 * File:    MrPoortext.java
 * Purpose: Wrap around mrpoortext_t
 *
 ******************************************************************************/


package com.b44t.messenger;


public class MrPoortext {

    public final static int      MR_TEXT1_NORMAL            = 0;
    public final static int      MR_TEXT1_DRAFT             = 1;
    public final static int      MR_TEXT1_USERNAME          = 2;
    public final static int      MR_TEXT1_SELF              = 3;

    public MrPoortext(long hPoortext) {
        m_hPoortext = hPoortext;
    }

    @Override protected void finalize() throws Throwable {
        super.finalize();
        MrPoortextUnref(m_hPoortext);
    }

    public String getText1() {
        return MrPoortextGetText1(m_hPoortext);
    }

    public int getText1Meaning() {
        return MrPoortextGetText1Meaning(m_hPoortext);
    }

    public String getText2() {
        return MrPoortextGetText2(m_hPoortext);
    }

    public long getTimestamp() {
        return MrPoortextGetTimestamp(m_hPoortext);
    }

    public int getState() {
        return MrPoortextGetState(m_hPoortext);
    }

    private long                  m_hPoortext;
    private native static void    MrPoortextUnref            (long hPoortext);
    private native static String  MrPoortextGetText1         (long hPoortext);
    private native static int     MrPoortextGetText1Meaning  (long hPoortext);
    private native static String  MrPoortextGetText2         (long hPoortext);
    private native static long    MrPoortextGetTimestamp     (long hPoortext);
    private native static int     MrPoortextGetState         (long hPoortext);
}
