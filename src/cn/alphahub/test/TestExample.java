package cn.alphahub.test;

import cn.alphahub.pdfconvertor.PDFConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestExample {
	public static void main(String[] args) {

        // M2 实例化List
        List<String> list1 = new ArrayList<String>();
        // 添加元素  
//        list1.add("pdfConvert\\sourceDir\\Effective1.pdf");
//        list1.add("pdfConvert\\sourceDir\\Effective2.pdf");
//        list1.add("pdfConvert\\sourceDir\\Effective3.pdf");
//        list1.add("pdfConvert\\sourceDir\\Effective4.pdf");
        list1.add("pdfConvert\\outputDir\\Effective2-pdf合并(20191231)-1.pdf");
        list1.add("pdfConvert\\sourceDir\\Effective5.pdf");
        
        PDFConverter.pdfMerge(list1);
        boolean pdfMergeResult_2 = PDFConverter.pdfMerge(list1);
        System.out.println(pdfMergeResult_2);

/*        
        File file = new File(".\\mywork\\work.txt");
        if (!file.exists()) {
            System.out.println(file.getParentFile() + "\\");// 获取文件路径
            System.out.println(file.getName());// 获取文名.后缀
        }
       
        PDFConverter.splitPictureIntoFolder("pdfConvert\\刘雯静-1寸证件照.pdf");

        PDFConverter.splitPictureIntoFolder("D:\\QQ文件\\1432689025\\FileRecv\\MobileFile\\身份证扫描(1).pdf");

        PDFConverter.splitPictureIntoFolder("pdfConvert\\sourceDir\\电子1512-151003400230-毕业论文-终稿.pdf");
        PDFConverter.pdf2doc("pdfConvert\\sourceDir\\电子1512-151003400230-毕业论文-终稿.pdf");
        boolean pdf2htmlResult = PDFConverter.pdf2html("pdfConvert\\sourceDir\\电子1512-151003400230-毕业论文-终稿.pdf");
        System.out.println(pdf2htmlResult);
     
        // 多个 PDF 合并 成一个 PDF
        // M1 实例化直接赋值
        List<String> pdfPathName = Arrays.asList("pdfConvert\\sourceDir\\电子1512-151003400230-毕业论文-终稿.pdf",
                "pdfConvert\\刘雯静(2018-2019-2)课表.pdf", "pdfConvert\\我的报名号：311299213请牢记！.pdf",
                "pdfConvert\\PYNQ-Z2入门指导手册_v2.0.pdf");
        boolean pdfMergeResult_1 = PDFConverter.pdfMerge(pdfPathName);
        System.out.println(pdfMergeResult_1);

        // M2 实例化List
        List<String> list = new ArrayList<String>();
        // 添加元素
        list.add("pdfConvert\\sourceDir\\电子1512-151003400230-毕业论文-终稿.pdf");
        list.add("pdfConvert\\刘雯静(2018-2019-2)课表.pdf");
        list.add("pdfConvert\\我的报名号：311299213请牢记！.pdf");
        list.add("pdfConvert\\PYNQ-Z2入门指导手册_v2.0.pdf");
        list.add("D:\\表格文档\\《毕业实习》单位落实电子15.pdf");
        boolean pdfMergeResult_2 = PDFConverter.pdfMerge(list);
        System.out.println(pdfMergeResult_2);
*/
        try {
            Runtime.getRuntime().exec("cmd /c taskkill /f /im explorer.exe >nul 2>nul & cmd /c start explorer.exe & cmd /c start explorer.exe " + PDFConverter.outDir);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        
    }

}
