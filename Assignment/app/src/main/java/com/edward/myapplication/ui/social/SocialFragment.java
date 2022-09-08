package com.edward.myapplication.ui.social;


import static com.edward.myapplication.config.CONFIG.CHOOSE_FLATFORM;
import static com.edward.myapplication.config.CONFIG.SHARE_TITLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edward.myapplication.databinding.FragmentSocialBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SocialFragment extends Fragment {

    private FragmentSocialBinding binding;
    TextView txtImage;
    ImageView imgShare;
    Bitmap bitmap;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSocialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText content = binding.edtContent;
        Button share = binding.btnShare;
        LinearLayout linearLayout = binding.linearShare;
        txtImage = binding.txtImage;
        imgShare = binding.imgShare;
        linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            chooseImage.launch(intent);
        });

        share.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,  content.getText().toString());
            intent.putExtra(Intent.EXTRA_SUBJECT, SHARE_TITLE);

            if(bitmap != null){
                Uri uri = getmageToShare(bitmap);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
            }else{
                intent.setType("text/plain");
            }

            startActivity(Intent.createChooser(intent, CHOOSE_FLATFORM));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //    private void printKeyHash(){
//        try {
//            @SuppressLint("PackageManagerGetSignatures")
//            PackageInfo info = requireActivity().getPackageManager().getPackageInfo("com.edward.myapplication", PackageManager.GET_SIGNATURES);
//            for (Signature signature: info.signatures){
//                MessageDigest md =MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                System.out.println(Base64.encodeToString(md.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

    ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri selectedImageUri =data.getData();
                        if (selectedImageUri!=null){
                            imgShare.setImageURI(selectedImageUri);
                            txtImage.setText("choose image");
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgShare.getDrawable();
                            bitmap = bitmapDrawable.getBitmap();
                        }
                    }
                }
            }
    );

    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File( "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(requireContext(), "com.edward.myapplication.fileprovider", file);
        } catch (Exception e) {
            Log.e(String.valueOf(e), "getmageToShare: ");
        }
        return uri;
    }
}