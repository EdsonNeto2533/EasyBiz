package com.mctable.easybiz.core.config

import com.mctable.easybiz.BuildKonfig

class AppEnvImpl: AppEnv {
    override val host: String
        get() = BuildKonfig.API_HOST
}