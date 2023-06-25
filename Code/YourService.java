package jp.jaxa.iss.kibo.rpc.sampleapk;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.android.gs.MessageType;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Mat;
import android.util.Log;
//import org.scijava.nativelib.NativeLoader;

//import clojure.lang.Var;

import org.opencv.aruco.DetectorParameters;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.aruco.Aruco;
import org.opencv.aruco.Dictionary;
//import org.opencv.highgui.HighGui;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;
//import org.opencv.core.FeatureDetector;
//import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.features2d.FastFeatureDetector;

/**
 * Class meant to handle commands from the Ground Data System and execute them in Astrobee
 */

//moveToPos(pathTarget1, quaternionTarget1);
//        delay(5);
//        Mat img = api.getMatNavCam();
//        api.saveMatImage(img, "Target1.png");
//        aim(img, pathTarget1[pathTarget1.length - 1], 1);
//        delay(5);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target1Snap.png");
//        api.laserControl(false);
//        api.takeTargetSnapshot(1);
//
//        moveToPos(pathTarget2, quaternionTarget2);
//        delay(5);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target2.png");
//        aim(img, pathTarget2[pathTarget2.length - 1], 4);
//        delay(5);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target2Snap.png");
//        api.laserControl(false);
//        api.takeTargetSnapshot(2);
//
//        moveToPos(pathTarget3, quaternionTarget3);
//        delay(5);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target3.png");
//        aim(img, pathTarget3[pathTarget3.length - 1], 4);
//        delay(5);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target3Snap.png");
//        api.laserControl(false);
//        api.takeTargetSnapshot(3);
//
//        moveToPos(pathTarget4, quaternionTarget4);
//        delay(5);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target4.png");
//        aim(img, pathTarget4[pathTarget4.length - 1], 2);
//        delay(5);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target4Snap.png");
//        api.laserControl(false);
//        api.takeTargetSnapshot(4);
//
//        moveToPos(pathTarget5, quaternionTarget5);
//        delay(5);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target5.png");
//        aim(img, pathTarget5[pathTarget5.length - 1], 5);
//        delay(5);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target5Snap.png");
//        api.laserControl(false);
//        api.takeTargetSnapshot(5);
//
//        moveToPos(pathTarget6, quaternionTarget6);
//        delay(5);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target6.png");
//        aim(img, pathTarget6[pathTarget6.length - 1], 0);
//        delay(5);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target6Snap.png");
//        api.laserControl(false);
//        api.takeTargetSnapshot(6);

public class YourService extends KiboRpcService {
    // ------------------ Setting up variable --------------------
    final int LOOP_MAX = 5;
    double[] cameraMatrixArray = {
            523.105750, 0.000000, 635.434258,
            0.000000, 534.765913, 500.335102,
            0.000000, 0.000000, 1.000000
    };
    double[] distCoeffsArray = { -0.164787, 0.020375, -0.001572, -0.000369, 0.000000 };


//    Point[][][] path = {
//            {{} , {new Point(10.4f, -10.1f, 4.47f), new Point(11.2746f, -10.04f, 5.41f)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}},
//            {{} , {}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}},
//            {{} , {new Point(0, 0, 0)}, {}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}},
//            {{} , {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}},
//            {{} , {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}},
//            {{} , {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}},
//            {{} , {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {}, {new Point(0, 0, 0)}},
//            {{} , {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {}}
//    };

