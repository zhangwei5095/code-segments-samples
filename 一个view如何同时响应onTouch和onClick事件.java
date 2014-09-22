/**一个view如何同时响应onTouch和onClick事件
 
在项目中遇到的问题，需要同时给imageVIew设置onTouch和onClick事件 ， 在onTouch事件中执行拖动操作，在onClick中执行显示一个对话框，可是两个事件一直有冲突。onTouch事件的返回值是boolean类型的，如果返回true ，那么就把事件拦截，onclick肯定无法响应；返回false，就同时执行onClick方法，要想把OnTouch和onClick事件完全的区
分，这里想到了一个不是很完美但使用完全没有错误的方法，就是在 OnTouch中的MotionEvent.ACTION_DOWN 时，记录下点（X1，Y1），在 MotionEvent.ACTION_UP 时，记录下点（X2，Y2），然后比对 俩点之间的距离，如果小于一个较小数值（比如5），就认为是Click事件，onTouch中返回false，如果距离较大，可以当作onTouch事件去处理，返回true：
示范如下：
 **/
@Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                x1 =  event.getRawX();//得到相对应屏幕左上角的坐标
                y1 =  event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getRawX();
                y2 = event.getRawY();
                Log.i("i", x1 + ",,," + y1 +",,,"+x2+",,,"+y2);
                double distance = Math.sqrt(Math.abs(x1-x2)*Math.abs(x1-x2)+Math.abs(y1-y2)*Math.abs(y1-y2));//两点之间的距离
                Log.i("i", "x1 - x2>>>>>>"+ distance);
                 if (distance < 15) { // 距离较小，当作click事件来处理 
                     showToastDialog("点击了");
                    return false;
                 } else {
                     showToastDialog("滑动了");
                    return true ;
                }
        }
        return false;   
    }
 
