@echo off
prompt $g

@rem define project name and path
set fname=project-bolt-sb1-p3htpesb.zip
set old_path=%USERPROFILE%\Downloads\%fname%

@rem check project file exists
if not exist %old_path% (
    echo %fname% file not found.
    prompt
    exit /b
)
echo Found project file:%fname%


echo Moving %fname% to Documents
move /y %old_path% "%USERPROFILE%\Documents\%fname%"

set zip_fpath=%USERPROFILE%\Documents\%fname%
set unzip_fpath=%USERPROFILE%\Documents\ATFTemp
@REM extract to container folder named ATFTemp overwrite current if there

echo Extracting %fname% to: %unzip_fpath%
winrar x -y -o+ %zip_fpath% %unzip_fpath%\
 
@rem ATFTemp contains folder named project with all relevant files rename too ActivityTrackerFrontend
@rem Don't use full path when specifiying new name
rename %unzip_fpath%\project ActivityTrackerFrontend
echo Renamed %unzip_fpath%\project to %unzip_fpath%\ActivityTrackerFrontend

if exist %USERPROFILE%\Documents\ActivityTrackerFrontend (
	@rem Remove old folder if its there
	rmdir /s /q %USERPROFILE%\Documents\ActivityTrackerFrontend
)
move /y %unzip_fpath%\ActivityTrackerFrontend %USERPROFILE%\Documents
echo Moving projects files to %USERPROFILE%\Documents\ActivityTrackerFrontend
 
rmdir /s /q %unzip_fpath%
echo Removed %USERPROFILE%\Documents\ATFTemp folder
 
@REM write contents of USERPROFILE\Documents\ActivityTrackerFrontend to ActivityTrackerFrontend
echo Copying ActivityTrackerFrontend to main project folder.
copy /y %USERPROFILE%\Documents\ActivityTrackerFrontend\* ActivityTrackerFrontend

prompt
@echo on
 
