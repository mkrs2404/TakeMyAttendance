package com.example.takemyattendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class ProfileDialogFragment extends DialogFragment {

    private Button syncBtn;
    private Button signOutBtn;
    private Bundle bundle;
    private ImageView profileImg;
    private TextView name;
    private TextView email;
    SharedPreferences sharedPreferences;
    boolean isConnected;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View DialogView = inflater.inflate(R.layout.profile_layout, null);

        CheckInternetConnection ck = new CheckInternetConnection(getContext());
        isConnected = ck.isConnected();

        sharedPreferences = builder.getContext().getSharedPreferences("googleData", Context.MODE_PRIVATE);

        syncBtn = DialogView.findViewById(R.id.syncBtn);
        signOutBtn = DialogView.findViewById(R.id.signOutBtn);
        profileImg = DialogView.findViewById(R.id.profileDialogImage);
        name = DialogView.findViewById(R.id.profileDialogName);
        email = DialogView.findViewById(R.id.profileDialogEmail);

        Uri imageUrl = Uri.parse(sharedPreferences.getString("image",""));
        name.setText(sharedPreferences.getString("fullname","Could Not Fetch"));
        email.setText(sharedPreferences.getString("email","Could Not Fetch"));
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.ic_account_circle_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(profileImg);

        syncBtn.setOnClickListener(v -> {
            //Drive Sync
            if(!isConnected){
                ProfileDialogFragment.this.getDialog().cancel();
                DialogFragment newFragment = new NoConnectionDialogFragment();
                newFragment.show(getActivity().getSupportFragmentManager(),"noConnectionDialog");
            }
            else {
                ProfileDialogFragment.this.getDialog().cancel();
                Intent intent = new Intent(getContext(), BackupActivity.class);
                startActivity(intent);
            }
        });

        signOutBtn.setOnClickListener(v -> {
            if(!isConnected){
                ProfileDialogFragment.this.getDialog().cancel();
                DialogFragment newFragment = new NoConnectionDialogFragment();
                newFragment.show(getActivity().getSupportFragmentManager(),"noConnectionDialog");
            }
            else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.putExtra("signOut", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        });
        builder.setView(DialogView)
                .setCancelable(true);
        return builder.create();
    }
}
