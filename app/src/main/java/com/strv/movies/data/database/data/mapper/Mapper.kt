package com.strv.movies.data.database.data.mapper

interface Mapper<I, O> {
    fun map(from: I): O
}