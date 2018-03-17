package test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void imgRegexTest(){
        String s= "<h1 style=\"font-size: 32px; font-weight: bold; border-bottom: 2px solid rgb(204, 204, 204); padding: 0px 4px 0px 0px; text-align: center; margin: 0px 0px 20px;\">各种美图<br/></h1><p style=\"text-align:center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813794049699.jpg\" title=\"1521190813794049699.jpg\"/></p><p style=\"text-align: center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813776087149.jpg\" title=\"1521190813776087149.jpg\"/></p><p style=\"text-align: center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813784010110.jpg\" title=\"1521190813784010110.jpg\"/></p><p style=\"text-align: center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813884014285.jpg\" title=\"1521190813884014285.jpg\"/></p><p style=\"text-align: center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813895048247.jpg\" title=\"1521190813895048247.jpg\"/></p><p style=\"text-align: center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813909074866.jpg\" title=\"1521190813909074866.jpg\"/></p><p style=\"text-align: center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813927048814.jpg\" title=\"1521190813927048814.jpg\"/></p><p style=\"text-align: center\"><img src=\"/ueditor/jsp/upload/image/20180316/1521190813936015887.jpg\" title=\"1521190813936015887.jpg\"/></p><p><br/></p>";

        String reg = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(s);
        List<String> list = new ArrayList<>();
        while(matcher.find()){
            String src = matcher.group();
            src = src.substring("src=\"".length(),src.length()-1);
            list.add(src);

        }
        System.out.println(list.size());
        for (String s1 : list) {
            System.out.println(s1);
        }
    }

    /*
    /ueditor/jsp/upload/image/20180316/1521190813794049699.jpg
/ueditor/jsp/upload/image/20180316/1521190813776087149.jpg
/ueditor/jsp/upload/image/20180316/1521190813784010110.jpg
/ueditor/jsp/upload/image/20180316/1521190813884014285.jpg
/ueditor/jsp/upload/image/20180316/1521190813895048247.jpg
/ueditor/jsp/upload/image/20180316/1521190813909074866.jpg
/ueditor/jsp/upload/image/20180316/1521190813927048814.jpg
/ueditor/jsp/upload/image/20180316/1521190813936015887.jpg
     */
    @Test
    public void ioTest() throws IOException {
        String realPath = "D:\\idea\\test\\blog\\out\\artifacts\\blog_war_exploded\\";
        FileUtils.copyFile(new File(realPath,"/ueditor/jsp/upload/image/20180316/1521190813936015887.jpg"),new File("f://ueditor/jsp/upload/image/20180316/1521190813936015887.jpg"));
    }

@Test
    public void dirCopyTest() throws IOException {
        String realPath = "D:\\idea\\test\\blog\\out\\artifacts\\blog_war_exploded\\";

        FileUtils.copyDirectory(new File(realPath,"/ueditor"),new File("f://ueditor"));
    }
}
