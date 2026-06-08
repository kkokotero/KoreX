package io.github.kkokotero.korex.sample.errors

import io.github.kkokotero.korex.errors.ErrorMapper
import io.github.kkokotero.korex.errors.UserFacingError

class SampleErrorMapper : ErrorMapper {
    override fun map(error: Throwable): UserFacingError = UserFacingError(
        title = "KoreX Sample Error",
        description = error.message ?: "Unknown sample error",
        cause = error,
    )
}
