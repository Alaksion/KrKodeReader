package br.com.alaksion.core_db.domain.model

data class Scan(
    val id: Long,
    val createdAt: String,
    val title: String,
    val code: String
)
