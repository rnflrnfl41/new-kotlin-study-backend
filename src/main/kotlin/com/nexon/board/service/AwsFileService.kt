package com.nexon.board.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

@Service
class AwsFileService(
        private val s3Client: S3Client,
        @Value("\${s3.bucket}")
        private val bucketName: String,
        @Value("\${s3.region}")
        private  val region: String
) {

    fun uploadProfileImg(multipartFile: MultipartFile, fileName:String): String {
        val uniqueFileName = UUID.randomUUID().toString() + "-" + fileName

        // S3에 업로드
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(uniqueFileName)
                        .contentType(multipartFile.contentType)
                        .build(),
                RequestBody.fromBytes(multipartFile.bytes)
        )

        // 업로드된 파일의 URL 반환
        return "https://${bucketName}.s3.${region}.amazonaws.com/${uniqueFileName}"
    }

    fun deleteFile(filePath: String): Boolean {

        var fileName = filePath.substringAfterLast("amazonaws.com/")

        return try {

            s3Client.deleteObject(
                DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

}