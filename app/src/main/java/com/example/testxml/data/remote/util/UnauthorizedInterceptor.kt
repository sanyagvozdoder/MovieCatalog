package com.example.testxml.data.remote.util

import com.example.testxml.common.sharedprefs.deleteInSharedPrefs
import com.example.testxml.core.MyApplication
import com.example.testxml.domain.interfaces.UnauthorizedEvent
import com.example.testxml.presentation.components.UnauthorizedEventImpl
import okhttp3.Interceptor
import okhttp3.Response

class UnauthorizedInterceptor (
    private val event: UnauthorizedEvent = UnauthorizedEventImpl()
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 401) {
            deleteInSharedPrefs(MyApplication.instance)
            event.onEvent()
        }
        return response
    }
}