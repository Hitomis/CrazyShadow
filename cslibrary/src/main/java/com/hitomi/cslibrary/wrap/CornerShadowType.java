package com.hitomi.cslibrary.wrap;

import android.support.annotation.IntDef;

/**
 * Created by hitomi on 2016/10/19.
 */
@IntDef(flag = true, value = {CornerShadowType.SECTOR, CornerShadowType.RINGSECTOR})
public @interface CornerShadowType {

    int SECTOR = 1;

    int RINGSECTOR = 1 << 1;

}
