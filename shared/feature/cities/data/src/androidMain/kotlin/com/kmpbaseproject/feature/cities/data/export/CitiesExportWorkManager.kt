package com.kmpbaseproject.feature.cities.data.export

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.kmpbaseproject.core.domain.ApplicationContext
import com.kmpbaseproject.feature.cities.domain.export.CitiesExportScheduler
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class)
class CitiesExportWorkManager(
  @ApplicationContext
  private val context: Context,
) : CitiesExportScheduler {
  val workManager by lazy { WorkManager.getInstance(context) }

  override fun schedule(query: String) {
    val request = OneTimeWorkRequestBuilder<CitiesExportWorker>()
      .setInputData(workDataOf(CitiesExportWorker.KEY_QUERY to query))
      .build()
    // A tap while an export is still pending doesn't start a second one
    workManager.enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.KEEP, request)
  }
}

private const val WORK_NAME = "cities-export"
