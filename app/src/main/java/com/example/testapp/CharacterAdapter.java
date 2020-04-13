package com.example.testapp;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testapp.model.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface LoadingCallback {
        void onEndReached();
    }

    private static int TYPE_LOADING = 0;
    private static int TYPE_CHARACTER = 1;

    private List<Character> characterList;
    private boolean doneLoading = false;
    private LoadingCallback loadingCallback;

    public CharacterAdapter(LoadingCallback loadingCallback) {
        characterList = new ArrayList<>();
        this.loadingCallback = loadingCallback;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_row, parent, false));
        }
        return new CharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.character_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterViewHolder) {
            ((CharacterViewHolder) holder).bind(characterList.get(position));
        } else {
            loadingCallback.onEndReached();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == characterList.size()) {
            return TYPE_LOADING;
        }
        return TYPE_CHARACTER;
    }

    @Override
    public int getItemCount() {
        if (characterList.size() == 0) {
            return 0;
        }
        if (doneLoading) {
            return characterList.size();
        } else {
            return characterList.size() + 1;
        }
    }

    public void updateData(List<Character> characters) {
        doneLoading = false;
        characterList.clear();
        characterList.addAll(characters);
        notifyDataSetChanged();
    }

    public void setDoneLoading() {
        doneLoading = true;
        notifyItemRemoved(characterList.size());
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
        }

        void bind(Character character) {
            textView.setText(character.getName());
            if (!TextUtils.isEmpty(character.getImage())) {
                Glide.with(imageView.getContext()).load(character.getImage()).into(imageView);
            }
        }
    }
}
