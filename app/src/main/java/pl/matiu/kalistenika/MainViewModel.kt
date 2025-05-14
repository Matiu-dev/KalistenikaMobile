package pl.matiu.kalistenika

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.matiu.kalistenika.language.AppLanguage
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val appLanguage: AppLanguage) : ViewModel()