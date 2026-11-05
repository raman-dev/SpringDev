@rem check if file exists if not exit program print shit don't exist 
@REM  ~downloads/project-name move to documents
@REM  if already exists then overwrite
move /y "%USERPROFILE%\downloads\project-bolt-sb1-p3htpesb" "%USERPROFILE%"\documents
@REM   extract to container folder named project-name-extract
@REM   root of extraction should be name ActivityTrackerFrontend
@REM   ~documents/project-name-extract to here
@REM        overwrite current ActivityTrackerFrontend folder 
