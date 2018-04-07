package pers.sxqsachin.mtgsearcher.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Vector;

import pers.sxqsachin.mtgsearcher.R;
import pers.sxqsachin.mtgsearcher.ThemeManager;
import pers.sxqsachin.mtgsearcher.databinding.ActivitySearchResultBinding;
import pers.sxqsachin.mtgsearcher.databinding.ViewCardPreviewBinding;
import pers.sxqsachin.mtgsearcher.listener.NetworkEventListener;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.net.bitmap.BitmapLoader;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.CardCondition;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;
import pers.sxqsachin.mtgsearcher.struct.mtg.search.CardSearchResult;
import pers.sxqsachin.mtgsearcher.struct.mtg.search.SimplyCardSearcher;
import pers.sxqsachin.mtgsearcher.ui.BindableActivity;
import pers.sxqsachin.mtgsearcher.ui.view.URLImageView;
import pers.sxqsachin.mtgsearcher.uidata.UIData_SearchResult;
import pers.sxqsachin.mtgsearcher.util.LogUtil;
import pers.sxqsachin.mtgsearcher.util.StatusBarUtil;

/**
 *
 * Activity_SearchResult
 *
 * Created by songxinqi-sachin on 16-6-30.
 */
public class Activity_SearchResult extends BindableActivity implements OnCompleteListener<CardSearchResult>, SwipeRefreshLayout.OnRefreshListener {

    private final static String     INTENT_CONDITION    =   "search_condition";
    private final static String     INTENT_KEYWORD =   "search_keyword";

    protected ActivitySearchResultBinding mBinding;
    private UIData_SearchResult     mData;

    private SimplyCardSearcher      mCardSearcher;

    private CardSearchResult        mSearchResult;
    private CardCondition           mSearchCondition;

    private boolean                 mLock;

    public static Intent getIntent(Context context, CardCondition cardCondition) {
        return getIntent(context, cardCondition, cardCondition.condition());
    }

    public static Intent getIntent(Context context, CardCondition cardCondition, String title) {
        Intent intent = new Intent();
        intent.setClass(context, Activity_SearchResult.class);
        intent.putExtra(INTENT_CONDITION, cardCondition);
        intent.putExtra(INTENT_KEYWORD, title);

        LogUtil.debugOut(title);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);
        mData = new UIData_SearchResult();
        mBinding.setData(mData);

        StatusBarUtil.trick(this);

        Intent intent = getIntent();
        mSearchCondition = intent.getParcelableExtra(INTENT_CONDITION);
        String keyword = intent.getStringExtra(INTENT_KEYWORD);

        if (keyword != null) {
            mData.setSearchKeyword(keyword);
        } else {
            mData.setSearchKeyword(mSearchCondition.condition());
        }

        setSupportActionBar(mBinding.tbSearchResult);
        mBinding.tbSearchResult.setNavigationIcon(R.drawable.ic_arrow_back);
        mBinding.tbSearchResult.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.srlSearchResult.setOnRefreshListener(this);
        mBinding.srlSearchResult.setColorSchemeColors(ThemeManager.getColorPrimary(this), ThemeManager.getColorPrimaryDark(this), ThemeManager.getColorAccent(this));
        mBinding.srlSearchResult.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 10);

        mBinding.rvCards.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvCards.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        });
        mBinding.rvCards.setAdapter(new RecycleViewAdapter());

        mBinding.rvCards.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                RecycleViewAdapter recycleViewAdapter = (RecycleViewAdapter) recyclerView.getAdapter();

                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
