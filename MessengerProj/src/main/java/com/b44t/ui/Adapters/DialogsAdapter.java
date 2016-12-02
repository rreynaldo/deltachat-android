/*
 * This part of the Delta Chat fronted is based on Telegram which is covered by the following note:
 *
 * This is the source code of Telegram for Android v. 3.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2016.
 */

package com.b44t.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.b44t.messenger.AndroidUtilities;
import com.b44t.messenger.MrChatlist;
import com.b44t.messenger.support.widget.RecyclerView;
import com.b44t.messenger.MrMailbox;
import com.b44t.messenger.TLRPC;
import com.b44t.ui.Cells.DialogCell;


public class DialogsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private int dialogsType;
    private long openedDialogId;

    private class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

    public DialogsAdapter(Context context, int type) {
        mContext = context;
        dialogsType = type;

        MrMailbox.reloadMainChatlist();
    }

    public void setOpenedDialogId(long id) {
        openedDialogId = id;
    }

    @Override
    public int getItemCount() {
        return MrMailbox.m_currChatlist.getCnt();
    }

    public TLRPC.TL_dialog getItem(int i) {
        return MrMailbox.m_currChatlist.get_TLRPC_TL_dialog(i);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof DialogCell) {
            ((DialogCell) holder.itemView).checkCurrentDialogIndex();
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = new DialogCell(mContext);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == 0) {
            DialogCell cell = (DialogCell) viewHolder.itemView;
            cell.useSeparator = (i != getItemCount() - 1);
            TLRPC.TL_dialog dialog = getItem(i);
            if (dialogsType == 0) {
                if (AndroidUtilities.isTablet()) {
                    cell.setDialogSelected(dialog.id == openedDialogId);
                }
            }
            cell.setDialog(dialog, i, dialogsType);
        }
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }
}