    Point[][][] path = {
            {{} , {new Point(10.4f, -10.1f, 4.47f), new Point(11.2746f,-9.92284f,5.2988f)}, {new Point(10.46f,-9.806f,4.48f),new Point(10.612,-9.0709,4.48)}, {new Point(10.46f,-9.806f,4.48f),new Point(10.71f,-7.7f,4.85815f),new Point(10.71f,-7.7f,4.48f)}, {new Point(10.46f,-9.806f,4.48f),new Point(10.51f,-6.7185f,5.1804f)}, {new Point(10.46f,-9.806f,4.48f),new Point(11.114f,-7.9756f,5.3393f)}, {new Point(10.46f,-9.806f,4.48f),new Point(11.355f,-8.9929f,4.7818f)}, {}, {}},
            {{} , {}, {new Point(10.612f,-9.0709f,4.9065f),new Point(10.612f,-9.0709f,4.48f)}, {new Point(10.71f,-7.7f,4.6778f),new Point(10.71f,-7.7f,4.48f)}, {new Point(10.51f,-6.7185f,5.2988f),new Point(10.51f,-6.7185f,5.1804f)}, {new Point(11.114f,-7.9756f,5.3393f)}, {new Point(11.355f,-8.9929f,4.7818f)}, {new Point(11.369f,-8.5518f,4.48f)}, {new Point(11.143f,-6.7607f,4.68094f),new Point(11.143f,-6.7607f,4.9654f)}},
            {{} , {new Point(10.612f,-9.0709f,4.9065f),new Point(11.2746f,-9.92284f,5.2988f)}, {}, {new Point(10.71f,-7.7f,5.09302f),new Point(10.71f,-7.7f,4.48f)}, {new Point(10.51f,- 9.0709f,5.1804f),new Point(10.51f,-6.7185f,5.1804f)}, {new Point(10.71f,-7.7f,5.41f),new Point(11.114f,-7.9756f,5.3393f)}, {new Point(10.612f,-9.0709f,5.41f),new Point(11.355f,-8.9929f,4.7818f)},{new Point(10.612f,-8.5845f,4.48f),new Point(11.369f,-8.5518f,4.48f)}, {new Point(10.612f,-8.1178f,4.96604f),new Point(11.143f,-6.7607f,4.9654f)}},
            {{} , {new Point(10.71f,-7.7f,4.6778f),new Point(11.2746f,-9.92284f,5.2988f)}, {new Point(10.71f,-7.7f,5.09302f),new Point(10.612f,-9.0709f,4.48f)}, {}, {new Point(10.51f,-6.9132f,5.1804f),new Point(10.51f,-6.7185f,5.1804f)}, {new Point(10.71f,-7.7f,5.41f),new Point(11.114f,-7.9756f,5.3393f)}, {new Point(10.8418f,-7.9564f,4.95047f),new Point(11.355f,-8.9929f,4.7818f)}, {new Point(11.369f,-8.5518f,4.99538f),new Point(11.369f,-8.5518f,4.48f)}, {new Point(11.143f,-6.7607f,4.9654f)}},
            {{} , {new Point(10.51f,-6.7185f,5.28668f),new Point(11.2746f,-9.92284f,5.2988f)}, {new Point(10.612f,-9.0709f,5.1804f),new Point(10.612f,-9.0709f,4.48f)}, {new Point(10.51f,-6.9132f,5.1804f),new Point(10.71f,-7.7f,4.48f)}, {}, {new Point(11.114f,-7.9756f,5.3393f)}, {new Point(10.51f,-8.9929f,5.1804f),new Point(11.355f,-8.9929f,4.7818f)}, {new Point(10.51f,-8.5518f,5.1804f),new Point(11.369f,-8.5518f,4.48f)},{new Point(11.143f,-6.7607f,4.9654f)}},
            {{} , {new Point(11.114f,-7.9756f,5.3393f)}, {new Point(10.7955f,-8.0635f,5.28879f),new Point(10.612f,-9.0709f,4.48f)}, {new Point(10.69f,-7.7f,5.41f),new Point(10.71f,-7.7f,4.48f)}, {new Point(10.51f,-6.7185f,5.1804f)}, {}, {new Point(11.355f,-8.9929f,4.7818f)}, {new Point(11.369f,-8.5518f,4.83f),new Point(11.369f,-8.5518f,4.48f)}, {new Point(11.143f,-6.7607f,5.12219f),new Point(11.143f,-6.7607f,4.9654f)}},
            {{} , {new Point(11.2746f,-9.92284f,5.2988f)}, {new Point(10.612f,-9.0709f,5.0818f),new Point(10.612f,-9.0709f,4.48f)}, {new Point(10.8418f,-7.9564f,4.95047f),new Point(10.71f,-7.7f,4.48f)}, {new Point(10.51f,-8.9929f,5.1804f),new Point(10.51f,-6.7185f,5.1804f)}, {new Point(11.114f,-7.9756f,5.3393f)}, {}, {new Point(11.369f,-8.5518f,4.48f)}, {new Point(11.143f,-6.7607f,4.9654f)}},
            {{} , {new Point(11.2746f,-9.92284f,5.2988f)}, {new Point(10.612f,-8.5845f,4.48f),new Point(10.612f,-9.0709f,4.48f)}, {new Point(11.369f,-8.5518f,5.09538f),new Point(10.71,-7.7,4.48)}, {new Point(10.51f,-8.5518f,5.1804f),new Point(10.51f,-6.7185f,5.1804f)}, {new Point(11.369f,-8.5518f,4.6725f),new Point(11.114f,-7.9756f,5.3393f)}, {new Point(11.355f,-8.9929f,4.7818f)}, {}, {new Point(11.369f, -8.5518f, 4.9654f), new Point(11.143f, -6.7607f, 4.9654f)}},
            {{} , {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {new Point(0, 0, 0)}, {}}
    };

    Quaternion[] quaternions = {
            new Quaternion(0f, 0f, -0.707f, 0.707f),
            new Quaternion(0f, 0f, -0.707f, 0.707f),
            new Quaternion(0.5f, 0.5f, -0.5f, 0.5f),
            new Quaternion(0f, 0.707f, 0f, 0.707f),
            new Quaternion(0f, 0f, -1f, 0f),
            new Quaternion(-0.5f, -0.5f, -0.5f, 0.5f),
            new Quaternion(0f, 0f, 0f, 1f),
            new Quaternion(0f, 0.707f, 0f, 0.707f)
    };

    Point[] pathTarget1 = new Point[] {
            new Point(10.4f, -10.1f, 4.47f),
            new Point(11.2746f, -10.04f, 5.41f)
    };
    Quaternion quaternionTarget1 = new Quaternion(0f, 0f, -0.707f, 0.707f);

    Point[] pathTarget2 = new Point[] {
            new Point(11.2746f, -9.92284f, 5.41f),
            new Point(10.612f, -9.0709f, 5.41f),
            new Point(10.612f, -9.0709f, 4.48f)
    };
    Quaternion quaternionTarget2 = new Quaternion(0.5f, 0.5f, -0.5f, 0.5f);

    Point[] pathTarget21 = new Point[] {
            new Point(10.612f, -9.0709f, 5.41f),
            new Point(11.2746f, -9.92284f, 5.41f),
            new Point(11.2746f, -10.04f, 5.41f)
    };

    Point[] pathTarget3 = new Point[] {
            new Point(10.612f, -9.0709f, 4.8325f),
            new Point(10.71f, -7.7f, 4.8325f),
            new Point(10.71f, -7.7f, 4.48f)
    };
    Quaternion quaternionTarget3 = new Quaternion(0f, 0.707f, 0f, 0.707f);

    Point[] pathTarget4 = new Point[] {
            new Point(10.46f, -7.7f, 4.48f),
            new Point(10.46f, -6.7185f, 4.48f),
            new Point(10.51f, -6.7185f, 5.1804)
    };
    Quaternion quaternionTarget4 = new Quaternion(0f, 0f, -1f, 0f);

    Point[] pathTarget5 = new Point[] {
            new Point(10.46f, -6.7185f, 5.3393f),
            new Point(11.114f, -7.9756f, 5.3393)
    };
    Quaternion quaternionTarget5 = new Quaternion(-0.5f, -0.5f, -0.5f, 0.5f);

    Point[] pathTarget6 = new Point[] {
            //new Point(11.355f, -8.9929f, 5.3393f),
            new Point(11.355f, -8.9929f, 4.7818)
    };
    Quaternion quaternionTarget6 = new Quaternion(0f, 0f, 0f, 1f);

    Point[] pathTarget7 = new Point[] {
            new Point(11.369f, -8.5518f, 4.48)
    };
    Quaternion quaternionTarget7 = new Quaternion(0f, 0.707f, 0f, 0.707f);

    Point[] pathGoal = new Point[] {
            new Point(11.369f, -8.5518f, 4.9654f),
            new Point(11.143f, -6.7607f, 4.9654f)
    };
    Quaternion quaternionGoal = new Quaternion(0f, 0f, -0.707f, 0.707f);

    @Override
    protected void runPlan1(){
        // the mission starts
        api.startMission();
        int loop_counter = 0;
        String mQrContent = "";
        Mat img;

        List<Long> timeRemain = api.getTimeRemaining();
        System.out.print("Time Remain ");
        System.out.println(timeRemain);

        //---
//        moveToPos(pathTarget1, quaternionTarget1);
//        delay(3);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target1.png");
//        aim(img, pathTarget1[pathTarget1.length - 1], 1);
//        delay(3);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target1Snap.png");
//        api.takeTargetSnapshot(1);
//        api.laserControl(false);

        ////////////////////////
        double[] upq = pyrToQ(90f,0f,0f);
        quaternions[2] = new Quaternion((float) upq[0], (float) upq[1], (float) upq[2], (float) upq[3]);
        quaternions[3] = new Quaternion((float) upq[0], (float) upq[1], (float) upq[2], (float) upq[3]);
        double[] downq = pyrToQ(-90f,0f,0f);
        quaternions[5] = new Quaternion((float) downq[0], (float) downq[1], (float) downq[2], (float) downq[3]);

//        moveToPos(pathTarget2, quaternionTarget2);
//        delay(3);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target2.png");
//        aim(img, pathTarget2[pathTarget2.length - 1], 4);
//        delay(3);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target2Snap.png");
//        api.takeTargetSnapshot(2);
//        api.laserControl(false);
//
//        moveToPos(pathTarget21, quaternionTarget1);
//        delay(5);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target3.png");
//        aim(img, pathTarget3[pathTarget3.length - 1], 4);
//        delay(5);
//        api.laserControl(true);
//        img = api.getMatNavCam();
//        api.saveMatImage(img, "Target3Snap.png");
//        api.takeTargetSnapshot(3);
//        api.laserControl(false);
        //---

        // get the list of active target id
        int currentPoint = 0;
        boolean scannedQR = false;
        List<Integer> targetsList = api.getActiveTargets();
        while(targetsList.size() > 0){
            timeRemain = api.getTimeRemaining();
            System.out.print("Time Remain ");
            System.out.println(timeRemain);
            if(timeRemain.get(1) < 135000 && !scannedQR) break;
            if(timeRemain.get(1) < 40000) break;

            System.out.print("Active Targets");
            System.out.println(targetsList);
            for(int i:targetsList){
                int direction = 0;
                if(i == 1) direction = 1;
                if(i == 2) direction = 4;
                if(i == 3) direction = 6;//4
                if(i == 4) direction = 2;
                if(i == 5) direction = 5;
                if(i == 6) direction = 0;

                moveToPos(path[currentPoint][i], quaternions[i]);
                delay(3);
                img = api.getMatNavCam();
                api.saveMatImage(img, "Target"+i+".png");
                aim(img, path[currentPoint][i][path[currentPoint][i].length - 1], direction);
                delay(3);
                api.laserControl(true);
                img = api.getMatNavCam();
                api.saveMatImage(img, "Target"+i+"Snap.png");
                api.takeTargetSnapshot(i);
                api.laserControl(false);
                currentPoint = i;
                if((i == 1 || i == 2 || i == 6) && !scannedQR){
                    moveToPos(path[currentPoint][7], quaternions[7]);
                    delay(3);
                    api.flashlightControlFront(0.05f);
                    img = api.getMatNavCam();
                    api.saveMatImage(img, "Target7.png");
                    mQrContent = scanQR(img);
                    api.flashlightControlFront(0.00f);
                    scannedQR = true;
                }

                if(timeRemain.get(1) < 135000 && !scannedQR) break;
                if(timeRemain.get(1) < 40000) break;
            }
            targetsList = api.getActiveTargets();
        }

        if(!scannedQR) {
            moveToPos(path[currentPoint][7], quaternions[7]);
            delay(3);
            api.flashlightControlFront(0.05f);
            img = api.getMatNavCam();
            api.saveMatImage(img, "Target7.png");
            mQrContent = scanQR(img);
            api.flashlightControlFront(0.00f);
            currentPoint = 7;
        }

        // notify that astrobee is heading to the goal
        api.notifyGoingToGoal();
        moveToPos(path[currentPoint][8], quaternionGoal);
        // send mission completion
        api.reportMissionCompletion(mQrContent);
    }

    @Override
    protected void runPlan2(){
        // write your plan 2 here
    }

    @Override
    protected void runPlan3(){
        // write your plan 3 here
    }

    private void aim(Mat img, Point position, int direction){ // direction 0 = front; 1 = left; 2 = back; 3 = right; 4 = up; 5 = down; 6 = up(point3);
        System.out.println("Start aim");
        Mat cameraMatrix = new Mat(3, 3, CvType.CV_32FC1);
        Mat distCoeffs = new Mat(1, 5, CvType.CV_32FC1);
        int row = 0, col = 0;
        cameraMatrix.put(row, col, cameraMatrixArray);
        distCoeffs.put(row, col, distCoeffsArray);
        Dictionary dictionary = Aruco.getPredefinedDictionary(Aruco.DICT_5X5_250);
        List<Mat> arucoFound = new ArrayList<>();
        Mat markerIds = new Mat();
        Mat rotationMatrix = new Mat(), translationVectors = new Mat();
        DetectorParameters parameters = DetectorParameters.create();
        Aruco.detectMarkers(img, dictionary, arucoFound, markerIds, parameters);
        Aruco.estimatePoseSingleMarkers(arucoFound, 0.05f, cameraMatrix, distCoeffs, rotationMatrix, translationVectors);

        int size2 = (int) (translationVectors.total() * translationVectors.channels());
        double[] disCam2TargetArray = new double[size2];
        translationVectors.get(0, 0, disCam2TargetArray);

        double avg = 0f;
        double ppm;

        if (arucoFound.size() > 0) {
            for (Mat corner : arucoFound) {
                corner.convertTo(corner, CvType.CV_64FC3);
                int size = (int) (corner.total() * corner.channels());
                double[] temp = new double[size]; // use double[] instead of byte[]
                corner.get(0, 0, temp);
                ppm = Math.sqrt(Math.pow(temp[2] - temp[0], 2) + Math.pow(temp[3] - temp[1], 2)) / 0.05f;
                avg += ppm;
            }

            ppm = avg / arucoFound.size();

            double[] fr = {};
            if(direction == 0) fr = pyrToQ(0f, 0f, 10f);
            if(direction == 1) fr = pyrToQ(0f, 0f, -80f);
            if(direction == 2) fr = pyrToQ(0f, 0f, -170f);
            if(direction == 3) fr = pyrToQ(0f, 0f, -260f);
            if(direction == 4 || direction == 6) fr = pyrToQ(80f, 0f, 0f);
            if(direction == 5) fr = pyrToQ(-80f, 0f, 0f);

            double avgDisCam2TargetArray = 0f;
            for(int i = 2;i < disCam2TargetArray.length; i+=3){
                avgDisCam2TargetArray += disCam2TargetArray[i];
            }
            avgDisCam2TargetArray = avgDisCam2TargetArray / (disCam2TargetArray.length/3);
            //double[] cr = calRotation(img, ppm, disCam2TargetArray[disCam2TargetArray.length - 1]);
            double[] cr = calRotation(img, ppm, avgDisCam2TargetArray, direction);

            Quaternion quaterniontF = new Quaternion((float) fr[0], (float) fr[1], (float) fr[2], (float) fr[3]);
            Quaternion quaterniontC = new Quaternion((float) cr[0], (float) cr[1], (float) cr[2], (float) cr[3]);

            System.out.print("Q1 ");
            System.out.print(cr[0]);
            System.out.print(" ");
            System.out.print(cr[1]);
            System.out.print(" ");
            System.out.print(cr[2]);
            System.out.print(" ");
            System.out.println(cr[3]);

            //api.moveTo(position, quaterniontF, true);
            //api.moveTo(position, quaterniontF, true);

            api.moveTo(position, quaterniontC, true);
            api.moveTo(position, quaterniontC, true);
        }
        else {
//            moveToPos(positionOptional, quaternionTarget2);
//            Mat imgO = api.getMatNavCam();
//            api.saveMatImage(img, "Target2Optional.png");
//            aim(imgO, positionOptional[positionOptional.length - 1]);
        }
    }

    static double[] calRotation(Mat img, double ppm, double A, int direction) {
        double cameraCenterx = img.width()/2; // camera width/2 (pixel)
        double cameraCentery = img.height()/2; // camera height/2 (pixel)
        double[] t2c = getTarget2Center(img);
        double targetPosx = t2c[0];
        double targetPosy = img.height()-t2c[1];

        double offsetX = -0.005f;
        double offsetY = 0.005f;

        double L = ( targetPosx - cameraCenterx ) / ppm + offsetX;
        double M = ( targetPosy - cameraCentery ) / ppm + offsetY;

        double lowestDiff = 1e9;
        double diff;
        double yaw = 0f;
        double pitch = 0f;
        double roll = 0f;

        for(double y = -45.00f;y <= 45.00f;y += 0.01f){
            diff = Math.abs(Math.tan(Math.toRadians(y)) + ((0.0994f-L)-(0.0572f-Math.cos(Math.toRadians(y))*0.0572f))/((A+0.1177f)+(Math.sin(Math.toRadians(y))*0.0572f)));
            if(diff < lowestDiff){
                lowestDiff = diff;
                yaw = y;
            }
        }

        lowestDiff = 1e9;
        for(double p = -45.00f;p <= 45.00f;p += 0.01f){
            diff = Math.abs(Math.tan(Math.toRadians(p)) + ((0.0285f-M)-(0.1111f-Math.cos(Math.toRadians(p))*0.1111f))/((A+0.1177f)+(Math.sin(Math.toRadians(p))*0.1111f)));
            if(diff < lowestDiff){
                lowestDiff = diff;
                pitch = p;
            }
        }

        //// debug
        System.out.print("ppm ");
        System.out.println(ppm);
        System.out.print("Cam x ");
        System.out.println(cameraCenterx);
        System.out.print("Cam y ");
        System.out.println(cameraCentery);
        System.out.print("Target x ");
        System.out.println(targetPosx);
        System.out.print("Target y ");
        System.out.println(targetPosy);
        System.out.print("A ");
        System.out.println(A);
        System.out.print("L ");
        System.out.println(L);
        System.out.print("M ");
        System.out.println(M);
        System.out.print("Yaw offset ");
        System.out.println(yaw);
        System.out.print("Pitch offset ");
        System.out.println(pitch);
        System.out.print("Direction ");
        System.out.println(direction);

        if(direction == 0) yaw = yaw+0;
        if(direction == 1) yaw = yaw+270;
        if(direction == 2) yaw = yaw+180;
        if(direction == 3) yaw = yaw+90;
        if(direction == 4){
            pitch = pitch+90;
            roll = yaw*3.5f;
            yaw = 0f;
        }
        if(direction == 6){
            pitch = pitch+90;
            roll = yaw*2f;
            yaw = 0f;
        }
        if(direction == 5){
            pitch = pitch-90;
            roll = yaw*3.5f;
            yaw = 0f;
        }

        System.out.print("Yaw ");
        System.out.println(yaw);
        System.out.print("Pitch ");
        System.out.println(pitch);
        System.out.print("Roll ");
        System.out.println(roll);

        return pyrToQ(pitch, roll, yaw);
    }

    static double[] getTarget2Center(Mat img) {
        Imgproc.blur(img, img, new Size(5, 5));
        Mat edges = new Mat();
        Imgproc.Canny(img, edges, 100, 200);

        Mat circles = new Mat();

        Imgproc.HoughCircles(edges, circles, Imgproc.CV_HOUGH_GRADIENT, 1, 1000, 100, 30, 10, 100 );
        if(circles.empty()) return new double[] {img.width()/2, img.height()/2};
        double[] temp = circles.get(0, 0);

        return temp;
    }

    private void delay(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        };
    }

