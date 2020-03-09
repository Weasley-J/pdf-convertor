@echo off
setlocal enabledelayedexpansion
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' (goto UACPrompt) else ( goto gotAdmin )
:UACPrompt
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
exit /B
:gotAdmin
if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
pushd "%CD%"
CD /D "%~dp0"
set a=80
set b=32
mode con: cols=%a% lines=%b%
title pdf convert                                 日期: %date%    时间: %time%
call :Set_Variables & goto home
:home
cls
@echo off
ECHO.
ECHO.
ECHO.    文件列表: 
ECHO.
for /f "tokens=*" %%i in ('dir "%src%\*.pdf" /s/b') do (
	ECHO.        %%~nxi
	SET "pdfName=%%~nxi"
)
ECHO.
ECHO.    可选操作: 
ECHO.
ECHO.        1. *.pdf to *.doc
ECHO.
ECHO.        2. *.pdf to *.html
ECHO.
set /p HP_INPUT=-^-^>请输入(数字/字母)：
if "%HP_INPUT%"=="1" (goto pdf2doc)
if "%HP_INPUT%"=="2" (goto pdf2html)
goto home

:pdf2doc
for /f "tokens=*" %%i in ('dir "%src%\*.pdf" /b/a') do (
	SET "docName=%%~ni.doc"
)
ECHO.
ECHO.    文件名：%docName%
ECHO.
del "%out%\%docName%" >nul 2>nul
%python% %pdfconvert% "%src%\%pdfName%" "%out%\%docName%"
echo.
pause
if exist "%out%\%docName%" (
	%explorer% %out% & exit
) else goto home

:pdf2html
for /f "tokens=*" %%i in ('dir "%src%\*.pdf" /b/a') do (
	SET "htmlName=%%~ni.html"
)
ECHO.
ECHO.    文件名：%htmlName%
ECHO.
del "%out%\%htmlName%" >nul 2>nul
:: %pdf2htmlEX% --hdpi 140 --vdpi 140 "%src%\%pdfName%" "%out%\%htmlName%"
:: %pdf2htmlEX% --hdpi 144 --vdpi 144 "%src%\%pdfName%" "%out%\%htmlName%"
:: %pdf2htmlEX% --hdpi 140 --vdpi 140 --dest-dir "%out%" "%src%\%pdfName%" "%htmlName%"
%pdf2htmlEX% --hdpi 144 --vdpi 144 --dest-dir "%out%" "%src%\%pdfName%" "%htmlName%"
echo.
pause
if exist "%out%\%htmlName%" (
	%explorer% %out% & exit
) else goto home

:Set_Variables
set "pdf2html=pdf2html\pdf2htmlEX.exe"
set "python=Modules\python\python.exe"
set "explorer=explorer.exe"
set "pdf2htmlEX=Modules\Encoder\Doc\PDF\pdf2htmlEX.exe"
set "pdfconvert=Modules\python\pdfconvert"
set "src=sourceDir"
set "out=outputDir"
exit /b

