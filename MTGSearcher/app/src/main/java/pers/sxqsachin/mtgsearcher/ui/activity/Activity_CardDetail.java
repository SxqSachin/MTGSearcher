package pers.sxqsachin.mtgsearcher.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import java.util.ArrayList;

import pers.sxqsachin.mtgsearcher.R;
import pers.sxqsachin.mtgsearcher.ThemeManager;
import pers.sxqsachin.mtgsearcher.databinding.ActivityCardDetailBinding;
import pers.sxqsachin.mtgsearcher.databinding.PopupTextOnlyBinding;
import pers.sxqsachin.mtgsearcher.listener.OnCompleteListener;
import pers.sxqsachin.mtgsearcher.struct.StringPair;
import pers.sxqsachin.mtgsearcher.struct.mtg.AllEdition;
import pers.sxqsachin.mtgsearcher.struct.mtg.CardPriceParser;
import pers.sxqsachin.mtgsearcher.struct.mtg.SingleCardParser;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCard;
import pers.sxqsachin.mtgsearcher.struct.mtg.card.MTGCardPrice;
import pers.sxqsachin.mtgsearcher.struct.mtg.condition.EditionCondition;
import pers.sxqsachin.mtgsearcher.ui.AbstractActivity;
import pers.sxqsachin.mtgsearcher.ui.adapter.RecyclerAdapter_Printing_Edition;
import pers.sxqsachin.mtgsearcher.ui.view.URLImageView;
import pers.sxqsachin.mtgsearcher.util.StatusBarUtil;

/**
 *
 * Activity_CardDetail
 *
 * Created by songxinqi-sachin on 16-7-8.
 */
public class Activity_CardDetail extends AbstractActivity implements SwipeRefreshLayout.OnRefreshListener {

    ActivityCardDetailBinding   mBinding;
    MTGCard                     mData;

    public static Intent getIntent(Context context, String cardUrl) {
        Intent intent = new Intent();
        intent.setClass(context, Activity_CardDetail.class);
        intent.putExtra("cardUrl", cardUrl);
        return intent;
    }

    public static Intent getIntent(Context context, MTGCard card) {
        Intent intent = new Intent();
        intent.setClass(context, Activity_CardDetail.class);
        intent.putExtra("card", card);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_card_detail);
        getCardFromIntent();

        StatusBarUtil.trick(this);