    private void moveToPos(Point[] position, Quaternion quaternion){
        int loopCounter = 0;
        Result result;
        for (Point p : position) {
            result = api.moveTo(p, quaternion, true);
            loopCounter = 0;
            while(!result.hasSucceeded() && loopCounter < LOOP_MAX){
                result = api.moveTo(p, quaternion, true);
                ++loopCounter;
            }
        }
    }

    static double[] pyrToQ(double pitch, double roll, double yaw){
        double cy = Math.cos(Math.toRadians(yaw * 0.5));
        double sy = Math.sin(Math.toRadians(yaw * 0.5));
        double cp = Math.cos(Math.toRadians(pitch * 0.5));
        double sp = Math.sin(Math.toRadians(pitch * 0.5));
        double cr = Math.cos(Math.toRadians(roll * 0.5));
        double sr = Math.sin(Math.toRadians(roll * 0.5));

        double[] result = {sr * cp * cy - cr * sp * sy, cr * sp * cy + sr * cp * sy, cr * cp * sy - sr * sp * cy, cr * cp * cy + sr * sp * sy};
        return result;
    }

    // You can add your method
    private String scanQR(Mat img){
        QRCodeDetector decoder = new QRCodeDetector();
        Mat points = new Mat();
        String data = decoder.detectAndDecode(img, points);
        String ans = "n/a";
        if(data.equals("JEM")) ans = "STAY_AT_JEM";
        if(data.equals("COLUMBUS")) ans = "GO_TO_COLUMBUS";
        if(data.equals("RACK1")) ans = "CHECK_RACK_1";
        if(data.equals("ASTROBEE")) ans = "I_AM_HERE";
        if(data.equals("INTBALL")) ans = "LOOKING_FORWARD_TO_SEE_YOU";
        if(data.equals("BLANK")) ans = "NO_PROBLEM";

        System.out.print("QR Data = ");
        System.out.println(data);
        System.out.println(ans);

        return ans;
    }
}
