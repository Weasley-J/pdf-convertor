package cn.alphahub.pdfconvertor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PDFConverter {

    public static final String outDir = "pdfConvert\\outputDir";
    private static final String pythonEX = "pdfConvert\\Modules\\python\\python.exe";
    private static final String pdfconvert = "pdfConvert\\Modules\\python\\pdfconvert";
    private static final String ebookConvert = "pdfConvert\\Modules\\EBookCodec\\ebook-convert.exe";
    private static final String pdf2htmlEX = "pdfConvert\\Modules\\Encoder\\Doc\\PDF\\pdf2htmlEX.exe";

    public static boolean pdf2doc(String filepathAndFilename) {
        return getPythonConvertPathAndNameString(filepathAndFilename, ".doc");
    }

    public static boolean pdf2txt(String filepathAndFilename) {
        return getPythonConvertPathAndNameString(filepathAndFilename, ".txt");
    }

    public static boolean pdf2xls(String filepathAndFilename) {
        return getPythonConvertPathAndNameString(filepathAndFilename, ".xls");
    }

    // Executable EXE
    public static boolean pdf2html(String filepathAndFilename) {
        makeDirectory(outDir);
        String sourcePath = getFilepathAndFilenameArray(filepathAndFilename).get(0);
        String filename = getFilepathAndFilenameArray(filepathAndFilename).get(1);
        String srcName = sourcePath + filename;
        String htmlName = filename.substring(0, filename.length() - 4) + ".html";
        String destName = outDir + "\\" + htmlName;
        String[] command = getEbookCommandCommandArrayStrings(srcName, htmlName, destName, outDir);
        runCommandActions2(filename, destName, command[new Random().nextInt(command.length)]);
        return convertResults(destName);
    }

    public static boolean pdfMerge(List<String> ListsOfPDFFiles) {
        makeDirectory(outDir);
        String tempSrcName = "";
        for (String list : ListsOfPDFFiles
        ) {
            tempSrcName += list + " ";
        }
        int index = new Random().nextInt(ListsOfPDFFiles.size());
        String mergedPdfRandomName = ListsOfPDFFiles.get(index).substring(ListsOfPDFFiles.get(index).lastIndexOf("\\") + 1);
        String srcName = tempSrcName.substring(0, tempSrcName.length() - 1);
        String timeStrOfDestFile = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String destName = outDir + "\\" + mergedPdfRandomName.substring(0, mergedPdfRandomName.length() - 4) + "-pdf合并(" + timeStrOfDestFile + ").pdf";
        String command = "cmd /c \"" + pythonEX + " " + pdfconvert + " " + srcName + " -o " + destName + "\"";
        runCommandActions2(srcName, destName, command);
        return convertResults(destName);
    }

    public static boolean splitPictureIntoFolder(String filepathAndFilename) {
        makeDirectory(outDir);
        String sourcePath = getFilepathAndFilenameArray(filepathAndFilename).get(0);
        String filename = getFilepathAndFilenameArray(filepathAndFilename).get(1);
        String srcName = sourcePath + filename;
        String FolderOfPdfSplitPictures = filename.substring(0, filename.length() - 4) + ".PicOfPDF";
        String destName = outDir + "\\" + FolderOfPdfSplitPictures;
        makeDirectory(destName);
        String command = "cmd /c \"" + pythonEX + " " + pdfconvert + " " + srcName + " -e " + destName + "\"";
        runCommandActions2(filename, destName, command);
        return convertResults(destName);
    }

    public static boolean pdf2epub(String filepathAndFilename) {
        return getPathAndNameString(filepathAndFilename, ".epub");
    }

    public static boolean pdf2mobi(String filepathAndFilename) {
        return getPathAndNameString(filepathAndFilename, ".mobi");
    }

    public static boolean pdf2azw3(String filepathAndFilename) {
        return getPathAndNameString(filepathAndFilename, ".azw3");
    }

    private static boolean getPathAndNameString(String filepathAndFilename, String fileSuffix) {
        makeDirectory(outDir);
        String sourcePath = getFilepathAndFilenameArray(filepathAndFilename).get(0);
        String filename = getFilepathAndFilenameArray(filepathAndFilename).get(1);
        String srcName = sourcePath + filename;
        String destName = outDir + "\\" + filename.substring(0, filename.length() - 4) + fileSuffix;
        runEbookCommand(filename, srcName, destName);
        return convertResults(destName);
    }

    private static boolean getPythonConvertPathAndNameString(String filepathAndFilename, String fileSuffix) {
        makeDirectory(outDir);
        String sourcePath = getFilepathAndFilenameArray(filepathAndFilename).get(0);
        String filename = getFilepathAndFilenameArray(filepathAndFilename).get(1);
        String srcName = sourcePath + filename;
        String destName = outDir + "\\" + filename.substring(0, filename.length() - 4) + fileSuffix;
        runCommandActions(filename, srcName, destName);
        return convertResults(destName);
    }

    private static void runEbookCommand(String filename, String srcName, String destName) {
        clearExistsCache(destName);
        new CommandUtil().executeCommand(ebookCommand(srcName, destName));
        printConvertResults(filename, destName);
    }

    private static void runCommandActions(String filename, String srcName, String destName) {
        clearExistsCache(destName);
        new CommandUtil().executeCommand(command(srcName, destName));
        printConvertResults(filename, destName);
    }

    private static void runCommandActions2(String filename, String destName, String command) {
        clearExistsCache(destName);
        new CommandUtil().executeCommand(command);
        printConvertResults(filename, destName);
    }

    private static boolean printConvertResults(String filename, String destName) {
        if (!(new File(destName).exists())) {
            System.out.println("Sorry: [" + filename + " " + destName + "]" + " does not exist. no such file!");
            return false;
        }
        if (new File(destName).exists() && new File(destName).isFile()) {
            System.out.println("[" + filename + "] Converted to [" + destName + "]");
            return true;
        }
        if (new File(destName).isDirectory()) {
            System.out.println("Picture in [" + filename + "] has split to Directory: [" + destName + "]");
            return true;
        } else System.out.println("\nThe file type is not supported\n"
                + "\t\t\tOR\n"
                + "Files of Picture convert to pdf not supported pdf convert to doc!"
        );
        return false;
    }

    private static boolean convertResults(String destName) {
        if (new File(destName).exists() && new File(destName).isFile()) return true;
        if (new File(destName).isDirectory()) return true;
        if (!(new File(destName).exists())) return false;
        return false;
    }

    private static String command(String srcName, String destName) {
        return "cmd /c \"" + pythonEX + " " + pdfconvert + " " + srcName + " " + destName + "\"";
    }

    private static String ebookCommand(String srcName, String destName) {
        return "cmd /c \"" + ebookConvert + " " + srcName + " " + destName + "\"";
    }

    private static String[] getEbookCommandCommandArrayStrings(String srcName, String htmlName, String destName, String outputDirectory) {
        return new String[]{
                "cmd /c \"" + pdf2htmlEX + " --hdpi 144 --vdpi 144 " + srcName + " " + destName + "\"",
                "cmd /c \"" + pdf2htmlEX + " --hdpi 144 --vdpi 144 --dest-dir " + outputDirectory + " " + srcName + " " + htmlName + "\""
        };
    }

    private static List<String> getFilepathAndFilenameArray(String filepathAndFilename) {
        List<String> list = new ArrayList<String>() {
            private static final long serialVersionUID = 1L;
        };
        File tempFile = new File(filepathAndFilename);
        String filepath = tempFile.getPath().trim().substring(0, tempFile.getPath().lastIndexOf("\\") + 1);
        String filename = tempFile.getName();
        list.add(filepath);
        list.add(filename);
        return list;
    }

    private static void clearExistsCache(String filesPathAndFilesName) {
        File file = new File(filesPathAndFilesName);
        if (file.exists() && file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) f.delete();
            return;
        }
    }

    private static void makeDirectory(String outDir) {
        File PDFOutDir = new File(outDir);
        if (!PDFOutDir.exists()) {
            PDFOutDir.mkdir();
            return;
        }
    }

}
