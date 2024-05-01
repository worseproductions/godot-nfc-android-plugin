package com.worseproductions.godotnfcandroidplugin

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot
import java.nio.charset.Charset

class GodotNfcAndroidPlugin(godot: Godot) : GodotPlugin(godot), NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null

    private val tagReadSignalInfo = SignalInfo("TagRead", List::class.java)

    override fun getPluginName() = BuildConfig.GODOT_PLUGIN_NAME

    override fun getPluginSignals(): MutableSet<SignalInfo> {
        return mutableSetOf(tagReadSignalInfo)
    }

    @UsedByGodot
    private fun startNfc() {
        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    @UsedByGodot
    private fun stopNfc() {
        nfcAdapter?.disableReaderMode(activity)
    }

    @UsedByGodot
    override fun onTagDiscovered(tag: Tag?) {
        if (tag == null) return
        emitSignal(tagReadSignalInfo.name, readTag(tag))
    }

    private fun readTag(tag: Tag): List<String>? {
        return Ndef.get(tag)?.use { ndef ->
            ndef.connect()
            val payload = ndef.ndefMessage.records.map {record ->
                String(record.payload, Charset.forName("US-ASCII"))
            }
            payload
        }
    }
}
