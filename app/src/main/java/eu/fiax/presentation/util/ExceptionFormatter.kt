package eu.fiax.presentation.util

import android.content.Context
import eu.fiax.faxyomi.network.HttpException
import eu.fiax.faxyomi.util.system.isOnline
import faxyomi.core.common.i18n.stringResource
import faxyomi.data.source.NoResultsException
import faxyomi.domain.source.model.SourceNotInstalledException
import faxyomi.i18n.MR
import java.net.UnknownHostException

context(Context)
val Throwable.formattedMessage: String
    get() {
        when (this) {
            is HttpException -> return stringResource(MR.strings.exception_http, code)
            is UnknownHostException -> {
                return if (!isOnline()) {
                    stringResource(MR.strings.exception_offline)
                } else {
                    stringResource(MR.strings.exception_unknown_host, message ?: "")
                }
            }

            is NoResultsException -> return stringResource(MR.strings.no_results_found)
            is SourceNotInstalledException -> return stringResource(MR.strings.loader_not_implemented_error)
        }
        return when (val className = this::class.simpleName) {
            "Exception", "IOException" -> message ?: className
            else -> "$className: $message"
        }
    }
