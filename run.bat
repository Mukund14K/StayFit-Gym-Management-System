@echo off
title StayFit Gym Management System Runner
echo ===================================================
echo  Compiling StayFit Gym Management System...
echo ===================================================

:: Step 1: Compile the Java files with the driver path linked
javac -cp "lib/mysql-connector-j-8.0.33.jar" GymApp.java Member.java DatabaseConnection.java

:: Check if compile was successful
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed! Please check your Java code for errors.
    pause
    exit /b %errorlevel%
)

echo.
echo ===================================================
echo  Launching Application...
echo ===================================================

:: Step 2: Launch the application compiled bytecode package
java -cp ".;lib/mysql-connector-j-8.0.33.jar" GymApp

pause
