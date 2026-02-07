# TicTacToe AI (Minimax Algorithm)

An unbeatable Tic-Tac-Toe AI implemented in Java using the Minimax algorithm and recursion.

## Overview
This project is a console-based Tic-Tac-Toe game where a human player competes against an AI opponent.  
The AI evaluates all possible future game states using recursive Minimax logic to always choose the optimal move.

## Features
- Unbeatable AI opponent using the Minimax algorithm
- Recursive game state evaluation
- Object-oriented design with abstract player classes
- Clear separation between game logic, AI logic, and user input
- Runs entirely in the console

## How the AI Works
The AI uses the Minimax algorithm to simulate every possible move:
- Winning positions are scored as `+1`
- Losing positions are scored as `-1`
- Draws are scored as `0`

The AI assumes the opponent always plays optimally and chooses the move that maximizes its guaranteed outcome.

## Technologies Used
- Java
- Recursion
- Object-Oriented Programming

## How to Run
1. Compile the program: ```bash
   javac TicTacToeAI.java
   ```bash
   javac TicTacToeAI.java
