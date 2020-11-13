public enum Places {
    CANVAS("https://ensign.instructure.com/"),
    LDRIVER("geckodriver-v0.27.0-linux64"),
    WDRIVER("geckodriver-v0.27.0-win64")
    ;

    public final String item;

    Places(String item){
        this.item = item;
    }

    public String toString(){
        return item;
    }

}
