package com.hbs.auto;

import com.sun.jna.Library;

/**
 * Created by zun.wei on 2022/5/8.
 */
public interface SuperPatrickLibrary extends Library {

    void findElement(String pStrId, String pStrName, String pStrClassName, String controlType);

    void sendKeys(String pKeysString);

    void sendShortCutKeys(String pKeysString);

}
