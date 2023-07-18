package com.clh.bean;
import java.util.List;
public class TranslateResult
{
    private String tSpeakUrl;

    private String requestId;

    private String query;

    private List<String> translation;

    private String errorCode;

    private String l;

    private boolean isWord;

    private String speakUrl;

    public void setTSpeakUrl(String tSpeakUrl){
        this.tSpeakUrl = tSpeakUrl;
    }
    public String getTSpeakUrl(){
        return this.tSpeakUrl;
    }
    public void setRequestId(String requestId){
        this.requestId = requestId;
    }
    public String getRequestId(){
        return this.requestId;
    }
    public void setQuery(String query){
        this.query = query;
    }
    public String getQuery(){
        return this.query;
    }
    public void setTranslation(List<String> translation){
        this.translation = translation;
    }
    public List<String> getTranslation(){
        return this.translation;
    }
    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return this.errorCode;
    }
    public void setL(String l){
        this.l = l;
    }
    public String getL(){
        return this.l;
    }
    public void setIsWord(boolean isWord){
        this.isWord = isWord;
    }
    public boolean getIsWord(){
        return this.isWord;
    }
    public void setSpeakUrl(String speakUrl){
        this.speakUrl = speakUrl;
    }
    public String getSpeakUrl(){
        return this.speakUrl;
    }
}
