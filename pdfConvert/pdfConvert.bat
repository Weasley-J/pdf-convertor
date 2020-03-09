@echo off
setlocal enabledelayedexpansion
call :lwj & goto lwjStart
::-----------------------------------------------------------
PDFconvert: Can convert PDF file to TXT,DOC,XLS,HTML) format.

Usage:
pdfconvert.py <filename.pdf> [filename|.txt|.doc|.xls|.html]
<filename.pdf>                    To convert the PDF file, it's necessary to input.
[filename.txt|.doc|.xls|.html]     Need to convert to the specified format of the file,
Support TXT, DOC, XLS, HTML files, If not input,Then the default convert TXT file.
General Options:
-h      Show help info.
-e      Extract the images from PDF file.
Usage:pdfconvert.py <filename.pdf> -e <extract path>

Merge pdf files:
Usage:pdfconvert.py [Pdf1] [pdf2] <pdf3> ... -o [Merge pdf]
::-----------------------------------------------------------
:lwj
:: set "filename=����FPGA+ARM������ʵʱ���ϵͳ�о�.pdf"
:: set "filename=����FPGA+ARM������ʵʱ���ϵͳ�о�"

:: set "filename=sourceDir\����1512-151003400230-��ҵ����-�ո�.pdf"
:: set "destName=����1512-151003400230-��ҵ����-�ո�"

:: set "filename=Merged_X4.pdf"
:: set "destName=Merged_X4"

set "filename=���ѧϰ���İ�v0.5.pdf"
set "destName=���ѧϰ���İ�v0.5"

set "pdf1=���ѧϰ���İ�v0.5x1.pdf"
set "pdf2=���ѧϰ���İ�v0.5x2.pdf"
set "pdf3=���ѧϰ���İ�v0.5x3.pdf"

set "pdfconvert=Modules\python\pdfconvert"
set "python=Modules\python\python.exe"
set "ebookConvert=Modules\EBookCodec\ebook-convert.exe"
set "pdf2htmlEX=Modules\Encoder\Doc\PDF\pdf2htmlEX.exe"
set "src=.\"
exit /b

:lwjStart
call :delTmpFile
:: %pdf2htmlEX% --hdpi 140 --vdpi 140 "%src%\%filename%" "%out%\%htmlName%"
%pdf2htmlEX% --hdpi 144 --vdpi 144 "%src%\%filename%" "%out%\%htmlName%"
:: %pdf2htmlEX% --hdpi 140 --vdpi 140 --dest-dir "%outDir%" "%src%\%filename%" "%htmlName%"
:: %pdf2htmlEX% --hdpi 144 --vdpi 144 --dest-dir ".\" "%filename%" "%destName%.html"
pause
echo.
%ebookConvert% "%filename%" "%destName%.epub"
%ebookConvert% "%filename%" "%destName%.mobi"
%ebookConvert% "%filename%" "%destName%.azw3"
echo.
%python% %pdfconvert% %pdf1% %pdf2% %pdf3% -o "Merged_X3.pdf"
md "%destName%.PicOfPDF" >nul 2>nul
%python% %pdfconvert% "%filename%" -e "%destName%.PicOfPDF"
:lwjStart1
%python% %pdfconvert% "%filename%" "%destName%.doc"
%python% %pdfconvert% "%filename%" "%destName%.txt"
%python% %pdfconvert% "%filename%" "%destName%.xls"
:: %python% %pdfconvert% "%filename%" "%destName%.html"
echo.
pause & exit

:delTmpFile
del "%destName%.epub" >nul 2>nul
del "%destName%.mobi" >nul 2>nul
del "%destName%.azw3" >nul 2>nul
del "%destName%.txt" >nul 2>nul
del "%destName%.doc" >nul 2>nul
del "%destName%.xls" >nul 2>nul
del "%destName%.html" >nul 2>nul
rd /s /q "%destName%.PicOfPDF" >nul 2>nul
del "pdf1_pdf2_pdf3_Merged.pdf" >nul 2>nul
exit /b