package org.leondorus.remill.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Drug(
    val id: DrugId,
    val name: String,
    val photoPath: @Serializable(with = UriSerializer::class) Uri? = null,
    val notifGroupId: NotifGroupId?,
)

@Serializable
@Parcelize
data class DrugId(val id: Int) : Parcelable

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Uri::class)
object UriSerializer : KSerializer<Uri> {
    override fun serialize(encoder: Encoder, value: Uri) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Uri {
        return Uri.parse(decoder.decodeString())
    }
}
