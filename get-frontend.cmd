<<<<<<< HEAD
@echo off
prompt $g

@rem define project name and path
set fname=project-bolt-sb1-p3htpesb.zip
set fpath="%USERPROFILE%\Downloads\%fname%"

@rem check project file exists
if not exist %fpath% (
    echo %fname% file not found.
    prompt
    exit /b
)

echo Found project file:%fname%
echo Moving to Documents
move /y %fpath% "%USERPROFILE%\Documents\%fname%"

set zip_fpath="%USERPROFILE%\Documents\%fname%"
set unzip_fpath="%USERPROFILE%\Documents\ATFTemp"
@REM   extract to container folder named ActivityTrackerFrontendExtract overwrite current if there

echo Extracting to: %unzip_fpath%
winrar x -y -o+ %zip_fpath% %unzip_fpath%\

ren %unzip_fpath%\project ActivityTrackerFrontend
move %unzip_fpath%\ActivityTrackerFrontend %USERPROFILE%\Documents

rmdir %unzip_fpath%
echo Created ActivityTrackerFrontend in Documents
@REM write contents of USERPROFILE\Documents\ActivityTrackerFrontend to ActivityTrackerFrontend

copy /y %USERPROFILE%\Documents\ActivityTrackerFrontend\* ActivityTrackerFrontend

prompt
echo on
=======
@rem check if file exists if not exit program print shit don't exist 
@REM  ~downloads/project-name move to documents
@REM  if already exists then overwrite
move /y "%USERPROFILE%\downloads\project-bolt-sb1-p3htpesb" "%USERPROFILE%"\documents
@REM   extract to container folder named project-name-extract
@REM   root of extraction should be name ActivityTrackerFrontend
@REM   ~documents/project-name-extract to here
@REM        overwrite current ActivityTrackerFrontend folder 