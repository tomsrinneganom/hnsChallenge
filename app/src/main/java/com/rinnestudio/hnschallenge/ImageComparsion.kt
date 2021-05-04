package com.rinnestudio.hnschallenge

import android.graphics.Bitmap
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.calib3d.Calib3d
import org.opencv.core.*
import org.opencv.features2d.FlannBasedMatcher
import org.opencv.features2d.SIFT
import org.opencv.imgproc.Imgproc


//TODO()
class ImageComparsion {
    fun compare(bitmapObject: Bitmap, bitmapScene: Bitmap) = liveData {
        withContext(Dispatchers.Default) {
            OpenCVLoader.initDebug()
            val imgObject = Mat()
            val imgScene = Mat()
            Utils.bitmapToMat(bitmapObject, imgObject)
            Utils.bitmapToMat(bitmapScene, imgScene)
            Imgproc.cvtColor(imgObject, imgObject, Imgproc.COLOR_RGB2GRAY)
            Imgproc.cvtColor(imgScene, imgScene, Imgproc.COLOR_RGB2GRAY)

            val detector = SIFT.create()

            val keyPointsObject = MatOfKeyPoint()
            val keyPointsScene = MatOfKeyPoint()

            val descriptorsObject = Mat()
            val descriptorsScene = Mat()

            detector.detectAndCompute(imgObject, Mat(), keyPointsObject, descriptorsObject)
            detector.detectAndCompute(imgScene, Mat(), keyPointsScene, descriptorsScene)

            if (descriptorsObject.empty() && descriptorsScene.empty()) {
                emit(false)
                return@withContext
            }
            val matcher = FlannBasedMatcher.create()
            val knnMatches: List<MatOfDMatch> = ArrayList()
            matcher.knnMatch(descriptorsObject, descriptorsScene, knnMatches, 2)

            val ratioThresh = 0.75f
            val listOfGoodMatches: MutableList<DMatch> = ArrayList()

            for (i in knnMatches.indices) {
                if (knnMatches[i].rows() > 1) {
                    val matches: Array<DMatch> = knnMatches[i].toArray()
                    if (matches[0].distance < ratioThresh * matches[1].distance) {
                        listOfGoodMatches.add(matches[0])
                    }
                }
            }

            val goodMatches = MatOfDMatch()
            goodMatches.fromList(listOfGoodMatches)
            val obj: MutableList<Point> = ArrayList()
            val scene: MutableList<Point> = ArrayList()
            val listOfKeypointsObject: List<KeyPoint> = keyPointsObject.toList()
            val listOfKeypointsScene: List<KeyPoint> = keyPointsScene.toList()
            for (i in listOfGoodMatches.indices) {
                //-- Get the keypoints from the good matches
                obj.add(listOfKeypointsObject[listOfGoodMatches[i].queryIdx].pt)
                scene.add(listOfKeypointsScene[listOfGoodMatches[i].trainIdx].pt)
            }
            val objMat = MatOfPoint2f()
            val sceneMat = MatOfPoint2f()
            objMat.fromList(obj)
            sceneMat.fromList(scene)
            val ransacReprojThreshold = 3.0
            var result = true
            var homography = Mat()
            try {
                homography =
                    Calib3d.findHomography(objMat, sceneMat, Calib3d.RANSAC, ransacReprojThreshold)
            } catch (e: Exception) {
                result = false
            }

            emit(goodMatches.size().height > keyPointsObject.size().height / 100 * 6 && result)
        }
    }
}