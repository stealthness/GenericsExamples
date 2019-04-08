# Robot Example

based on 
Genetic Algorithms in Java Basics
by Lee Jacobson, Burak Kanber

The Problem

The problem we are going to solve is designing a robotic controller that can use the robots sensors to navigate a robot successfully through a maze. The robot can take four actions: move one-step forward, turn left, turn right, or, rarely, do nothing. The robot also has six sensors: three on the front, one on the left, one on the right and one on the back.

The maze we are going to explore is comprised of walls that the robot can’t cross and will have an outlined route, below, which we want the robot to follow. Keep in mind that the purpose of this chapter isn’t to train a robot to solve mazes. Our purpose is to automatically program a robot controller with six sensors so that it doesn’t crash into walls; we’re simply using the maze as a complicated environment in which to test our robot controller.

[add image]

The robot’s sensors will activate whenever they detect a wall adjacent to the sensor. For example, the robot’s front sensor will activate if it detects a wall in front of the robot.

## Encoding

* “00”: do nothing
* “01”: move forward
* “10”: turn left
* “11”: turn right

