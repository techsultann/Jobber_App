package com.techsultan.jobber.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class Job(
    @SerializedName("candidate_required_location")
    val candidateRequiredLocation: String?,
    val category: String?,
    @SerializedName("company_logo_url")
    val companyLogoUrl: String?,
    @SerializedName("company_name")
    val companyName: String?,
    val description: String?,
    val jobId: Int?,
    @SerializedName("job_type")
    val jobType: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    val salary: String?,
    @SerializedName("tags")
    val tags: List<String?>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
) : Parcelable, Serializable