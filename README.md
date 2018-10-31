# 3x3x3-Rubiks-Cube-Solver

This project took around 7 months to get to fully working.
I developed the solving algorithm for the cube which gets you an average of around 25 move solution.
The goal was to write a solver that could solve the cube in less than 28 moves every time.
The image processing took around a month to get working and can be improved for better detection.

## System requirements: 
  Linux, Mac, or Windows  
  Java 1.6 and higher  
  IDE for Java Eclipse Luna or higher 
  1GB of disk space  

## How to install OpenCV in Eclipse

1. Navigate to [OpenCV](https://sourceforge.net/projects/opencvlibrary/)
2. Click download 
3. Extract it  
  If **Linux**:  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Navigate in Terminal to where you extacted it and run this `$ mkdir build && cd build && cmake .. && make`  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This will build OpenCV so that you can use it. This will take a while... 10-20 minutes to complete building   
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Once complete jump move to step 4.  
  If **Windows**:  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; OpenCV the file where you downloaded OpenCV run the `.exe`.  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You should see a **build** folder  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; If this file is there you can move to step 4.        
  If **Mac**:   
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I found this [tutorial](https://www.youtube.com/watch?v=U49CVY8yOxw) on how to install OpenCV on mac and it worked for me.  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Once complete you should end up with a **build** folder   
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You can now move to step 4.  

4. Run Eclipse  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Look for **Windows** in the toolbar on the top of Eclipse then choose **Preferences**   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Scroll down to **Java** then click **User Libraries**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; As shown. NOTE you shouldn't see *opencv-3.3.0* We are going to make that. Also *JavaCV* is irrelevant.  
   ![here](https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver/blob/master/images%20for%20readMe/preferences.png?raw=true)  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Then press **New** and name it *OpenCV-3.3.0* or something like that.   
5. Add OpenCV jar to User Library  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; After pressing **New** make sure you have the library you created selected.  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Then press **Add External Jars**  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Then navigate into your OpenCV folder to the `build/bin/` folder.  
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; You will find `opencv-330.jar`. Select it and add it to your library  
    
    It your library should look like ![this](https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver/blob/master/images%20for%20readMe/Jar%20Selection.png?raw=true "added user library")
    
6. Add Native Library Location  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Now we have to add the native OpenCV libraries to our OpenCV library.  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Click on Native Libary location and the libaries are located it in your dowloaded OpenCV folder in `/build/lib`   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Congrats you now have OpenCV configured and ready!  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; It should look something like ![this](https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver/blob/master/images%20for%20readMe/nativeLibraryLocation.png?raw=true)    
    
## Clone and Setup the Project
  Make sure you have Eclipse open.  
  We are going to clone the github repo inside Eclipse.  
    
1. Clone the repo:  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Right-click inside **Package Explorer**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Select **Import**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Click the **Git** folder  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Then click **Projects from Git**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Click **Clone URI**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Enter the **URI** : https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver.git  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Make sure you are signed in with GitHub  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Press **Next** until you see 3 options listing:  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ..1. Import Existing Eclipse Projects  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Make sure this is selected and press **Finish**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This will build the project.  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; This could take a minute or two finish building  
    
2. Add your OpenCV library:  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Right-click on the project name  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Select **Build Path** and select **Add Libraries**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Then click **User Library**  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Select your OpenCV library  
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Click **Finish**
  


## Running the program to solve your rubik's cube.

You are now ready to run the program. When running the program make sure to be running `MainFrame.java` 
You can now click **Run** and a window showing the camera will pop up. 
This is crucial. When running the program good lighting is helps success a lot. 
Avoid having lighting changes between pictures. 

To take a picture of a side press `SPACE`.   
To end the program press `X`.   
When you scan the cube you follow this order.  
**TOP, LEFT, FRONT, RIGHT, BACK BOTTOM**   
Here is a sample video showing how it should be done.  

[![Youtube video](https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver/blob/master/images%20for%20readMe/thumbnail.png?raw=true)](https://youtu.be/afAGtExoiLQ "Click to watch video!" )

If you follow the video and track how I move the cube to take photos of it in the video exactly you will get a solution to your cube.
If you get a successful solution you will see this printed in the console:  

     your cube: 

           OYG
           WYO
           YWG
       YBB RRR WWW OOB
       GBR BRB YGR YOR
       RBW BGO BOY OGG
           RWW
           OWG
           YYG
      Calculating...
           WWW
           WWW
           WWW
       OOO GGG RRR BBB
       OOO GGG RRR BBB
       OOO GGG RRR BBB
           YYY
           YYY
           YYY
      Your solution :) 
      R2 U' R B2 U' R2 U' B R2 U' L U R2 U' D L D B D B' D' L2 F2 L F2 R' D2 R 
      Number of moves: 28


On top is your scrambled cube and make sure you are holding it in that orientation before you try applying the solution

The top of the flat cube is the first 3 rows of the cube. That is the **TOP**.  
    
     OYG
     WYO
     YWG 
 
The left side of the cube is most left 3 rows in the middle chunk. That is the **LEFT**

     YBB
     GBR
     RBW 

The front side is: 

     RRR
     BRB
     BGO 
     
And so on. 

Here is an image showing the correct orientation.  
![cube image example](https://github.com/HaginCodes/3x3x3-Rubiks-Cube-Solver/blob/master/images%20for%20readMe/explain%20sides.jpg?raw=true)  

It matches the cube printed in the console

         OYG
         WYO
         YWG
     YBB RRR WWW OOB
     GBR BRB YGR YOR
     RBW BGO BOY OGG
         RWW
         OWG
         YYG

New to cube Notation? Don't understand what this `R2 U' R B2 U' R2 U'` means?
[This website](https://ruwix.com/the-rubiks-cube/notation/) explains it very well.

## Conclusion
Congrats! You now hopefully have been able to solve your Rubik's cube!  
If you are having difficulties to let me know and I will be happy to help out.

### Want to contribute to the project? 
Feel free to add on to this solver, help with the success rate of the image processing, make the GUI better. I am willing to work with you.

Special thanks to Daniel Walton for inspiring me to create the image processing part and helping me understand how to go about it and to David Gilday, who helped me understand the concepts in writing my own solving algorithm and the source of my inspiration to do this.
