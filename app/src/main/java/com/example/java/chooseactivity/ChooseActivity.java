package com.example.java.chooseactivity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class ChooseActivity extends ActionBarActivity {

    private HashMap<View,Integer> imageMap;
    private HashMap<View,Integer> containerMap;
    private HashMap<Integer,Integer> imageContainerMap;
    private int containerHeight;
    private int containerWidth;
    private int imageWidth;
    private int imageHeight;
    private LinearLayout screenlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageMap = new HashMap<>();
        containerMap = new HashMap<>();
        imageContainerMap = new HashMap<>();
        setContentView(R.layout.activity_choose);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        imageWidth = ((int)(0.3*display.getWidth()))-30;
        imageHeight=((int)(0.15*display.getHeight()))-30;
        containerHeight = ((int)(0.2*display.getHeight()))-20;
        containerWidth = ((int)(0.38*display.getWidth()))-20;
        Log.d("Size22",imageHeight+" "+imageWidth);
        ImageView v = (ImageView)findViewById(R.id.picture1);
        init(v,"image1",imageWidth,imageHeight,R.drawable.hat,R.id.picture1_container);
        ImageView v2 = (ImageView)findViewById(R.id.picture2);
        init(v2,"image2",imageWidth,imageHeight,R.drawable.bag,R.id.picture2_container);
        v2 = (ImageView)findViewById(R.id.picture3);
        init(v2, "image3",imageWidth,imageHeight, R.drawable.shoes,R.id.picture3_container);
        v2 = (ImageView)findViewById(R.id.picture4);
        init(v2, "image3",imageWidth,imageHeight, R.drawable.skirt,R.id.picture4_container);
        v2 = (ImageView)findViewById(R.id.picture5);
        init(v2, "image3",imageWidth,imageHeight, R.drawable.tshirt,R.id.picture5_container);
        v2 = (ImageView)findViewById(R.id.picture6);
        init(v2, "image3",imageWidth,imageHeight, R.drawable.trousers,R.id.picture6_container);
        screenlayout =(LinearLayout) findViewById(R.id.zzz);
        screenlayout.setOnDragListener(new MyDragListener());
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.container_for_image_1);
        frameLayout.setOnDragListener(new MyDragListener());
        containerMap.put(frameLayout,0);
        frameLayout = (FrameLayout)findViewById(R.id.container_for_image_2);
        frameLayout.setOnDragListener(new MyDragListener());
        containerMap.put(frameLayout,0);
        frameLayout = (FrameLayout)findViewById(R.id.container_for_image_3);
        frameLayout.setOnDragListener(new MyDragListener());
        containerMap.put(frameLayout,0);
    }
        private void init(ImageView v,String teg,int width,int height,int image,int container)
        {
            v.setTag(teg);
            v.setOnLongClickListener(new MyClickListener());
            v.setOnClickListener(new MyOnClickListener());
            Picasso.with(getApplicationContext()).load(image).resize(width,height).centerCrop().into(v);
            imageMap.put(v,image);
            imageContainerMap.put(image,container);
        }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private final class MyClickListener implements View.OnLongClickListener {

        // called when the item is long-clicked
        @Override
        public boolean onLongClick(View view) {
            // TODO Auto-generated method stub

            // create it from the object's tag
            int id = imageMap.get(view);
            for (Map.Entry<View,Integer> jh: containerMap.entrySet())
            {
               if( jh.getValue() == id) {
                  // resetView(view,jh.getKey(),jh.getValue());
                   return false;
               }
            }
            ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());

            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag( data, //data to be dragged
                    shadowBuilder, //drag shadow
                    view, //local data about the drag and drop operation
                    0   //no needed flags
            );


            view.setVisibility(View.INVISIBLE);
            return true;
        }

    }
    private final class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = imageMap.get(v);
            View containerView = null;
            for (Map.Entry<View,Integer> jh: containerMap.entrySet())
            {
                if( jh.getValue() == id) {
                    containerView = jh.getKey();
                     resetView(v,jh.getKey(),jh.getValue());
                }
            }
            containerMap.put(containerView,0);

        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        private void resetView(View v,View v2,int image)
        {
            //v2.setBackground();
            ViewGroup viewgroup = (ViewGroup) v.getParent();
            viewgroup.removeView(v);
            v2.setBackground(getResources().getDrawable(R.drawable.normal_shape));
            String teg = (String)v.getTag();
            imageMap.remove(v);
            ImageView view = new ImageView(getApplicationContext());
               Picasso.with(getApplicationContext()).load(image).resize(imageWidth,imageHeight).centerCrop().into((ImageView) view);
            imageMap.put(view, image);
            view.setTag(teg);
            view.setOnLongClickListener(new MyClickListener());
            view.setOnClickListener(new MyOnClickListener());
            LinearLayout layout =  (LinearLayout) findViewById(imageContainerMap.get(image));
            layout.addView(view);


        }
    }
    class MyDragListener implements View.OnDragListener{

        Drawable normalShape = getResources().getDrawable(R.drawable.normal_shape);
        Drawable targetShape = getResources().getDrawable(R.drawable.target_shape);

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;

                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:
                   if (!v.equals(screenlayout)) v.setBackground(targetShape);	//change the shape of the view
                    break;

                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(normalShape);	//change the shape of the view back to normal
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    // if the view is the bottomlinear, we accept the drag item
                    if(v == findViewById(R.id.container_for_image_1)||v == findViewById(R.id.container_for_image_2)||v == findViewById(R.id.container_for_image_3)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);

                        FrameLayout containView = (FrameLayout) v;
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = containerHeight;
                        layoutParams.width = containerWidth;
                        view.setLayoutParams(layoutParams);
                       // Picasso.with(getApplicationContext()).load(imageMap.get(view)).resize(containerWidth,containerHeight).centerCrop().into((ImageView)view);
                        containView.addView(view);
                        containerMap.put(v,imageMap.get(view));
                        view.setVisibility(View.VISIBLE);
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        Toast.makeText(context, "You can't drop the image here",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);	//go back to normal shape
                    if(containerMap.get(v)!=0) v.setBackground(getResources().getDrawable(R.drawable.all_layout_shape));
                   /* for(Map.Entry<View,Integer> bb: containerMap.entrySet())
                    {
                        int value = bb.getValue();
                        if (value==0) {
                            View view = bb.getKey();
                            view.setBackground(normalShape);
                        }
                    }*/
                default:
                    break;
            }
            return true;
        }
    }
}
