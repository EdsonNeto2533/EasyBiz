package com.mctable.easybiz.features.my_business.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.my_business.data.mapper.MyBusinessMapper
import com.mctable.easybiz.features.my_business.data.model.MyBusinessResponseModel

interface MyBusinessDatasource {
    suspend fun getMyBusiness(): Result<List<MyBusinessResponseModel>>
}

class MyBusinessDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : MyBusinessDatasource {
    override suspend fun getMyBusiness(): Result<List<MyBusinessResponseModel>> {
        return networking.get(
            host = appEnv.host,
            path = "/negocios/me",
            responseMapper = { jsonString ->
                MyBusinessMapper.parseListResponse(jsonString)
            }
        )
    }
}
