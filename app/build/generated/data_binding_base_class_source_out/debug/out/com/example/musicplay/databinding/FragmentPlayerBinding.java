// Generated by view binder compiler. Do not edit!
package com.example.musicplay.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.musicplay.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPlayerBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final ProgressBar seekBar;

  @NonNull
  public final MaterialCardView songCardView;

  @NonNull
  public final ImageView songIconIV;

  @NonNull
  public final TextView songNameTV;

  private FragmentPlayerBinding(@NonNull MaterialCardView rootView, @NonNull ProgressBar seekBar,
      @NonNull MaterialCardView songCardView, @NonNull ImageView songIconIV,
      @NonNull TextView songNameTV) {
    this.rootView = rootView;
    this.seekBar = seekBar;
    this.songCardView = songCardView;
    this.songIconIV = songIconIV;
    this.songNameTV = songNameTV;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPlayerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPlayerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_player, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPlayerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.seekBar;
      ProgressBar seekBar = ViewBindings.findChildViewById(rootView, id);
      if (seekBar == null) {
        break missingId;
      }

      MaterialCardView songCardView = (MaterialCardView) rootView;

      id = R.id.songIconIV;
      ImageView songIconIV = ViewBindings.findChildViewById(rootView, id);
      if (songIconIV == null) {
        break missingId;
      }

      id = R.id.songNameTV;
      TextView songNameTV = ViewBindings.findChildViewById(rootView, id);
      if (songNameTV == null) {
        break missingId;
      }

      return new FragmentPlayerBinding((MaterialCardView) rootView, seekBar, songCardView,
          songIconIV, songNameTV);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}