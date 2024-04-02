package com.example.aula_26_01_2024;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclePosts extends RecyclerView.Adapter<RecyclePosts.ViewHolder> {

    private Context ctx;
    private List<Post> post;

    private IOnSacaFoto listener;

    public RecyclePosts(Context ctx, List<Post> post) {
        this.ctx = ctx;
        this.post = post;
    }

    public void setOnSacaFotoListener(IOnSacaFoto lst) {
        this.listener = lst;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        EditText editid, editmodelo;
        Spinner spincat;
        ImageView imgfoto;
        Button btdel, btupdate;

        public ViewHolder(@NonNull View v) {
            super(v);
            editid = v.findViewById(R.id.edit_posttitle_itempost);
            editmodelo = v.findViewById(R.id.edit_desc_itempost);
            imgfoto = v.findViewById(R.id.img_foto_itempost);
            btdel = v.findViewById(R.id.bt_delete_itempost);
            btupdate = v.findViewById(R.id.bt_update_itempost);
        }
    }

    @NonNull
    @Override
    public RecyclePosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclePosts.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Post currentPost = post.get(position);
        holder.editid.setText(String.valueOf(currentPost.getTittle()));
        holder.editmodelo.setText(currentPost.getDesc());

        if (currentPost.getFoto() != null && currentPost.getFoto().length > 0) {
            holder.imgfoto.setImageBitmap(Post.arraytobitmap(currentPost.getFoto()));
        }

        holder.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.SacaFoto(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return post.size();
    }
}
