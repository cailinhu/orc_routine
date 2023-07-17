/**
  * Copyright 2023 bejson.com
  */
package com.clh.bean;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-generated: 2023-02-23 11:40:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OrcResult {

    public static OrcResult builderExceptionResult(){
        WordsResult wordsResult=  new WordsResult();
        wordsResult.setWords("配置文件异常!请检查config目录下的ocr_config.properties文件");
        OrcResult result =new OrcResult();
        result.setWords_result(Arrays.asList(wordsResult));
        return result;
    }

    private List<WordsResult> words_result;
    private int wordsResult_num;
    private long log_id;

    public List<WordsResult> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResult> words_result) {
        this.words_result = words_result;
    }

    public int getWordsResult_num() {
        return wordsResult_num;
    }

    public void setWordsResult_num(int wordsResult_num) {
        this.wordsResult_num = wordsResult_num;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }
}
