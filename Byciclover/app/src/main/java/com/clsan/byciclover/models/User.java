package com.clsan.byciclover.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by clsan on 06/04/2017.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
  private Integer mId;

  @NonNull
  private String mNickname;
}
