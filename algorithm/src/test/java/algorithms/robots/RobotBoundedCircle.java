package algorithms.robots;

import org.testng.annotations.Test;

/*
https://leetcode.com/problems/robot-bounded-in-circle/
On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three instructions:

"G": go straight 1 unit;
"L": turn 90 degrees to the left;
"R": turn 90 degrees to the right.
The robot performs the instructions given in order, and repeats them forever.

Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
 */
public class RobotBoundedCircle {
    @Test
    public boolean isRobotBounded(String instructions) {
        //north =0, east = 1, south = 2, west = 3
        int[][] directions = new int[][] {{0,1},{1,0},{0,-1},{-1,0}};

        int x=0, y=0;
        int idx = 0;

        for(char i: instructions.toCharArray()){
            if(i == 'L'){
                idx = (idx + 3) % 4;
            }else if( i == 'R'){
                idx = (idx + 1) % 4;
            }else{
                x += directions[idx][0];
                y += directions[idx][1];
            }
        }

        return (x == 0 && y ==0)  || idx != 0;
    }
}
