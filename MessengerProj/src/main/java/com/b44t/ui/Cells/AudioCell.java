/*******************************************************************************
 *
 *                          Messenger Android Frontend
 *                        (C) 2013-2016 Nikolai Kudashov
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
 ******************************************************************************/


package com.b44t.ui.Cells;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.b44t.messenger.AndroidUtilities;
import com.b44t.messenger.LocaleController;
import com.b44t.messenger.MediaController;
import com.b44t.messenger.MessageObject;
import com.b44t.messenger.R;
import com.b44t.ui.Components.CheckBoxView;
import com.b44t.ui.Components.LayoutHelper;

public class AudioCell extends FrameLayout {

    private ImageView playButton;
    private TextView titleTextView;
    private TextView authorTextView;
    private TextView timeTextView;
    private CheckBoxView checkBox;

    private MediaController.AudioEntry audioEntry;
    private static Paint paint;

    private AudioCellDelegate delegate;

    public interface AudioCellDelegate {
        void startedPlayingAudio(MessageObject messageObject);
    }

    public AudioCell(Context context) {
        super(context);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(0xffd9d9d9);
            paint.setStrokeWidth(1);
        }

        playButton = new ImageView(context);
        playButton.setScaleType(ImageView.ScaleType.CENTER);
        addView(playButton, LayoutHelper.createFrame(46, 46, (Gravity.START | Gravity.TOP), LocaleController.isRTL ? 0 : 13, 13, LocaleController.isRTL ? 13 : 0, 0));
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioEntry != null) {
                    if (MediaController.getInstance().isMessageOnAir(audioEntry.messageObject) && !MediaController.getInstance().isAudioPaused()) {
                        MediaController.getInstance().stopAudio();
                        playButton.setImageResource(R.drawable.attach_audio_inlist_play);
                    } else {
                        if (MediaController.getInstance().playAudio(audioEntry.messageObject)) {
                            playButton.setImageResource(R.drawable.attach_audio_inlist_pause);
                            if (delegate != null) {
                                delegate.startedPlayingAudio(audioEntry.messageObject);
                            }
                        }
                    }
                }
            }
        });

        titleTextView = new TextView(context);
        titleTextView.setTextColor(0xff212121);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        titleTextView.setLines(1);
        titleTextView.setMaxLines(1);
        titleTextView.setSingleLine(true);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        titleTextView.setGravity(Gravity.START | Gravity.TOP);
        addView(titleTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, LocaleController.isRTL ? 50 : 72, 7, LocaleController.isRTL ? 72 : 50, 0));

        authorTextView = new TextView(context);
        authorTextView.setTextColor(0xff8a8a8a);
        authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        authorTextView.setLines(1);
        authorTextView.setMaxLines(1);
        authorTextView.setSingleLine(true);
        authorTextView.setEllipsize(TextUtils.TruncateAt.END);
        authorTextView.setGravity(Gravity.START | Gravity.TOP);
        addView(authorTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, LocaleController.isRTL ? 50 : 72, 28, LocaleController.isRTL ? 72 : 50, 0));

        timeTextView = new TextView(context);
        timeTextView.setTextColor(0xff8a8a8a);
        timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        timeTextView.setLines(1);
        timeTextView.setMaxLines(1);
        timeTextView.setSingleLine(true);
        timeTextView.setEllipsize(TextUtils.TruncateAt.END);
        timeTextView.setGravity(Gravity.START | Gravity.TOP);
        addView(timeTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, LocaleController.isRTL ? 50 : 72, 44, LocaleController.isRTL ? 72 : 50, 0));

        checkBox = new CheckBoxView(context, R.drawable.round_check2);
        checkBox.setVisibility(VISIBLE);
        checkBox.setColor(0xff29b6f7);
        addView(checkBox, LayoutHelper.createFrame(
                    22, // width
                    22, // height
                    Gravity.START | Gravity.TOP, // gravity
                    LocaleController.isRTL ? 0 : 44, // marginLeft
                    39, // marginTop
                    LocaleController.isRTL ? 44 : 0, // marginRight
                    0)); // marginBottom
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(72), MeasureSpec.EXACTLY));
    }

    public void setAudio(MediaController.AudioEntry entry, boolean checked) {
        audioEntry = entry;

        titleTextView.setText(audioEntry.title);
        authorTextView.setText(audioEntry.author);
        timeTextView.setText(String.format("%d:%02d", audioEntry.duration / 60, audioEntry.duration % 60));
        playButton.setImageResource(MediaController.getInstance().isMessageOnAir(audioEntry.messageObject) && !MediaController.getInstance().isAudioPaused() ? R.drawable.attach_audio_inlist_pause : R.drawable.attach_audio_inlist_play);

        checkBox.setChecked(checked, false);
    }

    public void setChecked(boolean value) {
        checkBox.setChecked(value, true);
    }

    public void setDelegate(AudioCellDelegate audioCellDelegate) {
        delegate = audioCellDelegate;
    }

    public MediaController.AudioEntry getAudioEntry() {
        return audioEntry;
    }
}
