package com.example.emptytemiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.robotemi.sdk.*
import com.robotemi.sdk.Robot.*
import com.robotemi.sdk.Robot.Companion.getInstance
import com.robotemi.sdk.TtsRequest.Companion.create
import com.robotemi.sdk.activitystream.ActivityStreamObject
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage
import com.robotemi.sdk.constants.*
import com.robotemi.sdk.exception.OnSdkExceptionListener
import com.robotemi.sdk.exception.SdkException
import com.robotemi.sdk.face.ContactModel
import com.robotemi.sdk.face.OnContinuousFaceRecognizedListener
import com.robotemi.sdk.face.OnFaceRecognizedListener
import com.robotemi.sdk.listeners.*
import com.robotemi.sdk.map.Floor
import com.robotemi.sdk.map.MapModel
import com.robotemi.sdk.map.OnLoadFloorStatusChangedListener
import com.robotemi.sdk.map.OnLoadMapStatusChangedListener
import com.robotemi.sdk.model.CallEventModel
import com.robotemi.sdk.model.DetectionData
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener
import com.robotemi.sdk.navigation.listener.OnDistanceToDestinationChangedListener
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener
import com.robotemi.sdk.navigation.listener.OnReposeStatusChangedListener
import com.robotemi.sdk.navigation.model.Position
import com.robotemi.sdk.navigation.model.SafetyLevel
import com.robotemi.sdk.navigation.model.SpeedLevel
import com.robotemi.sdk.permission.OnRequestPermissionResultListener
import com.robotemi.sdk.permission.Permission
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener
import com.robotemi.sdk.sequence.SequenceModel
import com.robotemi.sdk.telepresence.CallState
import com.robotemi.sdk.telepresence.LinkBasedMeeting
import com.robotemi.sdk.telepresence.Participant
import com.robotemi.sdk.voice.ITtsService
import com.robotemi.sdk.voice.model.TtsVoice

class MainActivity : AppCompatActivity(), OnRequestPermissionResultListener, OnSdkExceptionListener, OnRobotReadyListener, OnLocationsUpdatedListener {

    private lateinit var places: List<String>
    private lateinit var robot: Robot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        robot = getInstance()

        robot.addOnRequestPermissionResultListener(this)
        robot.addOnSdkExceptionListener(this)
    }

    override fun onStart() {
        super.onStart()
        robot.showTopBar()
        robot.addOnRobotReadyListener(this)
        robot.addOnLocationsUpdatedListener(this)

    }

    override fun onStop() {

        robot.removeOnLocationsUpdateListener(this)
        robot.removeOnRobotReadyListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        robot.removeOnRequestPermissionResultListener(this)
        robot.removeOnSdkExceptionListener(this)
        super.onDestroy()
    }

    override fun onRobotReady(isReady: Boolean) {
        places = robot.locations
    }

    override fun onSdkError(sdkException: SdkException) {
    }

    override fun onRequestPermissionResult(
        permission: Permission,
        grantResult: Int,
        requestCode: Int
    ) {
    }

    override fun onLocationsUpdated(locations: List<String>) {
        places = robot.locations
    }

    fun speak(sentence: Any?) {
        val sentenceString = sentence?.toString() ?: ""
        robot.speak(create(sentenceString, false, TtsRequest.Language.SYSTEM, true, false))
    }




    fun a(places: List<String>) {
        robot.patrol(locations = places, times = 2, nonstop = false, waiting = 3 )
    }

    fun b(place: String) {
        robot.goTo(location = place, backwards = null, noBypass = null, speedLevel = null)
    }



    //https://github.com/robotemi/sdk/wiki/Locations    map

    //https://github.com/robotemi/sdk/wiki/Movement     manual movement



}
