package util;

import java.util.List;

/**
 * Created by 胡正再 on 2017/5/17.
 */

public interface HttpCallBack {
    public void handleResponse(List<?> list);
    public void onFailure();
}
