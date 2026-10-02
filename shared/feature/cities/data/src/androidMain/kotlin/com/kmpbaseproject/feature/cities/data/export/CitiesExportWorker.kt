package com.kmpbaseproject.feature.cities.data.export

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kmpbaseproject.core.domain.storage.DownloadsStorage
import com.kmpbaseproject.feature.cities.domain.CitiesRepository
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

class CitiesExportWorker @Inject constructor(
  @Assisted
  context: Context,
  @Assisted
  params: WorkerParameters,
  private val citiesRepository: CitiesRepository,
  private val downloadsStorage: DownloadsStorage,
) : CoroutineWorker(context, params) {
  override suspend fun doWork(): Result {
    val query = inputData.getString(KEY_QUERY) ?: return Result.failure()
    val markdown = citiesRepository.cities(query)
      .mapIndexed { index, city -> "${index + 1}. ${city.title}" }
      .joinToString(
        separator = "\n",
        postfix = "\n"
      )
    downloadsStorage.save(
      fileName = FILE_NAME,
      mimeType = MIME_TYPE,
      content = markdown
    )
    return Result.success()
  }

  companion object {
    const val KEY_QUERY = "query"
  }
}

private const val FILE_NAME = "cities.md"
private const val MIME_TYPE = "text/markdown"
