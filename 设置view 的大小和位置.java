package net.www.com.hello;


public class Main extends ActionBarActivity {

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	}

    private void switchThumb(View v, List<LayoutList> layouts) {



        

        Button b = new Button(this);
        b.setText("test");
		
		
		// 设置位置
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(30, 100, 0, 0);
        b.setLayoutParams(lp);
        layoutContainer.addView(b);

		// 设置大小
        ViewGroup.LayoutParams para = b.getLayoutParams();
        para.width = 300;
        para.height = 100;


        RelativeLayout.LayoutParams  lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button b2 = new Button(this);
        b2.setText("test2");
        lp2.setMargins(190, 100, 0, 0);
        b2.setLayoutParams(lp2);
        layoutContainer.addView(b2);

        ViewGroup.LayoutParams para2 = b2.getLayoutParams();
        para2.width = 200;
        para2.height = 200;


    }


}
