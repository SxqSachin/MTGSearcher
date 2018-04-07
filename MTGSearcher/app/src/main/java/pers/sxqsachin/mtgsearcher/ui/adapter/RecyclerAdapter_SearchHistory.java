package pers.sxqsachin.mtgsearcher.ui.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.ArrayList;

import pers.sxqsachin.mtgsearcher.ThemeManager;
import pers.sxqsachin.mtgsearcher.databinding.ViewPopupHistoryStringBinding;
import pers.sxqsachin.mtgsearcher.databinding.ViewPopupPeStringBinding;
import pers.sxqsachin.mtgsearcher.struct.StringPair;
import pers.sxqsachin.mtgsearcher.ui.activity.Activity_CardDetail;
import pers.sxqsachin.mtgsearcher.ui.activity.Activity_Search;

/**
 *
 * RecyclerAdapter_Printing_Edition
 *
 * Created by songxinqi-sachin on 16-7-15.
 */
public class RecyclerAdapter_SearchHistory extends RecyclerView.Adapter<RecyclerAdapter_SearchHistory.Holder> {

    Activity_Search     mActivity;
    ArrayList<StringPair>   mData;
    PopupWindow mPopup;

    public RecyclerAdapter_SearchHistory(@NonNull Activity_Search activity, PopupWindow popup, ArrayList<StringPair> data) {
        mActivity = activity;

        mData = new ArrayList<>(data);
        mPopup = popup;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewPopupHistoryStringBinding binding = ViewPopupHistoryStringBinding.inflate(mActivity.getLayoutInflater());

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final StringPair history = mData.get(position);
        holder.bindTo(history);

        if (position == 0) {
            holder.mBinding.vDivideLine.getLayoutParams().height = 0;
        }

        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.dismiss();
                mActivity.search(history.second, history.first, true);
            }
        });
    }

    @Override
    public void onViewRecycled(Holder holder) {
        super.onViewRecycled(holder);

        holder.recycle();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ViewPopupHistoryStringBinding mBinding;

        public Holder(ViewPopupHistoryStringBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public void bindTo(StringPair history) {
            mBinding.setHistory(history);
            mBinding.executePendingBindings();
        }

        public void recycle() {
            mBinding.vDivideLine.getLayoutParams().height = ThemeManager.dp2px(1);
            mBinding.tvStr.setTypeface(Typeface.DEFAULT);
        }
    }
}
