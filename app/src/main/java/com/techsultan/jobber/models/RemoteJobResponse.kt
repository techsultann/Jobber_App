package com.techsultan.jobber.models


import com.google.gson.annotations.SerializedName

data class RemoteJobResponse(
//    @SerializedName("job-count")
//    val jobCount: Job,
    @SerializedName("jobs")
    val jobs: List<Job?>?,
    @SerializedName("0-legal-notice")
    val legalNotice: String?,
    @SerializedName("00-warning")
    val warning: String?
)