        setSupportActionBar(mBinding.tbCardDetail);
        mBinding.tbCardDetail.setNavigationIcon(R.drawable.ic_arrow_back);
        mBinding.tbCardDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.srlCardDetail.setOnRefreshListener(this);
        mBinding.srlCardDetail.setColorSchemeColors(ThemeManager.getColorPrimary(this), ThemeManager.getColorPrimaryDark(this), ThemeManager.getColorAccent(this));
    }

    protected void getCardFromIntent() {
        mBinding.srlCardDetail.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.srlCardDetail.setRefreshing(true);
            }
        }, 10);
        Intent intent = getIntent();

        MTGCard card = intent.getParcelableExtra("card");
        if (null == card) {
            String cardUrl = intent.getStringExtra("cardUrl");
            if (cardUrl.isEmpty()) {
                finish();
            } else {
                SingleCardParser cardParser = new SingleCardParser();
                cardParser.parse(cardUrl, new OnCompleteListener<MTGCard>() {
                    @Override
                    public void onComplete(final MTGCard obj) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onParseCardComplete(obj);
                            }
                        });
                    }
                });
            }
        } else {
            onParseCardComplete(card);
        }
    }

    protected void onParseCardComplete(MTGCard card) {
        mBinding.srlCardDetail.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.srlCardDetail.setRefreshing(false);
            }
        }, 10);

        mData = card;
        mBinding.setCard(mData);

        mBinding.partCardDetail1.ivCardImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.partCardDetail1.ivCardImg.setImage(mData.getImgUrl());
            }
        }, 250);

        mBinding.partCardDetail1.ivCardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URLImageView imageView = (URLImageView) v;
                if (imageView.isLoadComplete()) {
                    Intent intent = Activity_ImageView.getIntent(Activity_CardDetail.this, mData.getImgUrl());
                    Activity_CardDetail.this.startActivity(intent);
                }
            }
        });

        hideView(mBinding.partCardDetail1.tvCardAllPrintings, mData.getPrintings().size() == 1);
        hideView(mBinding.partCardDetail1.tvCardAllEditions,  mData.getEditions().size() == 1);
        hideView(mBinding.partCardDetail1.tvCardOtherPartWithColon, !mData.hasOtherPart());
        hideView(mBinding.partCardDetail1.tvCardOtherPart, !mData.hasOtherPart());
        hideView(mBinding.partCardDetail1.tvCardAllLanguages, mData.getLanguages().size() == 1);
        hideView(mBinding.partCardDetail1.tvCardDescription, mData.getDescription().isEmpty());
        hideView(mBinding.partCardDetail1.tvCardBgDescription, mData.getBgDescription().isEmpty());
        hideView(mBinding.partCardDetail2.tvCardNote, mData.getNotes().isEmpty());
        hideView(mBinding.partCardDetail2.tvCardNote, mData.getNotes().isEmpty());

        mBinding.partCardDetail1.tvCardOtherPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntent(Activity_CardDetail.this, mData.getOtherPartUrl()));
            }
        });

        if (!AllEdition.getAbbFromEditionName(mData.getCurrentEditionName()).isEmpty()) {
            mBinding.partCardDetail1.tvCardEdition.setTextColor(ThemeManager.getColorPrimary(this));
            mBinding.partCardDetail1.tvCardEdition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = Activity_SearchResult.getIntent(Activity_CardDetail.this, new EditionCondition(mData.getCurrentEditionName()), mData.getCurrentEditionName());
//                    Intent intent = new Intent(Activity_CardDetail.this, Activity_SearchResult.class);
//                    String keyword = new EditionCondition(mData.getCurrentEditionName()).condition();
//                    intent.putExtra("search_keyword", keyword);
//                    intent.putExtra("search_keyword_show", mData.getCurrentEditionName());
                    startActivity(intent);
                }
            });
        }

        mBinding.partCardDetail1.tvCardAllEditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopup(R.layout.popup_text_only, v, 0, 0, Gravity.TOP | Gravity.START, mData.getEditions());
            }
        });
        mBinding.partCardDetail1.tvCardAllPrintings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopup(R.layout.popup_text_only, v, 0, 0, Gravity.CENTER, mData.getPrintings());
            }
        });
        mBinding.partCardDetail1.tvCardAllLanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopup(R.layout.popup_text_only, v, 0, 0, Gravity.CENTER, mData.getLanguages());
            }
        });

        CardPriceParser cardPriceParser = new CardPriceParser();
        cardPriceParser.parse(mData, new OnCompleteListener<MTGCardPrice>() {
            @Override
            public void onComplete(MTGCardPrice obj) {
                mData.setCardPrice(obj);
            }
        });
    }

    protected void  createPopup(int resId, View anchor, int xOffset, int yOffset, int gravity, ArrayList<StringPair> data) {
        PopupTextOnlyBinding binding = DataBindingUtil.inflate(getLayoutInflater(), resId, null, true);

        PopupWindow popupWindow = new PopupWindow(binding.getRoot(), -2, -2, true);
        binding.rvPe.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPe.setItemAnimator(new DefaultItemAnimator());
        binding.rvPe.setAdapter(new RecyclerAdapter_Printing_Edition(this, popupWindow, data));
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(anchor, xOffset, yOffset, gravity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBinding.partCardDetail1.ivCardImg.recycle();
    }

    @Override
    public void onRefresh() {
        onParseCardComplete(mData);
    }
}
