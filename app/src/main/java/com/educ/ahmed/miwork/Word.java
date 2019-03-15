package com.educ.ahmed.miwork;

/**
 * Word class is used to modify and customize the unit of item
 * in our item_list to add amd modify the appearance of the list
 */

 class Word {

    private String defaultWord;
    private String miworkWord;
    private int audioResourceID;
    private int imageResourceID =HAS_IMAGE_STATE;
    private static final int HAS_IMAGE_STATE= -1;





    Word(String defaultWord, String miworkWord,int audioResourceID) {
        this.defaultWord = defaultWord;
        this.miworkWord = miworkWord;
        this.audioResourceID=audioResourceID;
    }

    public Word(String defaultWord, String miworkWord, int imageResourceID,int audioResourceID) {
        this.defaultWord = defaultWord;
        this.miworkWord = miworkWord;
        this.imageResourceID = imageResourceID;
        this.audioResourceID=audioResourceID;
    }

    String getDefaultWord() {
        return defaultWord;
    }

    String getMiworkWord() {
        return miworkWord;
    }
     int getImageResourceID() {
        return imageResourceID;
    }

    boolean hasImage(){
        return imageResourceID !=HAS_IMAGE_STATE;
    }

     int getAudioResourceID() {
        return audioResourceID;
    }

    @Override
    public String toString() {
        return "Word{" +
                "defaultWord='" + defaultWord + '\'' +
                ", miworkWord='" + miworkWord + '\'' +
                ", audioResourceID=" + audioResourceID +
                ", imageResourceID=" + imageResourceID +
                '}';
    }
}
