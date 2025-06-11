package com.nexon.board.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.logging.LogLevel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.core.client.config.SdkAdvancedClientOption
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Component
class AwsS3Config(
    @Value("\${s3.access-key}")
    private val accessKey: String,
    @Value("\${s3.secret-key}")
    private val secretKey: String,
    @Value("\${s3.region}")
    private val region: String,
) {

    @Bean
    fun s3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }

}