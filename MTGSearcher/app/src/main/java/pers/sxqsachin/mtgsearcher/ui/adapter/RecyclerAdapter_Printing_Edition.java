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
import pers.sxqsachin.mtgsearcher.databinding.ViewPopupPeStringBinding;
import pers.sxqsachin.mtgsearcher.struct.StringPair;
import pers.sxqsachin.mtgsearcher.ui.activity.Activity_CardDetail;

/**
 *
 * RecyclerAdapter_Printing_Edition
 *
 * Created by songxinqi-sachin on 16-7-15.
 */
public class RecyclerAdapter_Printing_Edition extends RecyclerView.Adapter<RecyclerAdapter_Printing_Edition.Holder> {

    Activity    mActivity;
    ArrayList<StringPair>   mData;
    PopupWindow mPopup;

    public RecyclerAdapter_Printing_Edition(@NonNull Activity activity, PopupWindow popup, ArrayList<StringPair> data) {
        mActivity = activity;

        mData = new ArrayList<>(data);
        mPopup = popup;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewPopupPeStringBinding binding = ViewPopupPeStringBinding.inflate(mActivity.getLayoutInflater());

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final StringPair stringPair = mData.get(position);
        holder.bindTo(stringPair);

        if (position == 0) {
            holder.mBinding.vDivideLine.getLayoutParams().height = 0;
        }

        if (holder.mBinding.getPair().first.isEmpty()) {
            holder.mBinding.tvStr.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

        holder.mBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.mBinding.getPair().first.isEmpty()) {
                    return false;
                }

                mPopup.dismiss();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mActivity.startActivity(Activity_CardDetail.getIntent(mActivity, holder.mBinding.getPair().first));
                    }
                }).start();

                return true;
            }
        });

        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mBinding.getPair().first.isEmpty()) {
                    return;
                }

                mPopup.dismiss();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mActivity.startActivity(Activity_CardDetail.getIntent(mActivity, holder.mBinding.getPair().first));
                        mActivity.finish();
                    }
                }).start();
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
        ViewPopupPeStringBinding mBinding;

        public Holder(ViewPopupPeStringBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public void bindTo(StringPair stringPair) {
            mBinding.setPair(stringPair);
            mBinding.executePendingBindings();
        }

        public void recycle() {
            mBinding.vDivideLine.getLayoutParams().height = ThemeManager.dp2px(1);
            mBinding.tvStr.setTypeface(Typeface.DEFAULT);
        }
    }
}
