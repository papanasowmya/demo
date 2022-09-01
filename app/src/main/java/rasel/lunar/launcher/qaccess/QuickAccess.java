/*
 * Lunar Launcher
 * Copyright (C) 2022 Md Rasel Hossain
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package rasel.lunar.launcher.qaccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import rasel.lunar.launcher.databinding.QuickAccessBinding;
import rasel.lunar.launcher.helpers.Constants;

public class QuickAccess extends BottomSheetDialogFragment {

    private QuickAccessBinding binding;
    private final Constants constants = new Constants();
    private AccessUtils accessUtils;
    String packageOne, packageTwo, packageThree, packageFour, packageFive, packageSix,
            phoneNumOne, phoneNumTwo, phoneNumThree, thumbPhoneOne, thumbPhoneTwo, thumbPhoneThree,
            urlStringOne, urlStringTwo, urlStringThree, thumbUrlOne, thumbUrlTwo, thumbUrlThree;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = QuickAccessBinding.inflate(inflater, container, false);

        accessUtils = new AccessUtils(requireContext(), this, requireActivity());
        favApps();
        accessUtils.controlBrightness(binding.brightness);
        accessUtils.volumeControllers(binding.notification, binding.alarm, binding.media, binding.voice, binding.ring);

        return binding.getRoot();
    }

    private void favApps() {
        SharedPreferences prefsFavApps = requireContext().getSharedPreferences(constants.SHARED_PREFS_FAV_APPS, Context.MODE_PRIVATE);
        packageOne = prefsFavApps.getString(constants.FAV_APP_ + 1, "");
        packageTwo = prefsFavApps.getString(constants.FAV_APP_ + 2, "");
        packageThree = prefsFavApps.getString(constants.FAV_APP_ + 3, "");
        packageFour = prefsFavApps.getString(constants.FAV_APP_ + 4, "");
        packageFive = prefsFavApps.getString(constants.FAV_APP_ + 5, "");
        packageSix = prefsFavApps.getString(constants.FAV_APP_ + 6, "");

        accessUtils.favApps(packageOne, binding.appOne, 1); accessUtils.favApps(packageTwo, binding.appTwo, 2);
        accessUtils.favApps(packageThree, binding.appThree, 3); accessUtils.favApps(packageFour, binding.appFour, 4);
        accessUtils.favApps(packageFive, binding.appFive, 5); accessUtils.favApps(packageSix, binding.appSix, 6);
    }

    private void favPhoneAndUrls() {
        SharedPreferences prefsPhonesAndUrls = requireContext().getSharedPreferences(constants.SHARED_PREFS_PHONES_URLS, Context.MODE_PRIVATE);

        phoneNumOne = prefsPhonesAndUrls.getString(constants.PHONE_NO_ + 1, "");
        phoneNumTwo = prefsPhonesAndUrls.getString(constants.PHONE_NO_ + 2, "");
        phoneNumThree = prefsPhonesAndUrls.getString(constants.PHONE_NO_ + 3, "");
        thumbPhoneOne = prefsPhonesAndUrls.getString(constants.PHONE_THUMB_LETTER_ + 1, "");
        thumbPhoneTwo = prefsPhonesAndUrls.getString(constants.PHONE_THUMB_LETTER_ + 2, "");
        thumbPhoneThree = prefsPhonesAndUrls.getString(constants.PHONE_THUMB_LETTER_ + 3, "");

        urlStringOne = prefsPhonesAndUrls.getString(constants.URL_NO_ + 1, "");
        urlStringTwo = prefsPhonesAndUrls.getString(constants.URL_NO_ + 2, "");
        urlStringThree = prefsPhonesAndUrls.getString(constants.URL_NO_ + 3, "");
        thumbUrlOne = prefsPhonesAndUrls.getString(constants.URL_THUMB_LETTER_ + 1, "");
        thumbUrlTwo = prefsPhonesAndUrls.getString(constants.URL_THUMB_LETTER_ + 2, "");
        thumbUrlThree = prefsPhonesAndUrls.getString(constants.URL_THUMB_LETTER_ + 3, "");

        accessUtils.phonesAndUrls(constants.URL_ADDRESS, urlStringOne, thumbUrlOne, binding.urlOne, 1);
        accessUtils.phonesAndUrls(constants.URL_ADDRESS, urlStringTwo, thumbUrlTwo, binding.urlTwo, 2);
        accessUtils.phonesAndUrls(constants.URL_ADDRESS, urlStringThree, thumbUrlThree, binding.urlThree, 3);

        accessUtils.phonesAndUrls(constants.PHONE_NO, phoneNumOne, thumbPhoneOne, binding.phoneOne, 1);
        accessUtils.phonesAndUrls(constants.PHONE_NO, phoneNumTwo, thumbPhoneTwo, binding.phoneTwo, 2);
        accessUtils.phonesAndUrls(constants.PHONE_NO, phoneNumThree, thumbPhoneThree, binding.phoneThree, 3);
    }

    @Override
    public void onResume() {
        super.onResume();
        favPhoneAndUrls();
    }
}
