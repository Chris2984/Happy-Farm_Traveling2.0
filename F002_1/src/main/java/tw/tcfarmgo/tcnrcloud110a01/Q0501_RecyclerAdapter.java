package tw.tcfarmgo.tcnrcloud110a01;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Q0501_RecyclerAdapter extends RecyclerView.Adapter<Q0501_RecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Q0501_Post> mData;
    //    -------------------------------------------------------------------
    private OnItemClickListener mOnItemClickListener = null;
    //--------------------------------------------
    public Q0501_RecyclerAdapter(Context context, ArrayList<Q0501_Post> data) {
        this.mContext = context;
        this.mData = data;
    }
    //    -------------------------------------------------------------------
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //-------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.q0501_cell_post, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.img = (ImageView) view.findViewById(R.id.q0501_img);//correspond to layout
        holder.Name = (TextView) view.findViewById(R.id.q0501_t201);
        holder.Content = (TextView) view.findViewById(R.id.Content);
        holder.Add = (TextView) view.findViewById(R.id.q0501_t205);
        holder.Zipcode = (TextView) view.findViewById(R.id.q0501_t204);
        holder.TEL = (TextView) view.findViewById(R.id.q0501_t202);
        //????????????View??????????????????
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Q0501_Post post = mData.get(position);
        holder.Name.setText(post.FarmNm_CH);
        //holder.Website.setText("??????:"+post.Website);
//        if (post.Parkinginfo_Px.length()>0 && post.Parkinginfo_Py.length()>0){ //???????????????
//            holder.Parkinginfo_Px.setText("?????????:"+post.Parkinginfo_Px+",");
//            holder.Parkinginfo_Py.setText(post.Parkinginfo_Py);
//        }else{//????????????
//            //holder.Website.setText("[???????????????]");
//            holder.Parkinginfo_Px.setText("????????????????????????");
//            holder.Parkinginfo_Py.setText("");
//        }
//        holder.Px.setText("??????:"+post.Longitude+",");
//        holder.Py.setText("??????:"+post.Latitude);
        holder.TEL.setText("Tel:"+post.TEL);
        holder.Add.setText(post.Address_CH);
        holder.Content.setText(post.Feature_CH);

        if (post.PCode.length()>0){ //?????????????????????
            holder.Zipcode.setText("["+post.PCode+"]");
        }else{//?????????????????????
            holder.Zipcode.setText("[000]");
        }
//==//        ????????????????????????????????????,????????????????????????????????????????????????
////        String ans_Url = post.posterThumbnailUrl;
////        if (post.posterThumbnailUrl.getBytes().length == post.posterThumbnailUrl.length() ||
////                post.posterThumbnailUrl.getBytes().length > 100) {
////            ans_Url = post.posterThumbnailUrl;//??????????????????????????????
////        } else {
//////    ans_Url = utf8Togb2312(post.posterThumbnailUrl);
//////           ans_Url = utf8Togb2312(post.posterThumbnailUrl).replace("http://", "https://");
////        }
////        Glide.with(mContext)
////                .load(ans_Url)
////                .into(holder.img);
////----------------------------------------
//        if (post.Website.length() > 0) {
//            holder.Website.setText("" + QQ + ""); // ????????? Website ?????????
//        } else {
//            holder.Website.setText("?????????"); // ???????????? Website ????????? ?????????
//        }
// ==========================================//posterThumbnailUrl?????????Post
        Glide.with(mContext)
                .load(post.Photo)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(100, 75) //????????????????????????
                .transition(withCrossFade())
                .error(
                        Glide.with(mContext)
                                .load("https://tcnr2021a11.000webhostapp.com/post_img/nopic1.jpg")) //??????????????????
                .into(holder.img);

        //???position?????????itemView???Tag?????????????????????????????????
        holder.itemView.setTag(position);
    }

    ////    //    -----------??????????????????????????????????????????????????????-----------
//    public static String utf8Togb2312(String inputstr) {
//        String r_data = "";
//        try {
//            for (int i = 0; i < inputstr.length(); i++) {
//                char ch_word = inputstr.charAt(i);
////            ??????????????????????????????:????????????????????????
//                if (ch_word + "".getBytes().length > 1 && ch_word != ':' && ch_word != '/') {
//                    r_data = r_data + java.net.URLEncoder.encode(ch_word + "", "utf-8");
//                } else {
//                    r_data = r_data + ch_word;
//                }
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } finally {
////            System.out.println(r_data);
//        }
//        return r_data;
//    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //??????????????????getTag????????????position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //======= sub class   ==================
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView Name;
        public TextView Add;
        public TextView Content;
        public TextView Zipcode;
        //public TextView Website;
        //public TextView Px,Py;
        public TextView TEL;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
//-----------------------------------------------
}
