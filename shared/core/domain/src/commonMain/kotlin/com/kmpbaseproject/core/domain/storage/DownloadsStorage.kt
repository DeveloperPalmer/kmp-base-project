package com.kmpbaseproject.core.domain.storage

interface DownloadsStorage {
  suspend fun save(fileName: String, mimeType: String, content: String)
}
