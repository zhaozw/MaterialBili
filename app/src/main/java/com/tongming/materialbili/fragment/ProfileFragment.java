package com.tongming.materialbili.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.leakcanary.RefWatcher;
import com.tongming.materialbili.R;
import com.tongming.materialbili.activity.VideoPlayActivity;
import com.tongming.materialbili.base.BaseApplication;
import com.tongming.materialbili.base.BaseFragment;
import com.tongming.materialbili.model.AidVideo;
import com.tongming.materialbili.utils.LogUtil;
import com.tongming.materialbili.view.GlideGircleTransform;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tongming on 2016/3/20.
 */
public class ProfileFragment extends BaseFragment {

    private View view;
    private Bundle bundle;
    private TextView tv_title;
    private TextView tv_play;
    private TextView tv_comment;
    private TextView tv_desc;
    private TextView tv_coins;
    private TextView tv_collect;
    private TextView tv_author;
    private CircleImageView iv_author;
    private String aid;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    Bitmap bitmap = (Bitmap) msg.obj;
                    iv_author.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pager_profile;
    }

    @Override
    protected void afterCreate(Bundle saveInstanceState) {
        bundle = ((VideoPlayActivity)getActivity()).getInfo();
        initView();
        initData();
        LogUtil.i("pro","开始执行");
    }

    @Override
    protected void lazyLoad() {

    }

    private void initView(){
        iv_author = (CircleImageView) view.findViewById(R.id.author_face);
        tv_title = (TextView) view.findViewById(R.id.page_title);
        tv_play = (TextView) view.findViewById(R.id.page_play);
        tv_comment = (TextView) view.findViewById(R.id.page_danmaku);
        tv_desc = (TextView) view.findViewById(R.id.page_desc);
        tv_coins = (TextView) view.findViewById(R.id.coins);
        tv_collect = (TextView) view.findViewById(R.id.tv_collect);
        tv_author = (TextView) view.findViewById(R.id.tv_author);
    }

    private void initData(){
        aid = bundle.getString("aid");
        AidVideo video = (AidVideo) bundle.getParcelable("video");
        tv_title.setText(video.getTitle());
        tv_play.setText(video.getPlay());
        tv_comment.setText(video.getVideo_review());
        tv_desc.setText(video.getDescription());
        tv_coins.setText(video.getCoins());
        tv_collect.setText(video.getFavorites());
        tv_author.setText(video.getAuthor());
        Glide.with(this).load(video.getFace())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideGircleTransform(getActivity()))
                .into(iv_author);
        //LoadNetImage.getVd(video.getFace(),handler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
