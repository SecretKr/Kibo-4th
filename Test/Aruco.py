import numpy as np
import cv2
from time import sleep

img = cv2.imread("target7.png")

cameraMatrix = [
    [523.105750, 0.000000, 635.434258],
    [0.000000, 534.765913, 500.335102],
    [0.000000, 0.000000, 1.000000]
]
distCoeffs = [ -0.164787, 0.020375, -0.001572, -0.000369, 0.000000 ]

row = 0
col = 0

detect = cv2.QRCodeDetector()
value, points, straight_qrcode = detect.detectAndDecode(img)
print(value)

arucoDict = cv2.aruco.getPredefinedDictionary(cv2.aruco.DICT_5X5_250)
arucoParams = cv2.aruco.DetectorParameters()
(corners, ids, rejected) = cv2.aruco.detectMarkers(img, arucoDict,parameters=arucoParams)

if ids is not None:
    # Print corners and ids to the console
    for i, corner in zip(ids, corners):
        print('ID: {}; Corners: {}'.format(i, corner))

    # Outline all of the markers detected in our image
    img = cv2.aruco.drawDetectedMarkers(img, corners, borderColor=(0, 0, 255))


cimg = cv2.blur(img, (4,4));
cimg = cv2.Canny(cimg, 100, 200);
circles = cv2.HoughCircles(cimg, cv2.HOUGH_GRADIENT, 1, 1000, param1=100, param2=30, minRadius=10, maxRadius=75);
#circles = cv2.HoughCircles(cimg, cv2.HOUGH_GRADIENT, 1, 100, param1=p1, param2=p2, minRadius=10, maxRadius=400);
circles = np.uint16(np.around(circles))

for i in circles[0,:]:
    # draw the outer circle
    img = cv2.circle(img,(i[0],i[1]),i[2],(0,255,0),2)
    # draw the center of the circle
    img = cv2.circle(img,(i[0],i[1]),2,(0,0,255),3)
    #sleep(1)


cv2.imshow("",img)
cv2.waitKey(0)



# params = cv2.SimpleBlobDetector_Params()
# # Set the threshold
# params.minThreshold = 10
# params.maxThreshold = 200
# # Set the area filter
# params.filterByArea = True
# params.minArea = 1000
# params.maxArea = 10000
# # Set the circularity filter
# params.filterByCircularity = True
# params.minCircularity = 0.1
# params.maxCircularity = 1
# # Set the convexity filter
# params.filterByConvexity = True
# params.minConvexity = 0.87
# params.maxConvexity = 1
# # Set the inertia filter
# params.filterByInertia = True
# params.minInertiaRatio = 0.01
# params.maxInertiaRatio = 1
# detector = cv2.SimpleBlobDetector_create(params)
 
# # Detect blobs.
# keypoints = detector.detect(cimg)
 
# # Draw detected blobs as red circles.
# # cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS ensures the size of the circle corresponds to the size of blob
# im_with_keypoints = cv2.drawKeypoints(img, keypoints, np.array([]), (0,255,0), cv2.DRAW_MATCHES_FLAGS_DRAW_RICH_KEYPOINTS)
 
# Show keypoints
# cv2.imshow("Keypoints", im_with_keypoints)
# cv2.waitKey(0)
# cv2.namedWindow('image')
# def nothing(x):
#   pass

# cv2.createTrackbar('p1','image',1,255,nothing)
# cv2.createTrackbar('p2','image',1,255,nothing)
# cv2.createTrackbar('p3','image',1,255,nothing)
# cv2.createTrackbar('p4','image',1,255,nothing)
# ogimg = img
# while(1):
#     cv2.imshow('image',img)
#     k = cv2.waitKey(1) & 0xFF
#     if k == 27:
#         break
 
#     p1 = cv2.getTrackbarPos('p1','image')
#     p2 = cv2.getTrackbarPos('p2','image')
#     p3 = cv2.getTrackbarPos('p3','image')
#     p4 = cv2.getTrackbarPos('p4','image')
#     print(p1,p2)
#     cv2.waitKey(0)
#     img = ogimg