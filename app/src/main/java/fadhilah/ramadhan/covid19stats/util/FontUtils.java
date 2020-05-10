package fadhilah.ramadhan.covid19stats.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontUtils {


    private static final Hashtable<String, Typeface> mCache = new Hashtable<String, Typeface>();
    
    public static Typeface loadFontFromAssets(Context context) {
   	 
    	String fontName = Constant.FONT_NORMAL;
        // make sure we load each font only once
        synchronized (mCache) {
 
            if (! mCache.containsKey(fontName)) {
 
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
 
                mCache.put(fontName, typeface);
            }
 
            return mCache.get(fontName);
 
        }
 
    }
    
    public static Typeface loadFontFromAssets(Context context, String styleType) {
    	 
    	String fontName = Constant.FONT_NORMAL;
    	if(Constant.FONT_BOLD == styleType){
    		fontName = Constant.FONT_BOLD;
    	}
        // make sure we load each font only once
        synchronized (mCache) {
 
            if (! mCache.containsKey(fontName)) {
 
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
 
                mCache.put(fontName, typeface);
            }
 
            return mCache.get(fontName);
 
        }
 
    }
    
    public static Typeface loadFontFromAssets(Context context, String fontType, String styleType) {
   	 
    	String fontName = Constant.FONT_NORMAL;
    	if(Constant.FONT_BOLD.equals(fontType)){
    		fontName = Constant.FONT_BOLD;
    	}else if(Constant.FONT_ITALIC.equals(fontType)){
    		fontName = Constant.FONT_ITALIC;
    	}else if(Constant.FONT_BOLD_ITALIC.equals(fontType)){
    		fontName = Constant.FONT_BOLD_ITALIC;
    	}
        // make sure we load each font only once
        synchronized (mCache) {
 
            if (! mCache.containsKey(fontName)) {
 
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
 
                mCache.put(fontName, typeface);
            }
 
            return mCache.get(fontName);
 
        }
 
    }
}
