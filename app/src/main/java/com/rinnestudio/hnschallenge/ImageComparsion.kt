package com.rinnestudio.hnschallenge

import android.graphics.Bitmap
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfDMatch
import org.opencv.core.MatOfKeyPoint
import org.opencv.features2d.DescriptorMatcher
import org.opencv.features2d.FlannBasedMatcher
import org.opencv.features2d.SIFT
import org.opencv.imgproc.Imgproc

//TODO()
class ImageComparsion {
    fun compare(bitmapObject: Bitmap, bitmapScene: Bitmap): String {
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

        val matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED)
        val matches = MatOfDMatch()
        val matcher1 = FlannBasedMatcher.create(FlannBasedMatcher.FLANNBASED)
        matcher.match(descriptorsObject, descriptorsScene,matches)
        //-- Draw matches
        //-- Filter matches using the Lowe's ratio test

        return "knnMatches: ${matches.size()} descriptorsObject: ${descriptorsObject.size()} descriptorsScene: ${descriptorsScene.size()}"
    }
}