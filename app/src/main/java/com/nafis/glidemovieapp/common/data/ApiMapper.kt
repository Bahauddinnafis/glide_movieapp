package com.nafis.glidemovieapp.common.data

interface ApiMapper<Domain, Entity> {

    fun mapToDomain(apiDto: Entity): Domain

}