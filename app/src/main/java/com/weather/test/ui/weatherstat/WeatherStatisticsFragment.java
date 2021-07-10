package com.weather.test.ui.weatherstat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.weather.test.R;
import com.weather.test.databinding.FragmentWeatherStatisticsBinding;
import com.weather.test.domain.common.viewstate.FetchDataViewState;
import com.weather.test.domain.value.Celsius;
import com.weather.test.domain.value.Fahrenheit;
import com.weather.test.domain.value.KilometersPerHour;
import com.weather.test.domain.value.MilesPerHour;
import com.weather.test.domain.value.Speed;
import com.weather.test.domain.value.Temperature;
import com.weather.test.domain.viewmodel.weatherstat.WeatherStatisticsViewModel;
import com.weather.test.domain.viewmodel.weatherstat.WeatherStatisticsViewModel.UiWeatherStatisticsEntity;
import kotlin.Lazy;

import static org.koin.android.compat.ViewModelCompat.viewModel;

public class WeatherStatisticsFragment extends Fragment {
    final private Lazy<WeatherStatisticsViewModel> viewModel = viewModel(this,
            WeatherStatisticsViewModel.class);
    private FragmentWeatherStatisticsBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentWeatherStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final WeatherStatisticsViewModel actualViewModel = viewModel.getValue();

        binding.weatherStatisticsFragmentConstraintLayoutTemperature.setOnClickListener(v ->
            actualViewModel.changeTemperatureUnits());

        binding.weatherStatisticsFragmentConstraintLayoutWindSpeed.setOnClickListener(v ->
            actualViewModel.changeWindSpeedUnits());

        binding.weatherStatisticsFragmentButtonGetWeather.setOnClickListener(v -> {
            final String zipCodeText = binding.weatherStatisticsFragmentTextInputEditTextZipCode
                    .getEditableText().toString();
            final int zipCode = zipCodeText.length() == 0 ? 0 : Integer.parseInt(zipCodeText);
            actualViewModel.loadWeatherStatistics(zipCode);
        });

        actualViewModel.getTemperature().observe(getViewLifecycleOwner(), this::fillTemperatureField);

        actualViewModel.getWindSpeed().observe(getViewLifecycleOwner(), this::fillWindSpeedField);

        actualViewModel.getWeatherStatistics().observe(getViewLifecycleOwner(), result -> {
            if (result instanceof FetchDataViewState.Loading)
                showLoading();
            else if (result instanceof FetchDataViewState.Data) {
                hideLoading();
                fillFields(((FetchDataViewState.Data<UiWeatherStatisticsEntity>) result).getData());
            } else if (result instanceof FetchDataViewState.Failure) {
                hideLoading();
                showErrorToast(((FetchDataViewState.Failure<UiWeatherStatisticsEntity>) result).getError());
            }
        });
    }

    private void showLoading() {
        binding.weatherStatisticsFragmentFrameLayoutProgressBarHolder.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.weatherStatisticsFragmentFrameLayoutProgressBarHolder.setVisibility(View.GONE);
    }

    private void fillFields(UiWeatherStatisticsEntity statistics) {
        binding.weatherStatisticsFragmentTextViewLocationData.setText(statistics.getLocation());
        fillTemperatureField(statistics.getTemperature());
        fillWindSpeedField(statistics.getWindSpeed());
        binding.weatherStatisticsFragmentTextViewHumidityData.setText(
                getString(R.string.weatherStatistics_humidity_value, statistics.getHumidity()));
        binding.weatherStatisticsFragmentTextViewVisibilityData.setText(
                getString(R.string.weatherStatistics_visibility_value, statistics.getVisibility()));
        binding.weatherStatisticsFragmentTextViewSunriseTimeData.setText(
                statistics.getFormattedSunriseTime());
        binding.weatherStatisticsFragmentTextViewSunsetTimeData.setText(
                statistics.getFormattedSunsetTime());
    }

    private void fillTemperatureField(Temperature temperature) {
        int resourceStringId;
        if (temperature instanceof Fahrenheit)
            resourceStringId = R.string.weatherStatistics_temperatureFahrenheit_value;
        else if (temperature instanceof Celsius)
            resourceStringId = R.string.weatherStatistics_temperatureCelsius_value;
        else
            throw new IllegalStateException("Unexpected state");
        binding.weatherStatisticsFragmentTextViewTemperatureData.setText(
                getString(resourceStringId, temperature.getValue()));
    }

    private void fillWindSpeedField(Speed windSpeed) {
        int resourceStringId;
        if (windSpeed instanceof MilesPerHour)
            resourceStringId = R.string.weatherStatistics_speedMph_value;
        else if (windSpeed instanceof KilometersPerHour)
            resourceStringId = R.string.weatherStatistics_speedKmph_value;
        else
            throw new IllegalStateException("Unexpected state");
        binding.weatherStatisticsFragmentTextViewWindSpeedData.setText(
                getString(resourceStringId, windSpeed.getValue()));
    }

    private void showErrorToast(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }
}