//                if(newState == RecyclerView.SCROLL_STATE_IDLE){
//                }

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                        if (mSearchResult != null) {
                            if (mSearchResult.getMaxPage() == mSearchResult.getCurrentPage()) {
                                showNoMore();
                            } else {
                                if (!mLock) {
                                    search(mSearchCondition);
                                }
                            }
                        }
                    } else {
                        BitmapLoader.cancelTask();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBinding.rvCards.clearOnScrollListeners();
        mBinding.rvCards.destroyDrawingCache();
    }

    @Override
    public void onRefresh() {
        mCardSearcher = null;
        search(mSearchCondition);
    }

    protected void  search(CardCondition condition) {
        mLock = true;
        if (mCardSearcher == null) {
            mCardSearcher = new SimplyCardSearcher();
        }
        mCardSearcher.search(this, new NetworkEventListener() {
            @Override
            public void onSocketTimeOut() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.srlSearchResult.setRefreshing(false);
                        Toast.makeText(Activity_SearchResult.this, "SocketTimeOut, Pleases Refresh", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, condition);
        mBinding.srlSearchResult.setRefreshing(true);
    }

    @Override
    public void onComplete(final CardSearchResult result) {
        mLock = false;
        mSearchResult = result;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBinding.srlSearchResult.setRefreshing(false);
                if (result.isEmpty()) {
                    showNoMore();
                    return;
                }
                if (result.getCurrentPage() == 1) {
                    ((RecycleViewAdapter) mBinding.rvCards.getAdapter()).clearCards();
                }
                ((RecycleViewAdapter) mBinding.rvCards.getAdapter()).addCards(result.getCards());
            }
        });
    }

    protected void  showNoMore() {
        Snackbar snackbar = Snackbar.make(mBinding.clSearchResult, "No more", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static int mHolderNumber = 0;
    class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.AdapterViewHolder> {

        private Vector<MTGCard> mCards;

        public RecycleViewAdapter() {
            mCards = new Vector<>();
        }

        public void addCards(Vector<MTGCard> cards) {
            notifyItemRangeInserted(getItemCount(), cards.size());
            mCards.addAll(cards);
        }

        public void clearCards() {
            notifyItemRangeRemoved(0, getItemCount());
            mCards.clear();
        }

        @Override
        public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewCardPreviewBinding binding = ViewCardPreviewBinding.inflate(getLayoutInflater(), parent, false);

            return new AdapterViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(final AdapterViewHolder holder, int position) {
            final MTGCard card = mCards.get(position);
            card.setCardIndexInView((position + 1) + "/" + mSearchResult.getCount());

            holder.bindTo(card);

            hideView(holder.mBinding.tvCardDescription, card.getDescription().isEmpty());
            hideView(holder.mBinding.tvCardBgDescription, card.getBgDescription().isEmpty());

            holder.getBinding().ivCardImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    URLImageView imageView = (URLImageView) v;
                    if (imageView.isLoadComplete()) {
                        Intent intent = Activity_ImageView.getIntent(Activity_SearchResult.this, card.getImgUrl());
                        Activity_SearchResult.this.startActivity(intent);
                    }
                }
            });

            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(Activity_CardDetail.getIntent(Activity_SearchResult.this, card));
                }
            });
        }

        @Override
        public void onViewAttachedToWindow(AdapterViewHolder holder) {
            super.onViewDetachedFromWindow(holder);

            holder.getBinding().ivCardImg.setImage(holder.getBinding().getCard().getImgUrl());
        }

        @Override
        public void onViewDetachedFromWindow(AdapterViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(AdapterViewHolder holder) {
            super.onViewRecycled(holder);

            holder.recycle();
        }

        @Override
        public boolean onFailedToRecycleView(AdapterViewHolder holder) {
            LogUtil.debugOut("FAILED RECYCLE");
            return super.onFailedToRecycleView(holder);
        }

        protected void hideView(View v, boolean b) {
            if (b) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
                lp.height = 0;
                lp.topMargin = 0;
                v.setLayoutParams(lp);
            }
        }


        @Override
        public int getItemCount() {
            return mCards.size();
        }


        class AdapterViewHolder extends RecyclerView.ViewHolder {

            public int mIndex = 0;

            ViewCardPreviewBinding  mBinding;

            public ViewCardPreviewBinding   getBinding() {
                return mBinding;
            }

            public void bindTo(final MTGCard card) {
                mBinding.setCard(card);
                mBinding.executePendingBindings();
            }

            public AdapterViewHolder(ViewCardPreviewBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
                mIndex = mHolderNumber++;
            }

            public void recycle() {
                mBinding.ivCardImg.recycle();
                mBinding.tvCardBgDescription.getLayoutParams().height = -2;
                ((RelativeLayout.LayoutParams)mBinding.tvCardBgDescription.getLayoutParams()).topMargin = ThemeManager.dp2px(16);
                mBinding.tvCardDescription.getLayoutParams().height = -2;
                ((RelativeLayout.LayoutParams)mBinding.tvCardDescription.getLayoutParams()).topMargin = ThemeManager.dp2px(16);
            }
        }
    }
}

