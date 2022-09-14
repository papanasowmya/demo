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

package rasel.lunar.launcher.settings

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rasel.lunar.launcher.BuildConfig
import rasel.lunar.launcher.databinding.SettingsActivityBinding
import rasel.lunar.launcher.helpers.Constants
import rasel.lunar.launcher.helpers.UniUtils
import java.util.*

internal class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding
    private lateinit var settingsClickListeners: SettingsClickListeners
    private var timeFormatValue = 0
    private var tempUnit = 0
    private var showCity = 0
    private var showTodos = 0
    private var lockMode = 0
    private var themeValue = 0
    private lateinit var dateFormatValue: String
    private lateinit var cityName: String
    private lateinit var owmKey: String
    private lateinit var feedUrl: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializer()
        loadSettings()

        settingsClickListeners.timeFormat(binding.timeGroup, binding.followSystemTime,
            binding.selectTwelve, binding.selectTwentyFour)
        settingsClickListeners.tempUnit(binding.tempGroup, binding.selectCelsius, binding.selectFahrenheit)
        settingsClickListeners.showCity(binding.cityGroup, binding.showCityNegative, binding.showCityPositive)
        settingsClickListeners.showTodos(binding.showTodos)
        settingsClickListeners.screenLock(binding.lockGroup, binding.selectLockNegative,
            binding.selectLockAccessibility, binding.selectLockAdmin, binding.selectLockRoot)
        settingsClickListeners.theme(binding.themeGroup, binding.followSystemTheme,
            binding.selectDarkTheme, binding.selectLightTheme)
        settingsClickListeners.openAbout(binding.about)
    }

    private fun initializer() {
        settingsClickListeners = SettingsClickListeners(this)
        val sharedPreferences = applicationContext.getSharedPreferences(Constants().SHARED_PREFS_SETTINGS, MODE_PRIVATE)
        timeFormatValue = sharedPreferences.getInt(Constants().SHARED_PREF_TIME_FORMAT, 0)
        dateFormatValue =
            sharedPreferences.getString(Constants().SHARED_PREF_DATE_FORMAT, Constants().DEFAULT_DATE_FORMAT).toString()
        cityName = sharedPreferences.getString(Constants().SHARED_PREF_CITY_NAME, "").toString()
        owmKey = sharedPreferences.getString(Constants().SHARED_PREF_OWM_KEY, "").toString()
        tempUnit = sharedPreferences.getInt(Constants().SHARED_PREF_TEMP_UNIT, 0)
        showCity = sharedPreferences.getInt(Constants().SHARED_PREF_SHOW_CITY, 0)
        showTodos = sharedPreferences.getInt(Constants().SHARED_PREF_SHOW_TODOS, 3)
        feedUrl = sharedPreferences.getString(Constants().SHARED_PREF_FEED_URL, "").toString()
        lockMode = sharedPreferences.getInt(Constants().SHARED_PREF_LOCK, 0)
        themeValue = sharedPreferences.getInt(Constants().SHARED_PREF_THEME, 0)
    }

    private fun loadSettings() {
        when (timeFormatValue) {
            0 -> binding.followSystemTime.isChecked = true
            1 -> binding.selectTwelve.isChecked = true
            2 -> binding.selectTwentyFour.isChecked = true
        }

        binding.dateFormat.setText(dateFormatValue)
        binding.inputCity.setText(cityName)
        binding.inputOwm.setText(owmKey)

        when (tempUnit) {
            0 -> binding.selectCelsius.isChecked = true
            1 -> binding.selectFahrenheit.isChecked = true
        }
        when (showCity) {
            0 -> binding.showCityNegative.isChecked = true
            1 -> binding.showCityPositive.isChecked = true
        }

        binding.showTodos.value = showTodos.toFloat()
        binding.inputFeedUrl.setText(feedUrl)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            binding.selectLockAccessibility.isEnabled = false
        }
        if (!UniUtils().isRooted) {
            binding.selectLockRoot.isEnabled = false
        }

        when (lockMode) {
            0 -> binding.selectLockNegative.isChecked = true
            1 -> binding.selectLockAccessibility.isChecked = true
            2 -> binding.selectLockAdmin.isChecked = true
            3 -> binding.selectLockRoot.isChecked = true
        }
        when (themeValue) {
            0 -> binding.followSystemTheme.isChecked = true
            1 -> binding.selectDarkTheme.isChecked = true
            2 -> binding.selectLightTheme.isChecked = true
        }
        binding.version.text = BuildConfig.VERSION_NAME
    }

    private val dateFormat: String
        get() = Objects.requireNonNull(binding.dateFormat.text).toString().trim { it <= ' ' }

    private fun getCityName(): String {
        return Objects.requireNonNull(binding.inputCity.text).toString().trim { it <= ' ' }
    }

    private fun getOwmKey(): String {
        return Objects.requireNonNull(binding.inputOwm.text).toString().trim { it <= ' ' }
    }

    private fun getFeedUrl(): String {
        return Objects.requireNonNull(binding.inputFeedUrl.text).toString().trim { it <= ' ' }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (dateFormat.isEmpty()) {
            SettingsPrefsUtils().saveDateFormat(applicationContext, Constants().DEFAULT_DATE_FORMAT)
        } else {
            SettingsPrefsUtils().saveDateFormat(applicationContext, dateFormat)
        }
        SettingsPrefsUtils().saveCityName(applicationContext, getCityName())
        SettingsPrefsUtils().saveOwmKey(applicationContext, getOwmKey())
        SettingsPrefsUtils().saveFeedUrl(applicationContext, getFeedUrl())
    }
}