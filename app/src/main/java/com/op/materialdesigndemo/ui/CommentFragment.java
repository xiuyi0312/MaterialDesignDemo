package com.op.materialdesigndemo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.op.materialdesigndemo.R;
import com.op.materialdesigndemo.databinding.DialogCommentBinding;
import com.op.materialdesigndemo.entity.UserBehavior;
import com.op.materialdesigndemo.vm.NewsViewModel;

public class CommentFragment extends DialogFragment {
    private DialogCommentBinding binding;
    private int storyId;
    private NewsViewModel viewModel;

    public static CommentFragment getInstance(int storyId) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("story_id", storyId);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new NewsViewModel(getActivity().getApplication());
        setStyle(STYLE_NORMAL, R.style.CommentDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_comment, container, false);
        initViewAndListeners();
        initData();
        return binding.getRoot();
    }

    private void initViewAndListeners() {
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = binding.content.getText().toString();
                viewModel.updateUserBehaviorWithComment(
                        UserBehavior.OP_COMMENT, storyId, commentContent);
                Toast.makeText(getContext(), "Comment successfully!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void initData() {
        storyId = getArguments().getInt("story_id");
    }

}
