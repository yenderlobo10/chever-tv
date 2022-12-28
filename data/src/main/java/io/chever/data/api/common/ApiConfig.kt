package io.chever.data.api.common

import android.content.Context
import io.chever.shared.extension.loadProperties

abstract class ApiConfig(
    context: Context,
    secretsFileName: String = defaultSecretsFileName
) {

    abstract val baseUrl: String
    abstract val apiKey: String

    val secrets = context.loadProperties(secretsFileName)

    companion object {

        const val defaultSecretsFileName = "chever-keys.properties"
    }
}